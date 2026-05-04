package com.vetcare.petmeds.dto;

import com.vetcare.petmeds.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class ResponseDTO {
    private String response;
    private User user;

    public ResponseDTO(User user) {
        this.user = user;
    }

    public ResponseDTO(String response) {
        this.response = response;
    }
}
