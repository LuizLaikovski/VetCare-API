package com.vetcare.petmeds.modules.animal.dto;

import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import lombok.Builder;
import lombok.Data;

public class AnimalResponseDTO {
    private Long id;
    private String name;
    private Specie specie;
    private String race;
    private Sex gender;

    public AnimalResponseDTO(AnimalEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.specie = entity.getSpecie();
        this.race = entity.getRace();
        this.gender = entity.getGender();
    }



}
