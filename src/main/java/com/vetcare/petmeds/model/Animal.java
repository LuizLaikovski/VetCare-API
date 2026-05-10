package com.vetcare.petmeds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vetcare.petmeds.utilities.Specie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Specie specie; // cachorro, gato
    private String race; // raça
    private Integer age;
    private Double weight; // peso

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "owner_id")
    private User owner;
}
