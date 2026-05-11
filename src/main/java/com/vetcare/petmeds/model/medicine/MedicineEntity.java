package com.vetcare.petmeds.model.medicine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vetcare.petmeds.model.animal.AnimalEntity;
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
    private Long id;
    @Column(unique = true)
    private String name;
    private String manufacturer; // Fabricante
    @Enumerated(EnumType.STRING)
    private TypeMedicine type; // tipo compromido, pomada, liquido
    private String indicatedSpecies;  // especies indicados
    private String dosage; // dosagem

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference
    private AnimalEntity animal;

}
