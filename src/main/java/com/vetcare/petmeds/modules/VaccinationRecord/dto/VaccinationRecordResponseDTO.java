package com.vetcare.petmeds.modules.VaccinationRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vetcare.petmeds.modules.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.modules.user.dto.UserSimpleDTO;
import com.vetcare.petmeds.modules.vaccine.dto.VaccineResponseDTO;

import java.util.Date;

public class VaccinationRecordResponse {
    private Long id;
    private AnimalResponseDTO animalId;
    private VaccineResponseDTO vaccineId;
    private UserSimpleDTO medicId;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date applicationDate;
    private Integer numOfDoses;
    private String observation;
    private String applicationStatus;
}
