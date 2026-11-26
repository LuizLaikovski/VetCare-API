package com.vetcare.petmeds.applicationPrimary.vaccine.doc;

import com.vetcare.petmeds.applicationPrimary.vaccine.entity.VaccineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineDocumentDTO {
    private List<VaccineEntity> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
