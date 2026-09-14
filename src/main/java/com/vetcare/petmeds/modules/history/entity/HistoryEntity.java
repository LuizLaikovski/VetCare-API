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
    private Long id;

    @OneToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private AnimalEntity animal;

    @OneToOne
    @JoinColumn(name = "last_checup_id", referencedColumnName = "id")
    private InitialCheckUpEntity checkUp;
}
