package com.vetcare.petmeds.modules.fileTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadFileResponseDTO implements Serializable {

    private static final long seriaVersionUID = 1L;

    private String fileName;
    private String fileDowloadURI;
    private String fileType;
    private long size;
}
