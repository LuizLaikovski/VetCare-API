package com.vetcare.petmeds.applicationPrimary.animal.dto;

import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Sex;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import com.vetcare.petmeds.archives.images.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSimpleDescriptionDTO {

    private Long id;
    private String name;
    private Specie specie; // cachorro, gato
    private String race; // raça
    private Integer age;
    private Double weight; // peso
    private Sex gender;
    private byte[] image;

    public AnimalSimpleDescriptionDTO(AnimalEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.specie = entity.getSpecie();
        this.race = entity.getRace();
        this.age = entity.getAge();
        this.weight = entity.getWeight();
        this.gender = entity.getGender();
        this.image = null;
    }

    public AnimalSimpleDescriptionDTO(AnimalEntity entity, ImageService imageService) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.specie = entity.getSpecie();
        this.race = entity.getRace();
        this.age = entity.getAge();
        this.weight = entity.getWeight();
        this.gender = entity.getGender();
        this.image = imageService.findImageByIdToEntity(entity.getImageId()).getDados();
    }
}
