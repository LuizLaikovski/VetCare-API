package com.vetcare.petmeds.modules.user.dto;

import com.vetcare.petmeds.modules.user.entity.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private TypeUser typeUser;
}
