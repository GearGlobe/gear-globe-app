package com.gearglobe.app.backend.client.api.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDTO {
    private Long id;
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String country;
}
