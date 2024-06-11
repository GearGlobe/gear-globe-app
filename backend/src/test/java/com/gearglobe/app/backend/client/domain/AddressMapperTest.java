package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.AddressResponseDTO;
import com.gearglobe.dto.CreateAddressRequestDTO;
import com.gearglobe.dto.UpdateAddressRequestDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    @Test
    void testAddressToAddressResponseDTO() {
        // GIVEN
        Address address = prepareAddress();

        // WHEN
        AddressResponseDTO addressRequestDTO = AddressMapper.INSTANCE.map(address);

        // THEN
        assertAll("Verify mapping properties to AddressDTO",
                () -> assertEquals(address.getId(), addressRequestDTO.getId()),
                () -> assertEquals(address.getCity(), addressRequestDTO.getCity()),
                () -> assertEquals(address.getStreet(), addressRequestDTO.getStreet()),
                () -> assertEquals(address.getHouseNumber(), addressRequestDTO.getHouseNumber()),
                () -> assertEquals(address.getApartmentNumber(), addressRequestDTO.getApartmentNumber()),
                () -> assertEquals(address.getCountry(), addressRequestDTO.getCountry())
        );
    }

    @Test
    void testCreateAddressRequestDTOToAddress() {
        // GIVEN
        CreateAddressRequestDTO addressRequestDTO = prepareCreateAddressRequestDTO();

        // WHEN
        Address address = AddressMapper.INSTANCE.map(addressRequestDTO);

        // THEN
        assertAll("Verify mapping properties to Address",
                () -> assertEquals(addressRequestDTO.getCity(), address.getCity()),
                () -> assertEquals(addressRequestDTO.getStreet(), address.getStreet()),
                () -> assertEquals(addressRequestDTO.getHouseNumber(), address.getHouseNumber()),
                () -> assertEquals(addressRequestDTO.getApartmentNumber(), address.getApartmentNumber()),
                () -> assertEquals(addressRequestDTO.getCountry(), address.getCountry())
        );
    }

    @Test
    void testUpdateAddressRequestDTOToAddress() {
        //GIVEN
        UpdateAddressRequestDTO updateAddressRequestDTO = prepareUpdateAddressRequestDTO();

        //WHEN
        Address address = AddressMapper.INSTANCE.map(updateAddressRequestDTO);

        //THEN
        assertAll("Verify mapping properties to Address",
                () -> assertEquals(updateAddressRequestDTO.getCity(), address.getCity()),
                () -> assertEquals(updateAddressRequestDTO.getStreet(), address.getStreet()),
                () -> assertEquals(updateAddressRequestDTO.getHouseNumber(), address.getHouseNumber()),
                () -> assertEquals(updateAddressRequestDTO.getApartmentNumber(), address.getApartmentNumber()),
                () -> assertEquals(updateAddressRequestDTO.getCountry(), address.getCountry())
        );
    }

    private Address prepareAddress() {
        return Address.builder()
                .city("Sample City")
                .street("Sample Street")
                .country("Sample Country")
                .houseNumber("Sample House Number")
                .apartmentNumber("Sample Apartment Number")
                .build();
    }

    private CreateAddressRequestDTO prepareCreateAddressRequestDTO() {
        return CreateAddressRequestDTO.builder()
                .city("Sample DTO City")
                .street("Sample DTO Street")
                .country("Sample DTO Country")
                .houseNumber("Sample DTO House Number")
                .apartmentNumber("Sample DTO Apartment Number")
                .build();
    }

    private UpdateAddressRequestDTO prepareUpdateAddressRequestDTO() {
        return UpdateAddressRequestDTO.builder()
                .city("Sample Updated City")
                .street("Sample Updated Street")
                .country("Sample Updated Country")
                .houseNumber("Sample Updated House Number")
                .apartmentNumber("Sample Updated Apartment Number")
                .build();
    }
}
