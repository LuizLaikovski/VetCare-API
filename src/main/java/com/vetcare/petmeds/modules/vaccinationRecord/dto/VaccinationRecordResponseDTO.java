package com.vetcare.petmeds.modules.vaccinationRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vetcare.petmeds.modules.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.modules.user.dto.UserSimpleDTO;
import com.vetcare.petmeds.modules.vaccine.dto.VaccineResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationRecordResponseDTO {
    private Long id;
    private AnimalResponseDTO animal;
    private VaccineResponseDTO vaccine;
    private UserSimpleDTO medic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applicationDate;
    private Integer numOfDoses;
    private String observation;
    private String applicationStatus;
}
