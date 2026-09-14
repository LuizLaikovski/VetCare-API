package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.animal.controller.AnimalController;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.service.AnimalService;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AnimalController.class, 
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    private AnimalEntity animal;

    @BeforeEach
    void setUp() {
        animal = new AnimalEntity();
        animal.setId(1L);
        animal.setName("Rex");
        animal.setSpecie(Specie.Cachorro);
    }

    @Test
    void createAnimal_ShouldReturnCreated() throws Exception {
        when(animalService.createNewAnimal(any(AnimalEntity.class))).thenReturn(animal);

        mockMvc.perform(post("/animal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Animal cadastrado com sucesso"));
    }

    @Test
    void getAllAnimals_ShouldReturnOk() throws Exception {
        org.springframework.data.domain.Page<AnimalEntity> page = new org.springframework.data.domain.PageImpl<>(Arrays.asList(animal));
        when(animalService.getAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/animal/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Rex"));
    }

    @Test
    void getAnimalById_ShouldReturnOk() throws Exception {
        when(animalService.getById(1L)).thenReturn(animal);

        mockMvc.perform(get("/animal/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rex"));
    }

    @Test
    void getAnimalByName_ShouldReturnOk() throws Exception {
        when(animalService.getByName("Rex")).thenReturn(Optional.of(animal));

        mockMvc.perform(get("/animal/Rex"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rex"));
    }

    @Test
    void updateAnimal_ShouldReturnOk() throws Exception {
        when(animalService.updateAnimal(anyLong(), any(AnimalEntity.class))).thenReturn(animal);

        mockMvc.perform(put("/animal/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Animal atualizado com sucesso"));
    }

    @Test
    void deleteAnimal_ShouldReturnOk() throws Exception {
        // Since deleteById now returns ResponseEntity<ResponseDTO>, but the controller ignores it
        // We need to mock the service call.
        // Wait, the service returns ResponseEntity<ResponseDTO>.
        // Let's adjust the mock to match.
        when(animalService.deleteById(1L)).thenReturn(new org.springframework.http.ResponseEntity<>(new com.vetcare.petmeds.shared.ResponseDTO(new java.util.Date(), "Animal deletado com sucesso", "Animal deletado com sucesso"), org.springframework.http.HttpStatus.NO_CONTENT));

        mockMvc.perform(delete("/animal/1"))
                .andExpect(status().isNoContent());
    }
}
