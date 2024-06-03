package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMapperTest {

    @Test
    void testClientToClientResponseDTO() {
        // GIVEN
        Client client = prepareClient();

        // WHEN
        ClientResponseDTO clientResponseDTO = ClientMapper.INSTANCE.map(client);

        // THEN
        assertAll("Verify mapping properties to ClientResponseDTO",
                () -> assertEquals(client.getName(), clientResponseDTO.getName()),
                () -> assertEquals(client.getLastName(), clientResponseDTO.getLastName()),
                () -> assertEquals(client.getClientType(), clientResponseDTO.getClientType()),
                () -> assertEquals(client.getBirthDate(), clientResponseDTO.getBirthDate()),
                () -> assertEquals(client.getEmail(), clientResponseDTO.getEmail()),
                () -> assertEquals(client.getPhoneNumber(), clientResponseDTO.getPhoneNumber()),
                () -> assertEquals(client.getRole(), clientResponseDTO.getClientRole()),
                () -> assertEquals(client.getStatus(), clientResponseDTO.getClientStatus()),
                () -> assertEquals(client.getAddress().getStreet(), clientResponseDTO.getAddress().getStreet()),
                () -> assertEquals(client.getAddress().getCity(), clientResponseDTO.getAddress().getCity()),
                () -> assertEquals(client.getAddress().getHouseNumber(), clientResponseDTO.getAddress().getHouseNumber()),
                () -> assertEquals(client.getAddress().getApartmentNumber(), clientResponseDTO.getAddress().getApartmentNumber()),
                () -> assertEquals(client.getAddress().getCountry(), clientResponseDTO.getAddress().getCountry())
        );
    }

    @Test
    void testCreateClientRequestDTOToClient() {
        // GIVEN
        CreateClientRequestDTO clientRequestDTO = prepareCreateClientRequestDTO();

        // WHEN
        Client client = ClientMapper.INSTANCE.map(clientRequestDTO);

        // THEN
        assertAll("Verify mapping properties to Client",
                () -> assertEquals(clientRequestDTO.getName(), client.getName()),
                () -> assertEquals(clientRequestDTO.getLastName(), client.getLastName()),
                () -> assertEquals(clientRequestDTO.getClientType(), client.getClientType()),
                () -> assertEquals(clientRequestDTO.getBirthDate(), client.getBirthDate()),
                () -> assertEquals(clientRequestDTO.getEmail(), client.getEmail()),
                () -> assertEquals(clientRequestDTO.getPhoneNumber(), client.getPhoneNumber()),
                () -> assertEquals(clientRequestDTO.getPassword(), client.getPassword()),
                () -> assertEquals(clientRequestDTO.getAddress().getStreet(), client.getAddress().getStreet()),
                () -> assertEquals(clientRequestDTO.getAddress().getCity(), client.getAddress().getCity()),
                () -> assertEquals(clientRequestDTO.getAddress().getHouseNumber(), client.getAddress().getHouseNumber()),
                () -> assertEquals(clientRequestDTO.getAddress().getApartmentNumber(), client.getAddress().getApartmentNumber()),
                () -> assertEquals(clientRequestDTO.getAddress().getCountry(), client.getAddress().getCountry())
        );
    }

    @Test
    void testUpdateClientRequestDTOToClient() {
        // GIVEN
        UpdateClientRequestDTO clientRequestUpdateDTO = prepareUpdateClientRequestDTO();
        // WHEN
        Client client = ClientMapper.INSTANCE.map(clientRequestUpdateDTO);

        // THEN
        assertAll("Verify mapping properties to Client",
                () -> assertEquals(clientRequestUpdateDTO.getName(), client.getName()),
                () -> assertEquals(clientRequestUpdateDTO.getLastName(), client.getLastName()),
                () -> assertEquals(clientRequestUpdateDTO.getClientType(), client.getClientType()),
                () -> assertEquals(clientRequestUpdateDTO.getBirthDate(), client.getBirthDate()),
                () -> assertEquals(clientRequestUpdateDTO.getEmail(), client.getEmail()),
                () -> assertEquals(clientRequestUpdateDTO.getPhoneNumber(), client.getPhoneNumber())
        );
    }

    private Client prepareClient() {
        return Client.builder()
                .id(1L)
                .name("Sample First Name")
                .lastName("Sample Last Name")
                .clientType(ClientTypeDTO.PERSON)
                .birthDate(LocalDate.now())
                .email("sample.email@gmail.com")
                .phoneNumber("123456789")
                .password("samplePassword")
                .role(ClientRoleDTO.CLIENT)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .status(ClientStatusDTO.ACTIVE)
                .address(prepareAddress())
                .build();
    }

    private CreateClientRequestDTO prepareCreateClientRequestDTO() {
        return CreateClientRequestDTO.builder()
                .name("NameDTO")
                .lastName("LastNameDTO")
                .clientType(ClientTypeDTO.PERSON)
                .password("PasswordDTO!")
                .phoneNumber("123456789")
                .birthDate(LocalDate.parse("1990-04-04"))
                .address(prepareAddressRequestDTO())
                .email("sample.email@gmail.com")
                .build();
    }

    private UpdateClientRequestDTO prepareUpdateClientRequestDTO() {
        return UpdateClientRequestDTO.builder()
                .name("NameUpdated")
                .lastName("LastNameUpdated")
                .clientType(ClientTypeDTO.PERSON)
                .phoneNumber("123456789")
                .birthDate(LocalDate.parse("1990-04-04"))
                .email("sample.email@gmail.com")
                .build();
    }

    private Address prepareAddress() {
        return Address.builder()
                .id(1L)
                .city("Sample City")
                .street("Sample Street")
                .houseNumber("Sample House Number")
                .apartmentNumber("Sample Apartment Number")
                .country("Sample Country")
                .build();
    }

    private CreateAddressRequestDTO prepareAddressRequestDTO() {
        return CreateAddressRequestDTO.builder()
                .city("CityDTO")
                .street("StreetDTO")
                .country("CountryDTO")
                .houseNumber("DTO")
                .apartmentNumber("DTO")
                .build();
    }
}
