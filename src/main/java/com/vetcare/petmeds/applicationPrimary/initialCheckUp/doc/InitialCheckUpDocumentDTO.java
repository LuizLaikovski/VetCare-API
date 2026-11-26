package com.vetcare.petmeds.applicationPrimary.initialCheckUp.doc;

import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InitialCheckUpDocumentDTO {
    private List<InitialCheckUpResponseDTO> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
