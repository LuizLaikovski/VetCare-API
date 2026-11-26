package com.vetcare.petmeds.applicationPrimary.history.doc;

import com.vetcare.petmeds.applicationPrimary.history.dto.HistoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDocumentDTO {
    private List<HistoryResponseDTO> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
