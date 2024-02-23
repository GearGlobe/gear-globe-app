package com.gearglobe.app.backend.client.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    @NotBlank(message = "City is mandatory")
    @Size(min = 2, message = "City must have at least 3 characters")
    @Size(max = 90, message = "City must have at most 90 characters")
    private String city;

    @NotBlank(message = "Street is mandatory")
    @Size(min = 3, message = "Street must have at least 3 characters")
    @Size(max = 100, message = "Street must have at most 100 characters")
    private String street;

    @NotBlank(message = "House number is mandatory")
    private String houseNumber;

    private String apartmentNumber;

    @NotBlank(message = "Country is mandatory")
    @Size(max = 56, message = "Country must have at most 56 characters")
    private String country;
}
