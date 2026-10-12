package com.vetcare.petmeds.modules.medicine.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_medicine")
public class MedicineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "med_id")
    private Long id;

    @Column(name = "med_name", unique = true)
    private String name;

    @Column(name = "med_manufacturer")
    private String manufacturer; // Fabricante

    @Enumerated(EnumType.STRING)
    @Column(name = "med_type")
    private TypeMedicine type; // tipo compromido, pomada, liquido

    @Column(name = "med_indicatedspecies")
    private String indicatedSpecies;  // especies indicados

    @Column(name = "med_dosage")
    private String dosage; // dosagem

    @ManyToOne
    @JoinColumn(name = "med_animal_id", referencedColumnName = "ani_id")
    @JsonBackReference
    private AnimalEntity animal;

}
