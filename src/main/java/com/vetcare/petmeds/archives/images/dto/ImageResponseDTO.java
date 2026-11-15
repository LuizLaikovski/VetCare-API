package com.vetcare.petmeds.archives.images.dto;


import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageResponseDTO {
    private String name;
    private String typeContent;
    private byte[] data;

    public ImageResponseDTO(ImageEntity entity) {
        this.name = entity.getNome();
        this.typeContent = entity.getTipoConteudo();
        this.data = entity.getDados();
    }
}
