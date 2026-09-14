package com.vetcare.petmeds.modules.history.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryRequestDTO {
    private Long idAnimal;
    private Long idLastCheckup;
}
