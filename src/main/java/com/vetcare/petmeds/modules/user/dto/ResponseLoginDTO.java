package com.vetcare.petmeds.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseLoginDTO {
    private String response;
    private UserResponseDTO user;
    private String token;

    public ResponseLoginDTO(UserResponseDTO user) {
        this.user = user;
    }

    public ResponseLoginDTO(String response) {
        this.response = response;
    }
}