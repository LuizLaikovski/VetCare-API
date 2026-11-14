package com.vetcare.petmeds.modules.history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vetcare.petmeds.modules.animal.dto.AnimalDescriptionDTO;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.history.entity.HistoryEntity;
import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponseDTO {
    private Long id;
    private Long idAnimal;
    private String nameAnimal;
    private AnimalDescriptionDTO descriptionAnimal;
    private Long idCheckup;
    private InitialCheckUpDTO descriptionCheckup;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime dateCheckup;
    private Long idMedic;

    public HistoryResponseDTO(HistoryEntity historyEntity) {
        this.id = historyEntity.getId();
        this.idAnimal = historyEntity.getAnimal().getId();
        this.nameAnimal = historyEntity.getAnimal().getName();
        this.descriptionAnimal = new AnimalDescriptionDTO(historyEntity.getAnimal());
        this.idCheckup = historyEntity.getCheckUp().getId();
        this.descriptionCheckup = new InitialCheckUpDTO(historyEntity.getCheckUp());
        this.dateCheckup = historyEntity.getCheckUp().getExaminationDate();
        this.idMedic = historyEntity.getCheckUp().getVeterinarian().getId();
    }


}
