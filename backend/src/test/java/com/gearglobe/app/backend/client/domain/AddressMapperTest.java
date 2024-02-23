package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    @Test
    void testAddressToAddressDTO() {
        // Given
        Address address = new Address();
        address.setId(1L);
        address.setCity("Sample City");
        address.setStreet("Sample Street");
        address.setHouseNumber("Sample House Number");
        address.setApartmentNumber("Sample Apartment Number");
        address.setCountry("Sample Country");

        // When
        AddressDTO addressDTO = AddressMapper.INSTANCE.map(address);

        // Then
        assertAll("Verify mapping properties to AddressDTO",
                () -> assertEquals(address.getCity(), addressDTO.getCity()),
                () -> assertEquals(address.getStreet(), addressDTO.getStreet()),
                () -> assertEquals(address.getHouseNumber(), addressDTO.getHouseNumber()),
                () -> assertEquals(address.getApartmentNumber(), addressDTO.getApartmentNumber()),
                () -> assertEquals(address.getCountry(), addressDTO.getCountry()));
    }

    @Test
    void testAddressDTOToAddress() {
        // Given
        AddressDTO addressDTO = AddressDTO.builder()
                .city("Sample City DTO")
                .street("Sample Street DTO")
                .houseNumber("Sample House Number DTO")
                .apartmentNumber("Sample Apartment Number DTO")
                .country("Sample Country DTO")
                .build();

        // When
        Address address = AddressMapper.INSTANCE.map(addressDTO);

        // Then
        assertAll("Verify mapping properties to Address",
                () -> assertEquals(addressDTO.getCity(), address.getCity()),
                () -> assertEquals(addressDTO.getStreet(), address.getStreet()),
                () -> assertEquals(addressDTO.getHouseNumber(), address.getHouseNumber()),
                () -> assertEquals(addressDTO.getApartmentNumber(), address.getApartmentNumber()),
                () -> assertEquals(addressDTO.getCountry(), address.getCountry()));
    }
}
