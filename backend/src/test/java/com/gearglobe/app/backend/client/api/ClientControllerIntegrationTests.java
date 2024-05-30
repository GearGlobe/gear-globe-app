//package com.gearglobe.app.backend.client.api;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gearglobe.app.backend.client.api.dtos.*;
//import com.gearglobe.app.backend.client.api.dtos.enums.ClientType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Sql(scripts = "classpath:/data/insert_clients.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
//@Sql(scripts = "classpath:/data/insert_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
//public class ClientControllerIntegrationTests {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void shouldReturnAllClients() throws Exception {
//        String response = mockMvc.perform(get(ClientController.CLIENT_URL))
//                .andReturn().getResponse().getContentAsString();
//
//        List<ClientResponseDTO> clientsResponse = objectMapper.readValue(response, new TypeReference<>() {
//        });
//
//        assertEquals(3, clientsResponse.size());
//    }
//
//
//    @Test
//    void shouldReturnClientById() throws Exception {
//        final String expectedName = "Name1";
//
//        String response = mockMvc.perform(get(ClientController.CLIENT_URL + "/1"))
//                .andReturn().getResponse().getContentAsString();
//
//        ClientResponseDTO clientResponse = objectMapper.readValue(response, ClientResponseDTO.class);
//
//        assertEquals(expectedName, clientResponse.getName());
//    }
//
//    @Test
//    @Transactional
//    void shouldCreateClient() throws Exception {
//        ClientRequestDTO testeeClient = prepareClientRequestDTO();
//
//        String response = mockMvc.perform(post(ClientController.CLIENT_URL)
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(testeeClient)))
//                .andReturn().getResponse().getContentAsString();
//
//        ClientResponseDTO clientResponse = objectMapper.readValue(response, ClientResponseDTO.class);
//
//        assertAll("Should return created client",
//                () -> assertEquals(testeeClient.getName(), clientResponse.getName()),
//                () -> assertEquals(testeeClient.getLastName(), clientResponse.getLastName()),
//                () -> assertEquals(testeeClient.getClientType(), clientResponse.getClientType()),
//                () -> assertEquals(testeeClient.getBirthDate(), clientResponse.getBirthDate()),
//                () -> assertEquals(testeeClient.getEmail(), clientResponse.getEmail()),
//                () -> assertEquals(testeeClient.getPhoneNumber(), clientResponse.getPhoneNumber()),
//                () -> assertEquals(testeeClient.getAddress().getCity(), clientResponse.getAddress().getCity()),
//                () -> assertEquals(testeeClient.getAddress().getStreet(), clientResponse.getAddress().getStreet()),
//                () -> assertEquals(testeeClient.getAddress().getCountry(), clientResponse.getAddress().getCountry()),
//                () -> assertEquals(testeeClient.getAddress().getHouseNumber(), clientResponse.getAddress().getHouseNumber()),
//                () -> assertEquals(testeeClient.getAddress().getApartmentNumber(), clientResponse.getAddress().getApartmentNumber())
//    );
//    }
//
//    @Test
//    @Transactional
//    void shouldUpdateClient() throws Exception {
//        ClientRequestUpdateDTO testeeClient = prepareClientRequestUpdateDTO();
//
//        String response = mockMvc.perform(put(ClientController.CLIENT_URL + "/1")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(testeeClient)))
//                .andReturn().getResponse().getContentAsString();
//
//        ClientResponseDTO clientResponse = objectMapper.readValue(response, ClientResponseDTO.class);
//
//        assertAll("Should return updated client",
//                () -> assertEquals(testeeClient.getName(), clientResponse.getName()),
//                () -> assertEquals(testeeClient.getLastName(), clientResponse.getLastName()),
//                () -> assertEquals(testeeClient.getClientType(), clientResponse.getClientType()),
//                () -> assertEquals(testeeClient.getBirthDate(), clientResponse.getBirthDate()),
//                () -> assertEquals(testeeClient.getEmail(), clientResponse.getEmail()),
//                () -> assertEquals(testeeClient.getPhoneNumber(), clientResponse.getPhoneNumber())
//        );
//    }
//
//    @Test
//    @Transactional
//    void shouldUpdateClientAddress() throws Exception {
//        AddressRequestDTO testeeAddress = prepareUpdatedAddressDTO();
//
//        String response = mockMvc.perform(patch(ClientController.CLIENT_URL + "/1/address")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(testeeAddress)))
//                .andReturn().getResponse().getContentAsString();
//
//        AddressRequestDTO addressResponse = objectMapper.readValue(response, AddressRequestDTO.class);
//
//        assertAll("Should return updated address",
//                () -> assertEquals(testeeAddress.getCity(), addressResponse.getCity()),
//                () -> assertEquals(testeeAddress.getStreet(), addressResponse.getStreet()),
//                () -> assertEquals(testeeAddress.getCountry(), addressResponse.getCountry()),
//                () -> assertEquals(testeeAddress.getHouseNumber(), addressResponse.getHouseNumber()),
//                () -> assertEquals(testeeAddress.getApartmentNumber(), addressResponse.getApartmentNumber())
//        );
//    }
//
//    @Test
//    @Transactional
//    void shouldChangeClientPassword() throws Exception {
//        PasswordRequestUpdateDTO testeePassword = PasswordRequestUpdateDTO.builder()
//                .oldPassword("Password1!")
//                .newPassword("newPassword1!")
//                .build();
//
//        String response = mockMvc.perform(patch(ClientController.CLIENT_URL + "/1/password")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(testeePassword)))
//                .andReturn().getResponse().getContentAsString();
//
//        Long clientId = objectMapper.readValue(response, Long.class);
//
//        assertEquals(1L, clientId);
//    }
//
//    @Test
//    @Transactional
//    void shouldDeactivateClient () throws Exception {
//        String response = mockMvc.perform(delete(ClientController.CLIENT_URL + "/1"))
//                .andReturn().getResponse().getContentAsString();
//
//        Long clientId = objectMapper.readValue(response, Long.class);
//
//        assertEquals(1L, clientId);
//    }
//
//    private ClientRequestDTO prepareClientRequestDTO() {
//        return ClientRequestDTO.builder()
//                .name("Name4")
//                .lastName("LastName4")
//                .clientType(ClientType.PERSON)
//                .password("Password4!")
//                .phoneNumber("123456789")
//                .birthDate(LocalDate.parse("1990-04-04"))
//                .address(AddressRequestDTO.builder()
//                        .city("City4")
//                        .street("Street4")
//                        .country("Country4")
//                        .houseNumber("4")
//                        .apartmentNumber("4")
//                        .build())
//                .email("email4@gmail.com")
//                .build();
//    }
//
//    private ClientRequestUpdateDTO prepareClientRequestUpdateDTO() {
//        return ClientRequestUpdateDTO.builder()
//                .name("NameUpdated")
//                .lastName("LastNameUpdated")
//                .clientType(ClientType.PERSON)
//                .phoneNumber("123456789")
//                .birthDate(LocalDate.parse("1990-04-04"))
//                .email("updatedEmail@gmail.com")
//                .build();
//    }
//
//    private AddressRequestDTO prepareUpdatedAddressDTO() {
//        return AddressRequestDTO.builder()
//                .city("CityUpdated")
//                .street("StreetUpdated")
//                .country("CountryUpdated")
//                .houseNumber("Updated")
//                .apartmentNumber("Updated")
//                .build();
//    }
//
//}
