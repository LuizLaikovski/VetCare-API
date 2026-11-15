package com.vetcare.petmeds.applicationPrimary.animal.dto;

import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDescriptionDTO {
    private String name;
    private Specie specie;
    private String race;
    private Integer age;
    private Double weight;
    private String gender;
    private String nameOwner;

    public AnimalDescriptionDTO(AnimalEntity entity) {
        this.name = entity.getName();
        this.specie = entity.getSpecie();
        this.race = entity.getRace();
        this.age = entity.getAge();
        this.weight = entity.getWeight();
        this.gender = entity.getGender().toString();
        this.nameOwner = entity.getOwner().getName();
    }
}
