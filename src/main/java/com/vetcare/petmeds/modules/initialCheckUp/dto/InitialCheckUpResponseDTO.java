package com.vetcare.petmeds.modules.initialCheckUp.dto;

import com.vetcare.petmeds.modules.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import com.vetcare.petmeds.modules.initialCheckUp.enumns.*;
import com.vetcare.petmeds.modules.user.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InitialCheckUpResponseDTO {

    private Long id;
    private LocalDateTime examinationDate;
    private Specie species;
    private Sex sex;
    
    // DTOs simplificados para exibição
    private AnimalResponseDTO animal;
    private UserResponseDTO veterinarian;
    
    private String crt;
    private String rr;
    private String hr;
    private String tpc;
    private String hydration;
    private ArterialPulse arterialPulse;
    private MucosaStatus mucousMembranes;
    private ConsciousnessLevel consciousnessLevel;
    private NutritionalStatus nutritionalStatus;
    private Behavior behavior;
    private Boolean hasEctoparasites;
    private String ectoparasitesDetails;
    private ExamStatus postureMovement;
    private ExamStatus eyes;
    private ExamStatus ears;
    private ExamStatus oralCavity;
    private ExamStatus respiratory;
    private ExamStatus circulatory;
    private ExamStatus hemolymphatic;
    private ExamStatus digestive;
    private ExamStatus genital;
    private ExamStatus urinary;
    private ExamStatus nervous;
    private ExamStatus skinAndAppendages;
    private ExamStatus otherStatus;
    private String otherObservations;
}
