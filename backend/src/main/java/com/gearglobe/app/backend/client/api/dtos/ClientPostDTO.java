package com.gearglobe.app.backend.client.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientPostDTO {
    @NotNull
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    private String lastName;

    @NotBlank(message = "Client type is mandatory")
    private ClientType clientType;

    private LocalDate birthDate;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotNull
    private AddressDTO address;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Role is mandatory")
    private String role;

    @NotBlank(message = "Status is mandatory")
    private ClientStatus status;
}
