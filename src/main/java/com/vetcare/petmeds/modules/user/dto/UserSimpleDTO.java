package com.vetcare.petmeds.modules.user.dto;

import com.vetcare.petmeds.modules.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleDTO {
    private Long id;
    private String name;
    private String email;

    public UserSimpleDTO(UserEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
    }
}
