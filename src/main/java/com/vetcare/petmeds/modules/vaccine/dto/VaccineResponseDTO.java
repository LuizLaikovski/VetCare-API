package com.vetcare.petmeds.modules.vaccine.dto;

import com.vetcare.petmeds.modules.animal.entity.Specie;
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
}
