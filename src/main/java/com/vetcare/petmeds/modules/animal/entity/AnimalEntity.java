package com.vetcare.petmeds.modules.animal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_animals")
public class AnimalEntity {

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
    private UserEntity owner;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MedicineEntity> medicine = new ArrayList<>();
}
