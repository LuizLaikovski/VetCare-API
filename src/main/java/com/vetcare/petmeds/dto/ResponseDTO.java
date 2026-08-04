package com.vetcare.petmeds.dto;

import com.vetcare.petmeds.model.user.UserResponseDTO;
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
