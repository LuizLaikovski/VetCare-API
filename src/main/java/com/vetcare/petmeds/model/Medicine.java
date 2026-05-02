package com.vetcare.petmeds.model;

import com.vetcare.petmeds.utilities.TypeMedicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String manufacturer; // Fabricante
    private TypeMedicine type; // tipo compromido, pomada, liquido
    private String indicatedSpecies;  // especies indicados
    private String dosage; // dosagem

}
