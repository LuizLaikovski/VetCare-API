package com.vetcare.petmeds.modules.VaccinationRecord.entity;

import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.vaccine.entity.VaccineEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tb_vaccination_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "var_id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "var_aniid", referencedColumnName = "ani_id")
    private AnimalEntity animal;

    @ManyToOne
    @JoinColumn(nullable = false, name = "var_vacid", referencedColumnName = "vac_id")
    private VaccineEntity vaccine;

    @ManyToOne
    @JoinColumn(nullable = false, name = "var_medid", referencedColumnName = "use_id")
    private UserEntity medic;

    @Column(nullable = false, name = "var_application_date")
    private Date applicationDate;

    @Column(nullable = false, name = "var_num_of_doses")
    private Integer numOfDoses;

    @Column(nullable = false, name = "var_observation")
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "var_application_status")
    private ApplicationStatus applicationStatus;
}
