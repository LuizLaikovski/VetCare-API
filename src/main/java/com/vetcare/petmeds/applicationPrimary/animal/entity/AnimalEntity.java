package com.vetcare.petmeds.applicationPrimary.animal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
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
    @Column(name = "ani_id")
    private Long id;

    @Column(name = "ani_name")
    private String name;

    @Column(name = "ani_specie")
    @Enumerated(EnumType.STRING)
    private Specie specie; // cachorro, gato

    @Column(name = "ani_race")
    private String race; // raça

    @Column(name = "ani_age")
    private Integer age;

    @Column(name = "ani_weight")
    private Double weight; // peso

    @Column(name = "ani_gender")
    @Enumerated(EnumType.STRING)
    private Sex gender;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ani_owner_id", referencedColumnName = "use_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("animal")
    private List<InitialCheckUpEntity> initialCheckUp;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MedicineEntity> medicine = new ArrayList<>();

    @Column(name = "ani_image_id")
    private Long imageId;

    public void update(Double weight, Integer age, String race, UserEntity owner) {
        this.weight = weight;
        this.age = age;
        this.race = race;
        this.owner = owner;
    }
}
