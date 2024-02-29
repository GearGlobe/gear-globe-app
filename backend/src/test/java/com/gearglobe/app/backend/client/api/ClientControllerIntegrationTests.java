package com.gearglobe.app.backend.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientRequestDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientResponseDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "classpath:/data/insert_clients.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:/data/insert_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ClientControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllClients() throws Exception {
        String response = mockMvc.perform(get(ClientController.CLIENT_URL))
                .andReturn().getResponse().getContentAsString();

        List<ClientResponseDTO> clientsResponse = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(3, clientsResponse.size());
    }


    @Test
    void shouldReturnClientById() throws Exception {
        final String expectedName = "Name1";

        String response = mockMvc.perform(get(ClientController.CLIENT_URL + "/1"))
                .andReturn().getResponse().getContentAsString();

        ClientResponseDTO clientResponse = objectMapper.readValue(response, ClientResponseDTO.class);

        assertEquals(expectedName, clientResponse.getName());
    }

    @Test
    @Transactional
    void shouldCreateClient() throws Exception {
        ClientRequestDTO testeeClient = ClientRequestDTO.builder()
                .name("Name4")
                .lastName("LastName4")
                .clientType(ClientType.PERSON)
                .password("Password4")
                .phoneNumber("123456789")
                .birthDate(LocalDate.parse("1990-04-04"))
                .address(AddressDTO.builder()
                        .city("City4")
                        .street("Street4")
                        .houseNumber("4")
                        .apartmentNumber("4")
                        .build())
                .email("email4@gmail.com")
                .build();

        String response = mockMvc.perform(post(ClientController.CLIENT_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testeeClient)))
                .andReturn().getResponse().getContentAsString();

        ClientResponseDTO clientResponse = objectMapper.readValue(response, ClientResponseDTO.class);

        assertEquals(testeeClient.getName(), clientResponse.getName());
    }

}
