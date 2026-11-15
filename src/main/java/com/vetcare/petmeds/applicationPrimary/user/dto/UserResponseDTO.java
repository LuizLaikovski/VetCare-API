package com.vetcare.petmeds.applicationPrimary.user.dto;

import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {
        private Long id;
        private String name;
        private String email;
        private TypeUser typeUser;

        public UserResponseDTO(UserEntity entity) {
                this.id = entity.getId();
                this.name = entity.getName();
                this.email = entity.getEmail();
                this.typeUser = entity.getTypeUser();
        }
}
