package com.vetcare.petmeds.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {
        private String name;
        private String email;
        private TypeUser typeUser;
}
