package com.vetcare.petmeds.modules.history.entity;

import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "his_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "his_animal_id", referencedColumnName = "ani_id")
    private AnimalEntity animal;

    @OneToOne
    @JoinColumn(name = "his_checkupid", referencedColumnName = "inc_id")
    private InitialCheckUpEntity checkUp;
}
