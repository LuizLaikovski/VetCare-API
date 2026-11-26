package com.vetcare.petmeds.applicationPrimary.animal.doc;

import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalDescriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalDescriptionDocumentDTO {
    private List<AnimalDescriptionDTO> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
