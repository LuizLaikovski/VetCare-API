package com.vetcare.petmeds.modules.animal.dto;

import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSimpleDTO {

    private Long id;
    private String name;
    private Specie specie; // cachorro, gato
    private String race; // raça
    private Integer age;
    private Double weight; // peso
    private Sex gender;
}
