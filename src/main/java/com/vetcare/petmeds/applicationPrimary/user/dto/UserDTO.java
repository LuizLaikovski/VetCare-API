package com.vetcare.petmeds.applicationPrimary.user.dto;

import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
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
