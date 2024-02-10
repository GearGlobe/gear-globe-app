package com.gearglobe.app.backend.client.api.dtos;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ClientGetDTO {
    private Long id;
    private String name;
    private String lastName;
    private ClientType clientType;
    private String email;
    private String phoneNumber;
    private AddressDTO address;
    private String role;
    private ClientStatus status;
}
