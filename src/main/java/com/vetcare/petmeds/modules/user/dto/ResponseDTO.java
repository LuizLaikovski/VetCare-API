package com.vetcare.petmeds.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO {
    private String response;
    private UserResponseDTO user;
    private String token;

    public ResponseDTO(UserResponseDTO user) {
        this.user = user;
    }

    public ResponseDTO(String response) {
        this.response = response;
    }
}