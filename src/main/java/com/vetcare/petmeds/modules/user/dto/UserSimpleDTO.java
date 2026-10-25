package com.vetcare.petmeds.modules.user.dto;

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
}
