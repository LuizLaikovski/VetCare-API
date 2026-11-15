package com.vetcare.petmeds.archives.images.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageRequestDTO {
    private String name;
    private String typeContent;
    private MultipartFile data;
}
