package com.vetcare.petmeds.modules.vaccine.entity;

import com.vetcare.petmeds.modules.animal.entity.Specie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_vaccine")
public class VaccineEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vac_id")
    private Long id;

    @Column(nullable = false, length = 70, name = "vac_vaccinename")
    private String vaccineName;

    @Column(nullable = false, length = 250, name = "vac_description")
    private String description;

    @Column(nullable = false, name = "vac_numofdoses")
    private Integer numOfDoses;

    @Column(nullable = false, name = "vac_dayinterval")
    private Integer dayInterval;

    @Column(nullable = false, name = "vac_specie")
    @Enumerated(EnumType.STRING)
    private Specie specie;
}
