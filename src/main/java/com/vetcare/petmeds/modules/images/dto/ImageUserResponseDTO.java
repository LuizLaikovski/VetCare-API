package com.vetcare.petmeds.modules.images.dto;


import com.vetcare.petmeds.modules.images.entity.ImageEntity;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageUserResponseDTO {
    private String nameUser;
    private String emailUser;
    private String name;
    private String typeContent;
    private byte[] data;

    public ImageUserResponseDTO(UserEntity user, ImageEntity image) {
        this.nameUser = user.getName();
        this.emailUser = user.getEmail();
        this.name = image.getNome();
        this.typeContent = image.getTipoConteudo();
        this.data = image.getDados();
    }
}
