package com.vetcare.petmeds.applicationPrimary.user.doc;

import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDocumentDTO {
    private List<UserEntity> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
