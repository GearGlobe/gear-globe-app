package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressRequestDTO;
import com.gearglobe.app.backend.client.api.dtos.AddressResponseDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    @Test
    void testAddressToAddressResponseDTO() {
        // Given
        Address address = new Address();
        address.setId(1L);
        address.setCity("Sample City");
        address.setStreet("Sample Street");
        address.setHouseNumber("Sample House Number");
        address.setApartmentNumber("Sample Apartment Number");
        address.setCountry("Sample Country");

        // When
        AddressResponseDTO addressRequestDTO = AddressMapper.INSTANCE.map(address);

        // Then
        assertAll("Verify mapping properties to AddressDTO",
                () -> assertEquals(address.getId(), addressRequestDTO.getId()),
                () -> assertEquals(address.getCity(), addressRequestDTO.getCity()),
                () -> assertEquals(address.getStreet(), addressRequestDTO.getStreet()),
                () -> assertEquals(address.getHouseNumber(), addressRequestDTO.getHouseNumber()),
                () -> assertEquals(address.getApartmentNumber(), addressRequestDTO.getApartmentNumber()),
                () -> assertEquals(address.getCountry(), addressRequestDTO.getCountry()));
    }

    @Test
    void testAddressRequestDTOToAddress() {
        // Given
        AddressRequestDTO addressRequestDTO = AddressRequestDTO.builder()
                .city("Sample City DTO")
                .street("Sample Street DTO")
                .houseNumber("Sample House Number DTO")
                .apartmentNumber("Sample Apartment Number DTO")
                .country("Sample Country DTO")
                .build();

        // When
        Address address = AddressMapper.INSTANCE.map(addressRequestDTO);

        // Then
        assertAll("Verify mapping properties to Address",
                () -> assertEquals(addressRequestDTO.getCity(), address.getCity()),
                () -> assertEquals(addressRequestDTO.getStreet(), address.getStreet()),
                () -> assertEquals(addressRequestDTO.getHouseNumber(), address.getHouseNumber()),
                () -> assertEquals(addressRequestDTO.getApartmentNumber(), address.getApartmentNumber()),
                () -> assertEquals(addressRequestDTO.getCountry(), address.getCountry()));
    }
}
