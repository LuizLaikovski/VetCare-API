package com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto;

import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Sex;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.enumns.*;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitialCheckUpResponseDTO {

    private Long id;
    private LocalDateTime examinationDate;
    private Specie species;
    private Sex sex;
    
    // DTOs simplificados para exibição
    private AnimalResponseDTO animal;
    private UserProfileDTO veterinarian;
    
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

    public InitialCheckUpResponseDTO(InitialCheckUpEntity entity) {
        this.id = entity.getId();
        this.examinationDate = entity.getExaminationDate();
        this.species = entity.getSpecies();
        this.sex = entity.getSex();
        this.animal = new AnimalResponseDTO(entity.getAnimal());
        this.veterinarian = new UserProfileDTO(entity.getVeterinarian());
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
