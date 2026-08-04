package com.vetcare.petmeds.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDTO {
    private String response;
    private UserResponseDTO user;

    public ResponseDTO(UserResponseDTO user) {
        this.user = user;
    }

    public ResponseDTO(String response) {
        this.response = response;
    }
}
