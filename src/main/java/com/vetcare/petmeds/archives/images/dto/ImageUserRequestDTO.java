package com.vetcare.petmeds.archives.images.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageUserRequestDTO {
    private Long idUser;
    private ImageRequestDTO image;
}
