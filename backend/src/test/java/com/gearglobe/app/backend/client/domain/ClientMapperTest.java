package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.*;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientRole;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientStatus;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMapperTest {

    @Test
    void testClientToClientResponseDTO() {
        // GIVEN
        Client client = Client.builder()
                .id(1L)
                .name("Sample First Name")
                .lastName("Sample Last Name")
                .clientType(ClientType.PERSON)
                .birthDate(LocalDate.now())
                .email("sample.email@gmail.com")
                .phoneNumber("123456789")
                .password("samplePassword")
                .role(ClientRole.CLIENT)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .status(ClientStatus.ACTIVE)
                .build();

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
                () -> assertEquals(client.getRole(), clientResponseDTO.getRole()),
                () -> assertEquals(client.getStatus(), clientResponseDTO.getStatus()));
    }

    @Test
    void testClientRequestDTOToClient() {
        // GIVEN
        ClientRequestDTO clientRequestDTO = ClientRequestDTO.builder()
                .name("Sample First Name DTO")
                .lastName("Sample Last Name DTO")
                .clientType(ClientType.PERSON)
                .birthDate(LocalDate.now())
                .email("sample.email@gmail.com")
                .phoneNumber("123456789")
                .address(AddressRequestDTO.builder()
                        .city("Sample City")
                        .street("Sample Street")
                        .houseNumber("Sample House Number")
                        .apartmentNumber("Sample Apartment Number")
                        .country("Sample Country")
                        .build())
                .password("samplePassword")
                .build();

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
                () -> assertEquals(clientRequestDTO.getAddress().getStreet(), client.getAddress().getStreet())
        );
    }

    @Test
    void testClientRequestUpdateDTOToClient() {
        // GIVEN
        ClientRequestUpdateDTO clientRequestUpdateDTO = ClientRequestUpdateDTO.builder()
                .name("Sample First Name DTO")
                .lastName("Sample Last Name DTO")
                .clientType(ClientType.PERSON)
                .birthDate(LocalDate.now())
                .email("sample.email@gmail.com")
                .phoneNumber("123456789")
                .build();
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
}
