package com.vetcare.petmeds.applicationPrimary.vaccine.dto;

import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import com.vetcare.petmeds.applicationPrimary.vaccine.entity.VaccineEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaccineResponseDTO {
    private Long id;
    private String vaccineName;
    private String description;
    private Integer numOfDoses;
    private Integer dayInterval;
    private Specie specie;

    public VaccineResponseDTO(VaccineEntity entity) {
        this.id = entity.getId();
        this.vaccineName = entity.getVaccineName();
        this.description = entity.getDescription();
        this.numOfDoses = entity.getNumOfDoses();
        this.dayInterval = entity.getDayInterval();
        this.specie = entity.getSpecie();
    }
}
