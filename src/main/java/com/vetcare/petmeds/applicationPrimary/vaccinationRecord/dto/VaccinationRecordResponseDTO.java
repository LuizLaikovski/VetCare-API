package com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserSimpleDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.entity.VaccinationRecordEntity;
import com.vetcare.petmeds.applicationPrimary.vaccine.dto.VaccineResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationRecordResponseDTO {
    private Long id;
    private AnimalResponseDTO animal;
    private VaccineResponseDTO vaccine;
    private UserSimpleDTO medic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applicationDate;
    private Integer numOfDoses;
    private String observation;
    private String applicationStatus;

    public VaccinationRecordResponseDTO(VaccinationRecordEntity entity) {
        this.id = entity.getId();
        this.animal = new AnimalResponseDTO(entity.getAnimal());
        this.vaccine = new VaccineResponseDTO(entity.getVaccine());
        this.medic =  new UserSimpleDTO(entity.getMedic());
        this.applicationDate = entity.getApplicationDate();
        this.numOfDoses = entity.getNumOfDoses();
        this.observation = entity.getObservation();
        this.applicationStatus = entity.getApplicationStatus().name();
    }
}
