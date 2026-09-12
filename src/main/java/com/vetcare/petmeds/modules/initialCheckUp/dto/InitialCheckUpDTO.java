package com.vetcare.petmeds.modules.initialCheckUp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import com.vetcare.petmeds.modules.initialCheckUp.enumns.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class InitialCheckUpDTO {

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date examinationDate;
    private Specie species;
    private Sex sex;
    private Long animalId;
    private Long veterinarianId;
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
