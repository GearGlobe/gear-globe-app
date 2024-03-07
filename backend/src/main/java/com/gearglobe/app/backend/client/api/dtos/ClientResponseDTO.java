package com.gearglobe.app.backend.client.api.dtos;

import com.gearglobe.app.backend.client.api.dtos.enums.ClientRole;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientStatus;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private ClientType clientType;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private AddressRequestDTO address;
    private ClientRole role;
    private ClientStatus status;
}
