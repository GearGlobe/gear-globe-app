package com.gearglobe.app.backend.client.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    @NotNull
    private Long id;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "Street is mandatory")
    private String street;
    @NotBlank(message = "House number is mandatory")
    private String houseNumber;
    @NotBlank(message = "Country is mandatory")
    private String country;
}
