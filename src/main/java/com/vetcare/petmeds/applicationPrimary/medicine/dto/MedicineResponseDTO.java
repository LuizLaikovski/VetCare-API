package com.vetcare.petmeds.applicationPrimary.medicine.dto;

import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponseDTO {
    private Long id;
    private String name;
    private String manufacturer;
    private String type;
    private String indicatedSpecies;
    private String dosage;
    private AnimalResponseDTO animal;

    public MedicineResponseDTO(MedicineEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.manufacturer = entity.getManufacturer();
        this.type = entity.getType().toString();
        this.indicatedSpecies = entity.getIndicatedSpecies();
        this.dosage = entity.getDosage();
        this.animal = entity.getAnimal() != null ? new AnimalResponseDTO(entity.getAnimal()) : null;
    }
}
