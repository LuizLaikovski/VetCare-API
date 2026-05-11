package com.vetcare.petmeds.dto;

import com.vetcare.petmeds.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDTO {
    private String response;
    private UserEntity user;

    public ResponseDTO(UserEntity user) {
        this.user = user;
    }

    public ResponseDTO(String response) {
        this.response = response;
    }
}
