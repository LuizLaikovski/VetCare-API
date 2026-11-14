package com.vetcare.petmeds.modules.vaccinationRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationRecordCreateDTO {
    private Long animalId;
    private Long vaccineId;
    private Long medicId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applicationDate;
    private Integer numOfDoses;
    private String observation;
    private String applicationStatus;
}
