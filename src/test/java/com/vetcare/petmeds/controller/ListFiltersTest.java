package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.applicationPrimary.initialCheckUp.controller.InitialCheckUpController;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.service.InitialCheckUpService;
import com.vetcare.petmeds.applicationPrimary.medicine.controller.MedicineController;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.controller.VaccinationRecordController;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.service.VaccinationRecordService;
import com.vetcare.petmeds.applicationPrimary.vaccine.controller.VaccineController;
import com.vetcare.petmeds.applicationPrimary.vaccine.service.VaccineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Filtros por query param nas coleções (substituem as antigas rotas /name, /animal, /date etc.). */
@WebMvcTest(controllers = {InitialCheckUpController.class, MedicineController.class, VaccineController.class, VaccinationRecordController.class},
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class ListFiltersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InitialCheckUpService checkUpService;
    @MockitoBean
    private MedicineService medicineService;
    @MockitoBean
    private VaccineService vaccineService;
    @MockitoBean
    private VaccinationRecordService recordService;

    // ---- medicamentos e vacinas: ?name=
    @Test
    void medicines_WithName_ShouldSearchByName() throws Exception {
        when(medicineService.getByName(eq("dip"), any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/medicines").param("name", "dip")).andExpect(status().isOk());

        verify(medicineService).getByName(eq("dip"), any());
        verify(medicineService, never()).getAllMedicines(any());
    }

    @Test
    void medicines_WithBlankName_ShouldListAll() throws Exception {
        when(medicineService.getAllMedicines(any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/medicines").param("name", " ")).andExpect(status().isOk());

        verify(medicineService, never()).getByName(any(), any());
    }

    @Test
    void vaccines_WithoutName_ShouldListAll() throws Exception {
        when(vaccineService.getAll(any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/vaccines")).andExpect(status().isOk());

        verify(vaccineService, never()).getByName(any(), any());
    }

    // ---- check-ups
    @Test
    void checkUps_WithPartialPeriod_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/initial-check-ups").param("start", "2026-01-01"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/initial-check-ups").param("end", "2026-01-01"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkUps_WithInvalidDate_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/initial-check-ups").param("start", "x").param("end", "y"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkUps_WithoutFilters_ShouldListAll() throws Exception {
        when(checkUpService.findAll(any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/initial-check-ups")).andExpect(status().isOk());

        verify(checkUpService).findAll(any());
    }

    @Test
    void checkUps_AnimalFilterHasPrecedenceOverVeterinarian() throws Exception {
        when(checkUpService.findByAnimalId(eq(1L), any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/initial-check-ups").param("animalId", "1").param("veterinarianId", "2"))
                .andExpect(status().isOk());

        verify(checkUpService, never()).findByVeterinarianId(any(), any());
    }

    // ---- registros de vacinação
    @Test
    void records_WithMedic_ShouldFilterByMedic() throws Exception {
        when(recordService.findByMedicId(eq(3L), any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/vaccination-records").param("medicId", "3")).andExpect(status().isOk());

        verify(recordService).findByMedicId(eq(3L), any());
    }

    @Test
    void records_WithVaccine_ShouldFilterByVaccine() throws Exception {
        when(recordService.findByVaccineId(eq(4L), any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/vaccination-records").param("vaccineId", "4")).andExpect(status().isOk());

        verify(recordService).findByVaccineId(eq(4L), any());
    }

    @Test
    void records_WithStatus_ShouldFilterByStatus() throws Exception {
        when(recordService.findByApplicationStatus(anyString(), any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/vaccination-records").param("status", "APPLIED")).andExpect(status().isOk());

        verify(recordService).findByApplicationStatus(eq("APPLIED"), any());
    }

    @Test
    void records_WithDate_ShouldFilterByDate() throws Exception {
        when(recordService.findByApplicationDate(any(Date.class), any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/vaccination-records").param("date", "2026-01-01")).andExpect(status().isOk());

        verify(recordService).findByApplicationDate(any(Date.class), any());
    }

    @Test
    void records_WithPeriod_ShouldFilterByRange() throws Exception {
        when(recordService.findByDateRange(any(Date.class), any(Date.class), any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/vaccination-records").param("startDate", "2026-01-01").param("endDate", "2026-12-31"))
                .andExpect(status().isOk());

        verify(recordService).findByDateRange(any(Date.class), any(Date.class), any());
    }

    @Test
    void records_WithPartialPeriod_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/vaccination-records").param("startDate", "2026-01-01"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/vaccination-records").param("endDate", "2026-01-01"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void records_WithoutFilters_ShouldListAll() throws Exception {
        when(recordService.findAll(any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/vaccination-records")).andExpect(status().isOk());

        verify(recordService).findAll(any());
    }

    @Test
    void oldFilterRoutes_ShouldNotExist() throws Exception {
        mockMvc.perform(get("/vaccination-record/all")).andExpect(status().is4xxClientError());
        mockMvc.perform(get("/initialCheckUp/all")).andExpect(status().is4xxClientError());
        mockMvc.perform(get("/vaccine/name/x")).andExpect(status().is4xxClientError());
    }
}
