package com.gearglobe.app.backend.client.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gearglobe.app.backend.configuration.exception.ClientNotFoundException;
import com.gearglobe.app.backend.configuration.exception.IncorrectClientTypeDataException;
import com.gearglobe.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private ClientServiceImpl clientService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        clientRepository.saveAll(prepareClients());
        addressRepository.saveAll(prepareAddress());
    }

    @Test
    void getAllClients_ShouldReturnListOfClientResponseDTOs() {
        // GIVEN
        final List<Client> expectedClients = prepareClients();

        // WHEN
        when(clientRepository.findAll()).thenReturn(expectedClients);

        List<ClientResponseDTO> result = clientService.getAllClients();

        // THEN
        assertFalse(result.isEmpty());

        assertAll("Verify returned list of DTO client responses",
                () -> assertEquals(expectedClients.size(), result.size()),
                () -> assertThat(result).extracting(ClientResponseDTO::getName).contains("Name1", "Name2", "Name3", "Name4"),
                () -> assertThat(result).extracting(ClientResponseDTO::getLastName).contains("LastName1", "LastName2", "LastName3", null),
                () -> assertThat(result).extracting(ClientResponseDTO::getBirthDate).contains(LocalDate.parse("2001-01-01"), LocalDate.parse("2002-02-02"), LocalDate.parse("2003-03-03"), null),
                () -> assertThat(result).extracting(ClientResponseDTO::getBirthDate).contains(LocalDate.parse("2001-01-01"), LocalDate.parse("2002-02-02"), LocalDate.parse("2003-03-03")),
                () -> assertThat(result).extracting(ClientResponseDTO::getEmail).contains("email1@gmail.com", "email2@gmail.com", "email3@gmail.com", "email4@gmail.com"),
                () -> assertThat(result).extracting(ClientResponseDTO::getPhoneNumber).contains("123456789"),
                () -> assertThat(result).extracting(ClientResponseDTO::getClientType).contains(ClientTypeDTO.PERSON, ClientTypeDTO.COMPANY),
                () -> assertThat(result).extracting(ClientResponseDTO::getAddress).extracting(AddressResponseDTO::getCity).contains("City"),
                () -> assertThat(result).extracting(ClientResponseDTO::getAddress).extracting(AddressResponseDTO::getStreet).contains("Street"),
                () -> assertThat(result).extracting(ClientResponseDTO::getAddress).extracting(AddressResponseDTO::getHouseNumber).contains("1", "2", "3", "4"),
                () -> assertThat(result).extracting(ClientResponseDTO::getAddress).extracting(AddressResponseDTO::getApartmentNumber).contains("1", "2", "3", "4"),
                () -> assertThat(result).extracting(ClientResponseDTO::getAddress).extracting(AddressResponseDTO::getCountry).contains("Country")
        );
    }

    @Test
    void getClientById_ExistingId_ShouldReturnClientResponseDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Client expectedClient = prepareClients().getFirst();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(expectedClient));

        ClientResponseDTO result = clientService.getClientById(existingId);

        // THEN

        assertAll("Verify returned DTO client",
                () -> assertEquals(expectedClient.getName(), result.getName()),
                () -> assertEquals(expectedClient.getLastName(), result.getLastName()),
                () -> assertEquals(expectedClient.getClientType(), result.getClientType()),
                () -> assertEquals(expectedClient.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(expectedClient.getEmail(), result.getEmail()),
                () -> assertEquals(expectedClient.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(expectedClient.getRole(), result.getClientRole()),
                () -> assertEquals(expectedClient.getStatus(), result.getClientStatus()),
                () -> assertEquals(expectedClient.getAddress().getId(), result.getAddress().getId()),
                () -> assertEquals(expectedClient.getAddress().getCity(), result.getAddress().getCity()),
                () -> assertEquals(expectedClient.getAddress().getStreet(), result.getAddress().getStreet()),
                () -> assertEquals(expectedClient.getAddress().getHouseNumber(), result.getAddress().getHouseNumber()),
                () -> assertEquals(expectedClient.getAddress().getApartmentNumber(), result.getAddress().getApartmentNumber()),
                () -> assertEquals(expectedClient.getAddress().getCountry(), result.getAddress().getCountry())
        );
    }

    @Test
    void getOfferById_NonExistingId_ShouldThrowClientNotFoundException() {
        // GIVEN
        final Long nonExistingId = 999L;

        // WHEN
        when(clientRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(nonExistingId));
    }

    @Test
    void createClientPerson_ShouldReturnClientResponseDTO() {
        // GIVEN
        final CreateClientRequestDTO clientDTO = prepareCreateClientRequestDTO();
        final Client expectedClient = ClientMapper.INSTANCE.map(clientDTO);

        // WHEN
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);

        ClientResponseDTO result = clientService.createClient(clientDTO);

        // THEN
        assertAll("Verify returned DTO expectedClient",
                () -> assertEquals(expectedClient.getName(), result.getName()),
                () -> assertEquals(expectedClient.getLastName(), result.getLastName()),
                () -> assertEquals(expectedClient.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(expectedClient.getEmail(), result.getEmail()),
                () -> assertEquals(expectedClient.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(expectedClient.getClientType(), result.getClientType()),
                () -> assertEquals(expectedClient.getRole(), result.getClientRole()),
                () -> assertEquals(expectedClient.getStatus(), result.getClientStatus()),
                () -> assertEquals(expectedClient.getAddress().getId(), result.getAddress().getId()),
                () -> assertEquals(expectedClient.getAddress().getCity(), result.getAddress().getCity()),
                () -> assertEquals(expectedClient.getAddress().getStreet(), result.getAddress().getStreet()),
                () -> assertEquals(expectedClient.getAddress().getHouseNumber(), result.getAddress().getHouseNumber()),
                () -> assertEquals(expectedClient.getAddress().getApartmentNumber(), result.getAddress().getApartmentNumber()),
                () -> assertEquals(expectedClient.getAddress().getCountry(), result.getAddress().getCountry())
        );
    }

    @Test
    void createClientCompany_ShouldReturnClientResponseDTO() {
        // GIVEN
        final CreateClientRequestDTO clientDTO = prepareCreateClientCompanyRequestDTO();
        final Client expectedClient = ClientMapper.INSTANCE.map(clientDTO);

        // WHEN
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);

        ClientResponseDTO result = clientService.createClient(clientDTO);

        // THEN
        assertAll("Verify returned DTO expectedClient",
                () -> assertEquals(expectedClient.getName(), result.getName()),
                () -> assertNull(result.getLastName()),
                () -> assertNull(result.getBirthDate()),
                () -> assertEquals(expectedClient.getEmail(), result.getEmail()),
                () -> assertEquals(expectedClient.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(expectedClient.getClientType(), result.getClientType()),
                () -> assertEquals(expectedClient.getRole(), result.getClientRole()),
                () -> assertEquals(expectedClient.getStatus(), result.getClientStatus()),
                () -> assertEquals(expectedClient.getAddress().getId(), result.getAddress().getId()),
                () -> assertEquals(expectedClient.getAddress().getCity(), result.getAddress().getCity()),
                () -> assertEquals(expectedClient.getAddress().getStreet(), result.getAddress().getStreet()),
                () -> assertEquals(expectedClient.getAddress().getHouseNumber(), result.getAddress().getHouseNumber()),
                () -> assertEquals(expectedClient.getAddress().getApartmentNumber(), result.getAddress().getApartmentNumber()),
                () -> assertEquals(expectedClient.getAddress().getCountry(), result.getAddress().getCountry())
        );
    }

    @Test
    void createInvalidClientPerson_ShouldThrowIncorrectClientTypeDataException() {
        // GIVEN
        final CreateClientRequestDTO clientDTO = prepareInvalidCreateClientRequestDTO();
        final Client expectedClient = ClientMapper.INSTANCE.map(clientDTO);

        // WHEN
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);

        // THEN
        assertAll("Verify thrown exception for invalid client data",
                () -> assertThrows(IncorrectClientTypeDataException.class, () -> clientService.createClient(clientDTO))
        );
    }

    @Test
    void updateClient_ShouldReturnUpdatedClientResponseDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Client client = prepareClients().getFirst();
        final String clientPreviousName = client.getName();
        final String clientPreviousLastName = client.getLastName();
        final String clientPreviousEmail = client.getEmail();
        final UpdateClientRequestDTO updatedClientDTO = prepareUpdateClientRequestDTO();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(ClientMapper.INSTANCE.map(updatedClientDTO));

        ClientResponseDTO result = clientService.updateClient(existingId, updatedClientDTO);

        // THEN
        assertAll("Verify returned updated DTO expectedClient",
                () -> assertEquals(updatedClientDTO.getName(), result.getName()),
                () -> assertNotEquals(clientPreviousName, result.getName()),
                () -> assertEquals(updatedClientDTO.getLastName(), result.getLastName()),
                () -> assertNotEquals(clientPreviousLastName, result.getLastName()),
                () -> assertEquals(updatedClientDTO.getEmail(), result.getEmail()),
                () -> assertNotEquals(clientPreviousEmail, result.getEmail()),
                () -> assertEquals(updatedClientDTO.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(updatedClientDTO.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(updatedClientDTO.getClientType(), result.getClientType()),
                () -> assertEquals(client.getAddress().getId(), result.getAddress().getId()),
                () -> assertEquals(client.getAddress().getCity(), result.getAddress().getCity()),
                () -> assertEquals(client.getAddress().getStreet(), result.getAddress().getStreet()),
                () -> assertEquals(client.getAddress().getHouseNumber(), result.getAddress().getHouseNumber()),
                () -> assertEquals(client.getAddress().getApartmentNumber(), result.getAddress().getApartmentNumber()),
                () -> assertEquals(client.getAddress().getCountry(), result.getAddress().getCountry())
        );
    }

    @Test
    void updateInvalidClient_ShouldThrowIncorrectClientTypeDataException() {
        // GIVEN
        final Long existingId = 1L;
        final Client client = prepareClients().getFirst();
        final UpdateClientRequestDTO clientDTO = prepareInvalidUpdateClientRequestDTO();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(ClientMapper.INSTANCE.map(clientDTO));

        // THEN
        assertAll("Verify thrown exception for invalid client data",
                () -> assertThrows(IncorrectClientTypeDataException.class, () -> clientService.updateClient(existingId, clientDTO))
        );
    }

    @Test
    void updateClientAddress_ShouldReturnUpdatedAddressResponseDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Address address = prepareAddress().getFirst();
        final String addressPreviousCity = address.getCity();
        final String addressPreviousStreet = address.getStreet();
        final String addressPreviousCountry = address.getCountry();
        final String addressPreviousHouseNumber = address.getHouseNumber();
        final String addressPreviousApartmentNumber = address.getApartmentNumber();
        final UpdateAddressRequestDTO addressRequestDTO = prepareUpdateAddressRequestDTO();

        // WHEN
        when(addressRepository.findByClientId(existingId)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(AddressMapper.INSTANCE.map(addressRequestDTO));

        AddressResponseDTO result = clientService.updateClientAddress(existingId, addressRequestDTO);

        // THEN
        assertAll("Verify returned updated DTO address",
                () -> assertEquals(addressRequestDTO.getCity(), result.getCity()),
                () -> assertNotEquals(addressPreviousCity, result.getCity()),
                () -> assertEquals(addressRequestDTO.getStreet(), result.getStreet()),
                () -> assertNotEquals(addressPreviousStreet, result.getStreet()),
                () -> assertEquals(addressRequestDTO.getHouseNumber(), result.getHouseNumber()),
                () -> assertNotEquals(addressPreviousHouseNumber, result.getHouseNumber()),
                () -> assertEquals(addressRequestDTO.getApartmentNumber(), result.getApartmentNumber()),
                () -> assertNotEquals(addressPreviousApartmentNumber, result.getApartmentNumber()),
                () -> assertEquals(addressRequestDTO.getCountry(), result.getCountry()),
                () -> assertNotEquals(addressPreviousCountry, result.getCountry())
        );
    }

    @Test
    void changeClientPassword_ShouldReturnClientIdResponseDTO() {
        // GIVEN
        final Long existingId = 1L;
        final String newPassword = "newPassword1!";
        final Client client = prepareClients().getFirst();
        final String oldPassword = client.getPassword();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(passwordEncoder.matches(oldPassword, client.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            client.changePassword(newPassword);
            return client;
        });

        ClientIdResponseDTO result = clientService.changeClientPassword(existingId, oldPassword, newPassword);

        // THEN
        assertAll("Verify changed password and returned client id",
                () -> assertEquals(existingId, result.getId())
        );
    }

    @Test
    void deactivateClient_ShouldReturnClientIdResponseDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Client client = prepareClients().getFirst();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            client.deactivateClient();
            return client;
        });

        ClientIdResponseDTO result = clientService.deactivateClient(existingId);

        // THEN
        assertAll("Verify changed status to INACTIVE and returned client id",
                () -> assertEquals(existingId, result.getId())
        );
    }

    private static List<Client> prepareClients() {
        return List.of(
                Client.builder()
                        .id(1L)
                        .name("Name1")
                        .lastName("LastName1")
                        .birthDate(LocalDate.parse("2001-01-01"))
                        .phoneNumber("123456789")
                        .password("Password1!")
                        .status(ClientStatusDTO.ACTIVE)
                        .role(ClientRoleDTO.CLIENT)
                        .email("email1@gmail.com")
                        .clientType(ClientTypeDTO.PERSON)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .address(prepareAddress().getFirst())
                        .build(),
                Client.builder()
                        .id(2L)
                        .name("Name2")
                        .lastName("LastName2")
                        .birthDate(LocalDate.parse("2002-02-02"))
                        .phoneNumber("123456789")
                        .password("Password2!")
                        .status(ClientStatusDTO.SUSPENDED)
                        .role(ClientRoleDTO.CLIENT)
                        .email("email2@gmail.com")
                        .clientType(ClientTypeDTO.PERSON)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .address(prepareAddress().get(1))
                        .build(),
                Client.builder()
                        .id(3L)
                        .name("Name3")
                        .lastName("LastName3")
                        .birthDate(LocalDate.parse("2003-03-03"))
                        .phoneNumber("123456789")
                        .password("Password3!")
                        .status(ClientStatusDTO.INACTIVE)
                        .role(ClientRoleDTO.CLIENT)
                        .email("email3@gmail.com")
                        .clientType(ClientTypeDTO.PERSON)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .address(prepareAddress().get(2))
                        .build(),
                Client.builder()
                        .id(4L)
                        .name("Name4")
                        .phoneNumber("123456789")
                        .password("Password4!")
                        .status(ClientStatusDTO.INACTIVE)
                        .role(ClientRoleDTO.CLIENT)
                        .email("email4@gmail.com")
                        .clientType(ClientTypeDTO.COMPANY)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .address(prepareAddress().get(3))
                        .build()
        );
    }

    private static CreateClientRequestDTO prepareCreateClientRequestDTO() {
        return CreateClientRequestDTO.builder()
                .name("NameDTO")
                .lastName("LastNameDTO")
                .birthDate(LocalDate.parse("2001-01-01"))
                .email("emailDTO@gmail.com")
                .password("passwordDTO1!")
                .clientType(ClientTypeDTO.PERSON)
                .phoneNumber("123456789")
                .address(prepareCreateAddressRequestDTO())
                .build();
    }

    private static CreateClientRequestDTO prepareCreateClientCompanyRequestDTO() {
        return CreateClientRequestDTO.builder()
                .name("NameDTO")
                .email("emailDTO@gmail.com")
                .password("passwordDTO1!")
                .clientType(ClientTypeDTO.COMPANY)
                .phoneNumber("123456789")
                .address(prepareCreateAddressRequestDTO())
                .build();
    }

    private static CreateClientRequestDTO prepareInvalidCreateClientRequestDTO() {
        return CreateClientRequestDTO.builder()
                .name("NameDTO")
                .email("emailDTO@gmail.com")
                .password("passwordDTO1!")
                .clientType(ClientTypeDTO.PERSON)
                .phoneNumber("123456789")
                .address(prepareCreateAddressRequestDTO())
                .build();
    }

    private static UpdateClientRequestDTO prepareInvalidUpdateClientRequestDTO() {
        return UpdateClientRequestDTO.builder()
                .name("NameDTO")
                .email("emailDTO@gmail.com")
                .clientType(ClientTypeDTO.PERSON)
                .phoneNumber("123456789")
                .build();
    }

    private static List<Address> prepareAddress() {
        return List.of(
                Address.builder()
                        .id(1L)
                        .city("City")
                        .street("Street")
                        .houseNumber("1")
                        .apartmentNumber("1")
                        .country("Country")
                        .build(),
                Address.builder()
                        .id(2L)
                        .city("City")
                        .street("Street")
                        .houseNumber("2")
                        .apartmentNumber("2")
                        .country("Country")
                        .build(),
                Address.builder()
                        .id(3L)
                        .city("City")
                        .street("Street")
                        .houseNumber("3")
                        .apartmentNumber("3")
                        .country("Country")
                        .build(),
                Address.builder()
                        .id(4L)
                        .city("City")
                        .street("Street")
                        .houseNumber("4")
                        .apartmentNumber("4")
                        .country("Country")
                        .build()
        );
    }

    private static UpdateClientRequestDTO prepareUpdateClientRequestDTO() {
        return UpdateClientRequestDTO.builder()
                .name("UpdatedName")
                .lastName("UpdatedLastName")
                .clientType(ClientTypeDTO.PERSON)
                .birthDate(LocalDate.parse("2001-01-01"))
                .phoneNumber("123456789")
                .email("updatedemail@gmail.com")
                .build();
    }

    private static CreateAddressRequestDTO prepareCreateAddressRequestDTO() {
        return CreateAddressRequestDTO.builder()
                .city("CityDTO")
                .street("StreetDTO")
                .houseNumber("1")
                .apartmentNumber("1")
                .country("CountryDTO")
                .build();
    }

    private static UpdateAddressRequestDTO prepareUpdateAddressRequestDTO() {
        return UpdateAddressRequestDTO.builder()
                .city("UpdatedCity")
                .street("UpdatedStreet")
                .houseNumber("UpdatedNumber")
                .country("UpdatedCountry")
                .build();
    }
}
