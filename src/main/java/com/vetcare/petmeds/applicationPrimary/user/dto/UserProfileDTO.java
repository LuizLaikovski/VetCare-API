package com.vetcare.petmeds.applicationPrimary.user.dto;

import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.archives.images.service.ImageService;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDTO {
        private Long id;
        private String name;
        private String email;
        private TypeUser typeUser;
        @Schema(description = "Imagem de perfil em bytes; null quando o usuário não possui imagem", nullable = true)
        private byte[] image;

        public UserProfileDTO(UserEntity entity) {
                this.id = entity.getId();
                this.name = entity.getName();
                this.email = entity.getEmail();
                this.typeUser = entity.getTypeUser();
                this.image = null;
        }

        public UserProfileDTO(UserEntity entity, ImageService imageService) {
                this.id = entity.getId();
                this.name = entity.getName();
                this.email = entity.getEmail();
                this.typeUser = entity.getTypeUser();
                this.image = loadImage(entity.getImageId(), imageService);
        }

        /** A imagem é opcional: sem imageId (ou com a imagem já removida) o perfil segue com image = null. */
        private static byte[] loadImage(Long imageId, ImageService imageService) {
                if (imageId == null) {
                        return null;
                }
                try {
                        return imageService.findImageByIdToEntity(imageId).getDados();
                } catch (ResourceNotFoundException e) {
                        return null;
                }
        }
}
