package com.vetcare.petmeds.modules.animal.dto;

import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimalResponseDTO {
    private Long id;
    private String name;
    private Specie specie;
    private String race;
    private Sex gender;
}
