package com.vetcare.petmeds.modules.initialCheckUp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.modules.initialCheckUp.enumns.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public InitialCheckUpDTO(InitialCheckUpEntity entity) {
        if (entity.getExaminationDate() != null) {
            this.examinationDate = java.util.Date.from(
                    entity.getExaminationDate().atZone(java.time.ZoneId.systemDefault()).toInstant()
            );
        }

        this.species = entity.getSpecies();
        this.sex = entity.getSex();
        this.animalId = entity.getAnimal() != null ? entity.getAnimal().getId() : null;
        this.veterinarianId = entity.getVeterinarian() != null ? entity.getVeterinarian().getId() : null;

        this.crt = entity.getCrt();
        this.rr = entity.getRr();
        this.hr = entity.getHr();
        this.tpc = entity.getTpc();
        this.hydration = entity.getHydration();
        this.arterialPulse = entity.getArterialPulse();
        this.mucousMembranes = entity.getMucousMembranes();
        this.consciousnessLevel = entity.getConsciousnessLevel();
        this.nutritionalStatus = entity.getNutritionalStatus();
        this.behavior = entity.getBehavior();
        this.hasEctoparasites = entity.getHasEctoparasites();
        this.ectoparasitesDetails = entity.getEctoparasitesDetails();
        this.postureMovement = entity.getPostureMovement();
        this.eyes = entity.getEyes();
        this.ears = entity.getEars();
        this.oralCavity = entity.getOralCavity();
        this.respiratory = entity.getRespiratory();
        this.circulatory = entity.getCirculatory();
        this.hemolymphatic = entity.getHemolymphatic();
        this.digestive = entity.getDigestive();
        this.genital = entity.getGenital();
        this.urinary = entity.getUrinary();
        this.nervous = entity.getNervous();
        this.skinAndAppendages = entity.getSkinAndAppendages();
        this.otherStatus = entity.getOtherStatus();
        this.otherObservations = entity.getOtherObservations();
    }
}
