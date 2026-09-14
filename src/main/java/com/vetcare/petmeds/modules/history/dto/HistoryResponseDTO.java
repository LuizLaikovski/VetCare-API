package com.vetcare.petmeds.modules.history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class HistoryResponseDTO {
    private Long id;
    private Long idAnimal;
    private String nameAnimal;
    private String descriptionAnimal;
    private Long idCheckup;
    private String descriptionCheckup;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime dateCheckup;
    private Long idMedic;
}
