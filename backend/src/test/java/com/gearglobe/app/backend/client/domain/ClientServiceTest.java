package com.gearglobe.app.backend.client.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gearglobe.app.backend.client.api.dtos.*;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientRole;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientStatus;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientType;
import com.gearglobe.app.backend.configuration.exception.ClientNotFoundException;
import com.gearglobe.app.backend.configuration.exception.IncorrectClientTypeDataException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

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
                () -> assertThat(result).extracting(ClientResponseDTO::getClientType).contains(ClientType.PERSON, ClientType.COMPANY),
                () -> assertThat(result).extracting(ClientResponseDTO::getRole).contains(ClientRole.CLIENT),
                () -> assertThat(result).extracting(ClientResponseDTO::getStatus).contains(ClientStatus.ACTIVE, ClientStatus.SUSPENDED, ClientStatus.INACTIVE)

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
                () -> assertEquals(expectedClient.getRole(), result.getRole()),
                () -> assertEquals(expectedClient.getStatus(), result.getStatus())
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
        final ClientRequestDTO clientDTO = prepareClientPersonDTOToCreate();
        final AddressRequestDTO addressRequestDTO = prepareAddressDTO();
        final Client expectedClient = ClientMapper.INSTANCE.map(clientDTO);
        final Address expectedAddress = AddressMapper.INSTANCE.map(addressRequestDTO);

        // WHEN
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);
        when(addressRepository.save(any(Address.class))).thenReturn(expectedAddress);

        ClientResponseDTO result = clientService.createClient(clientDTO);

        // THEN
        assertAll("Verify returned DTO expectedClient",
                () -> assertEquals(expectedClient.getName(), result.getName()),
                () -> assertEquals(expectedClient.getLastName(), result.getLastName()),
                () -> assertEquals(expectedClient.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(expectedClient.getEmail(), result.getEmail()),
                () -> assertEquals(expectedClient.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(expectedClient.getClientType(), result.getClientType()),
                () -> assertEquals(expectedClient.getRole(), result.getRole()),
                () -> assertEquals(expectedClient.getStatus(), result.getStatus()),
                () -> assertEquals(expectedAddress.getCity(), result.getAddress().getCity()),
                () -> assertEquals(expectedAddress.getStreet(), result.getAddress().getStreet()),
                () -> assertEquals(expectedAddress.getHouseNumber(), result.getAddress().getHouseNumber()),
                () -> assertEquals(expectedAddress.getApartmentNumber(), result.getAddress().getApartmentNumber()),
                () -> assertEquals(expectedAddress.getCountry(), result.getAddress().getCountry())
        );
    }

    @Test
    void createClientCompany_ShouldReturnClientResponseDTO() {
        // GIVEN
        final ClientRequestDTO clientDTO = prepareClientCompanyDTOToCreate();
        final AddressRequestDTO addressRequestDTO = prepareAddressDTO();
        final Client expectedClient = ClientMapper.INSTANCE.map(clientDTO);
        final Address expectedAddress = AddressMapper.INSTANCE.map(addressRequestDTO);

        // WHEN
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);
        when(addressRepository.save(any(Address.class))).thenReturn(expectedAddress);

        ClientResponseDTO result = clientService.createClient(clientDTO);

        // THEN
        assertAll("Verify returned DTO expectedClient",
                () -> assertEquals(expectedClient.getName(), result.getName()),
                () -> assertNull(result.getLastName()),
                () -> assertNull(result.getBirthDate()),
                () -> assertEquals(expectedClient.getEmail(), result.getEmail()),
                () -> assertEquals(expectedClient.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(expectedClient.getClientType(), result.getClientType()),
                () -> assertEquals(expectedClient.getRole(), result.getRole()),
                () -> assertEquals(expectedClient.getStatus(), result.getStatus()),
                () -> assertEquals(expectedAddress.getCity(), result.getAddress().getCity()),
                () -> assertEquals(expectedAddress.getStreet(), result.getAddress().getStreet()),
                () -> assertEquals(expectedAddress.getHouseNumber(), result.getAddress().getHouseNumber()),
                () -> assertEquals(expectedAddress.getApartmentNumber(), result.getAddress().getApartmentNumber()),
                () -> assertEquals(expectedAddress.getCountry(), result.getAddress().getCountry())
        );
    }

    @Test
    void createInvalidClientPerson_ShouldThrowIncorrectClientTypeDataException() {
        // GIVEN
        final ClientRequestDTO clientDTO = prepareInvalidClientDTOToCreate();
        final AddressRequestDTO addressRequestDTO = prepareAddressDTO();
        final Client expectedClient = ClientMapper.INSTANCE.map(clientDTO);
        final Address expectedAddress = AddressMapper.INSTANCE.map(addressRequestDTO);

        // WHEN
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);
        when(addressRepository.save(any(Address.class))).thenReturn(expectedAddress);

        // THEN
        assertThrows(IncorrectClientTypeDataException.class, () -> clientService.createClient(clientDTO));
    }

    @Test
    void updateClient_ShouldReturnUpdatedClientResponseDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Client client = prepareClients().getFirst();
        final ClientRequestUpdateDTO clientDTO = prepareClientDTOToUpdate();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(ClientMapper.INSTANCE.map(clientDTO));

        ClientResponseDTO result = clientService.updateClient(existingId, clientDTO);

        // THEN
        assertAll("Verify returned updated DTO expectedClient",
                () -> assertEquals(clientDTO.getName(), result.getName()),
                () -> assertNotEquals(client.getName(), result.getName()),
                () -> assertEquals(clientDTO.getLastName(), result.getLastName()),
                () -> assertNotEquals(client.getLastName(), result.getLastName()),
                () -> assertEquals(clientDTO.getEmail(), result.getEmail()),
                () -> assertNotEquals(client.getEmail(), result.getEmail()),
                () -> assertEquals(clientDTO.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(clientDTO.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(clientDTO.getClientType(), result.getClientType())
        );
    }

    @Test
    void updateInvalidClient_ShouldThrowIncorrectClientTypeDataException() {
        // GIVEN
        final Long existingId = 1L;
        final Client client = prepareClients().getFirst();
        final ClientRequestUpdateDTO clientDTO = prepareInvalidClientDTOToUpdate();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(ClientMapper.INSTANCE.map(clientDTO));

        // THEN
        assertThrows(IncorrectClientTypeDataException.class, () -> clientService.updateClient(existingId, clientDTO));
    }

    @Test
    void updateClientAddress_ShouldReturnUpdatedAddressDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Address address = prepareAddress();
        final AddressRequestDTO addressRequestDTO = prepareAddressDTO();

        // WHEN
        when(addressRepository.findByClientId(existingId)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(AddressMapper.INSTANCE.map(addressRequestDTO));

        AddressResponseDTO result = clientService.updateClientAddress(existingId, addressRequestDTO);

        // THEN
        assertAll("Verify returned updated DTO address",
                () -> assertEquals(addressRequestDTO.getCity(), result.getCity()),
                () -> assertNotEquals(address.getCity(), result.getCity()),
                () -> assertEquals(addressRequestDTO.getStreet(), result.getStreet()),
                () -> assertNotEquals(address.getStreet(), result.getStreet()),
                () -> assertEquals(addressRequestDTO.getHouseNumber(), result.getHouseNumber()),
                () -> assertEquals(addressRequestDTO.getApartmentNumber(), result.getApartmentNumber()),
                () -> assertEquals(addressRequestDTO.getCountry(), result.getCountry()),
                () -> assertNotEquals(address.getCountry(), result.getCountry())
        );
    }

    @Test
    void changeClientPassword_ShouldReturnClientId() {
        // GIVEN
        final Long existingId = 1L;
        final String password = "newPassword1!";
        final Client client = prepareClients().getFirst();
        final String oldPassword = client.getPassword();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(passwordEncoder.matches(oldPassword, client.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            client.setPassword(password);
            return client;
        });

        Long result = clientService.changeClientPassword(existingId, oldPassword, password);

        // THEN
        assertAll("Verify changed password and returned client id",
                () -> assertEquals(existingId, result),
                () -> assertEquals(password, client.getPassword())
        );
    }

    @Test
    void deactivateClient_ShouldReturnClientId() {
        // GIVEN
        final Long existingId = 1L;
        final Client client = prepareClients().getFirst();

        // WHEN
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            client.setStatus(ClientStatus.INACTIVE);
            return client;
        });

        Long result = clientService.deactivateClient(existingId);

        // THEN
        assertAll("Verify changed status to INACTIVE and returned client id",
                () -> assertEquals(existingId, result),
                () -> assertEquals(ClientStatus.INACTIVE, client.getStatus())
        );
    }

    private static Address prepareAddress() {
        return new Address(1L, "City", "Street", "1", "1", "Country", prepareClients().getFirst());
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
                        .status(ClientStatus.ACTIVE)
                        .role(ClientRole.CLIENT)
                        .email("email1@gmail.com")
                        .clientType(ClientType.PERSON)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build(),
                Client.builder()
                        .id(2L)
                        .name("Name2")
                        .lastName("LastName2")
                        .birthDate(LocalDate.parse("2002-02-02"))
                        .phoneNumber("123456789")
                        .password("Password2!")
                        .status(ClientStatus.SUSPENDED)
                        .role(ClientRole.CLIENT)
                        .email("email2@gmail.com")
                        .clientType(ClientType.PERSON)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build(),
                Client.builder()
                        .id(3L)
                        .name("Name3")
                        .lastName("LastName3")
                        .birthDate(LocalDate.parse("2003-03-03"))
                        .phoneNumber("123456789")
                        .password("Password3!")
                        .status(ClientStatus.INACTIVE)
                        .role(ClientRole.CLIENT)
                        .email("email3@gmail.com")
                        .clientType(ClientType.PERSON)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build(),
                Client.builder()
                        .id(4L)
                        .name("Name4")
                        .phoneNumber("123456789")
                        .password("Password4!")
                        .status(ClientStatus.INACTIVE)
                        .role(ClientRole.CLIENT)
                        .email("email4@gmail.com")
                        .clientType(ClientType.COMPANY)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
    }

    private static ClientRequestDTO prepareClientPersonDTOToCreate() {
        return ClientRequestDTO.builder()
                .name("NameDTO")
                .lastName("LastNameDTO")
                .birthDate(LocalDate.parse("2001-01-01"))
                .email("emailDTO@gmail.com")
                .password("passwordDTO1!")
                .clientType(ClientType.PERSON)
                .phoneNumber("123456789")
                .address(prepareAddressDTO())
                .build();
    }

    private static ClientRequestDTO prepareClientCompanyDTOToCreate() {
        return ClientRequestDTO.builder()
                .name("NameDTO")
                .email("emailDTO@gmail.com")
                .password("passwordDTO1!")
                .clientType(ClientType.COMPANY)
                .phoneNumber("123456789")
                .address(prepareAddressDTO())
                .build();
    }

    private static ClientRequestDTO prepareInvalidClientDTOToCreate() {
        return ClientRequestDTO.builder()
                .name("NameDTO")
                .email("emailDTO@gmail.com")
                .password("passwordDTO1!")
                .clientType(ClientType.PERSON)
                .phoneNumber("123456789")
                .address(prepareAddressDTO())
                .build();
    }

    private static ClientRequestUpdateDTO prepareInvalidClientDTOToUpdate() {
        return ClientRequestUpdateDTO.builder()
                .name("NameDTO")
                .email("emailDTO@gmail.com")
                .clientType(ClientType.PERSON)
                .phoneNumber("123456789")
                .build();
    }

    private static AddressRequestDTO prepareAddressDTO() {
        return AddressRequestDTO.builder()
                .city("CityDTO")
                .street("StreetDTO")
                .houseNumber("1")
                .apartmentNumber("1")
                .country("CountryDTO")
                .build();
    }

    private static ClientRequestUpdateDTO prepareClientDTOToUpdate() {
        return ClientRequestUpdateDTO.builder()
                .name("UpdatedName")
                .lastName("UpdatedLastName")
                .clientType(ClientType.PERSON)
                .birthDate(LocalDate.parse("2001-01-01"))
                .phoneNumber("123456789")
                .email("updatedemail@gmail.com")
                .build();
    }
}
