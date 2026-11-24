package com.vetcare.petmeds.applicationPrimary.animal.dto;

import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Sex;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
