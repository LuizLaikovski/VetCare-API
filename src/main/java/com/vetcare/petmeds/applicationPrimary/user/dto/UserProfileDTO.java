package com.vetcare.petmeds.applicationPrimary.user.dto;

import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.archives.images.service.ImageService;
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
                this.image = imageService.findImageByIdToEntity(entity.getImageId()).getDados();
        }
}
