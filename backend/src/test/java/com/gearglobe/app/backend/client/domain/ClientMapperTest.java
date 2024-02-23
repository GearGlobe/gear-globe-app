package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMapperTest {

    @Test
    void testClientToClientResponseDTO() {
        // Given
        Client client = new Client();
        client.setId(1L);
        client.setName("Sample First Name");
        client.setLastName("Sample Last Name");
        client.setClientType(ClientType.PERSON);
        client.setBirthDate(LocalDate.now());
        client.setEmail("sample.email@gmail.com");
        client.setPhoneNumber("123456789");
        client.setPassword("samplePassword");
        client.setRole("sampleRole");
        client.setCreatedDate(LocalDateTime.now());
        client.setModifiedDate(LocalDateTime.now());
        client.setStatus(ClientStatus.ACTIVE);

        // When
        ClientResponseDTO clientResponseDTO = ClientMapper.INSTANCE.map(client);

        // Then
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
        // Given
        ClientRequestDTO clientRequestDTO = ClientRequestDTO.builder()
                .name("Sample First Name DTO")
                .lastName("Sample Last Name DTO")
                .clientType(ClientType.PERSON)
                .birthDate(LocalDate.now())
                .email("sample.email@gmail.com")
                .phoneNumber("123456789")
                .address(AddressDTO.builder()
                        .city("Sample City")
                        .street("Sample Street")
                        .houseNumber("Sample House Number")
                        .apartmentNumber("Sample Apartment Number")
                        .country("Sample Country")
                        .build())
                .password("samplePassword")
                .role("sampleRole")
                .build();

        // When
        Client client = ClientMapper.INSTANCE.map(clientRequestDTO);

        // Then
        assertAll("Verify mapping properties to Client",
                () -> assertEquals(clientRequestDTO.getName(), client.getName()),
                () -> assertEquals(clientRequestDTO.getLastName(), client.getLastName()),
                () -> assertEquals(clientRequestDTO.getClientType(), client.getClientType()),
                () -> assertEquals(clientRequestDTO.getBirthDate(), client.getBirthDate()),
                () -> assertEquals(clientRequestDTO.getEmail(), client.getEmail()),
                () -> assertEquals(clientRequestDTO.getPhoneNumber(), client.getPhoneNumber()),
                () -> assertEquals(clientRequestDTO.getPassword(), client.getPassword()),
                () -> assertEquals(clientRequestDTO.getAddress().getStreet(), client.getAddress().getStreet()),
                () -> assertEquals(clientRequestDTO.getRole(), client.getRole()));
    }

    @Test
    void testClientRequestUpdateDTOToClient() {
        // Given
        ClientRequestUpdateDTO clientRequestUpdateDTO = ClientRequestUpdateDTO.builder()
                .name("Sample First Name DTO")
                .lastName("Sample Last Name DTO")
                .clientType(ClientType.PERSON)
                .birthDate(LocalDate.now())
                .email("sample.email@gmail.com")
                .phoneNumber("123456789")
                .role("sampleRole")
                .build();
        // When
        Client client = ClientMapper.INSTANCE.map(clientRequestUpdateDTO);

        // Then
        assertAll("Verify mapping properties to Client",
                () -> assertEquals(clientRequestUpdateDTO.getName(), client.getName()),
                () -> assertEquals(clientRequestUpdateDTO.getLastName(), client.getLastName()),
                () -> assertEquals(clientRequestUpdateDTO.getClientType(), client.getClientType()),
                () -> assertEquals(clientRequestUpdateDTO.getBirthDate(), client.getBirthDate()),
                () -> assertEquals(clientRequestUpdateDTO.getEmail(), client.getEmail()),
                () -> assertEquals(clientRequestUpdateDTO.getPhoneNumber(), client.getPhoneNumber()),
                () -> assertEquals(clientRequestUpdateDTO.getRole(), client.getRole()));
    }
}
