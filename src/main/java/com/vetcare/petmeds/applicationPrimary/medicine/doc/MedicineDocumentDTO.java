package com.vetcare.petmeds.applicationPrimary.medicine.doc;

import com.vetcare.petmeds.applicationPrimary.medicine.dto.MedicineResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineDocumentDTO {
    private List<MedicineResponseDTO> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
