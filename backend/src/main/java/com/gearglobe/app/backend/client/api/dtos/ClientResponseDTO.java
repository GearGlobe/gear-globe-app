package com.gearglobe.app.backend.client.api.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class ClientResponseDTO {
    private String name;
    private String lastName;
    private ClientType clientType;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private AddressDTO address;
    private String role;
    private ClientStatus status;
}
