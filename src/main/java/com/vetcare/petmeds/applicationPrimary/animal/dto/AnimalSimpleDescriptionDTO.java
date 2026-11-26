package com.vetcare.petmeds.applicationPrimary.animal.dto;

import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Sex;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import com.vetcare.petmeds.archives.images.service.ImageService;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Imagem do animal em bytes; null quando o animal não possui imagem", nullable = true)
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
        this.image = loadImage(entity.getImageId(), imageService);
    }

    /** A imagem é opcional: sem imageId (ou com a imagem já removida) o animal segue com image = null. */
    private static byte[] loadImage(Long imageId, ImageService imageService) {
        if (imageId == null) {
            return null;
        }
        try {
            return imageService.findImageByIdToEntity(imageId).getDados();
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }
}
