package com.vetcare.petmeds.modules.images.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageUserRequestDTO {
    private Long idUser;
    private ImageRequestDTO image;
}
