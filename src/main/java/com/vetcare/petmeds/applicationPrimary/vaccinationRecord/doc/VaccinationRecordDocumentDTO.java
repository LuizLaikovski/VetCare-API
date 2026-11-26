package com.vetcare.petmeds.applicationPrimary.vaccinationRecord.doc;

import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationRecordDocumentDTO {
    private List<VaccinationRecordResponseDTO> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
