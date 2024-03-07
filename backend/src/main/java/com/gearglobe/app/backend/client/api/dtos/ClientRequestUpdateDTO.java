package com.gearglobe.app.backend.client.api.dtos;

import com.gearglobe.app.backend.client.api.dtos.enums.ClientType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientRequestUpdateDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, message = "Name must have at least 3 characters")
    @Size(max = 50, message = "Name must have at most 50 characters")
    private String name;

    @Size(min = 2, message = "Name must have at least 3 characters")
    @Size(max = 50, message = "Name must have at most 50 characters")
    private String lastName;

    @NotNull(message = "Client type is mandatory")
    private ClientType clientType;

    private LocalDate birthDate;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{9}$", message = "Phone number must have 9 digits")
    private String phoneNumber;

}
