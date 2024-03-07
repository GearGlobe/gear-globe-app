package com.gearglobe.app.backend.client.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordRequestUpdateDTO {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
