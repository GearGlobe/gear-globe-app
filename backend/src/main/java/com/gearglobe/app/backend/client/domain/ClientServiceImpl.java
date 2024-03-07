package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.*;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientRole;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientStatus;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientType;
import com.gearglobe.app.backend.configuration.exception.AddressNotFoundException;
import com.gearglobe.app.backend.configuration.exception.ClientNotFoundException;
import com.gearglobe.app.backend.configuration.exception.IncorrectClientTypeDataException;
import com.gearglobe.app.backend.configuration.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<ClientResponseDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::map)
                .toList();
    }

    @Override
    public ClientResponseDTO getClientById(Long id) {
        return clientRepository.findById(id)
                .map(ClientMapper.INSTANCE::map)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    @Override
    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientDTO) {
        Client client = ClientMapper.INSTANCE.map(clientDTO);
        if (isPasswordNotValid(client.getPassword())) {
            throw new IncorrectPasswordException("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character!");
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setStatus(ClientStatus.ACTIVE);
        client.setRole(ClientRole.CLIENT);

        if (isPersonNotValid(client)) {
            throw new IncorrectClientTypeDataException("Last name and birth date are required for person type!");
        }

        AddressRequestDTO addressRequestDTO = clientDTO.getAddress();
        Address address = AddressMapper.INSTANCE.map(addressRequestDTO);

        address.setClient(client);
        Address saveAddress = addressRepository.save(address);

        client.setAddress(saveAddress);
        Client saveClient = clientRepository.save(client);

        return ClientMapper.INSTANCE.map(saveClient);
    }

    @Override
    public ClientResponseDTO updateClient(Long id, ClientRequestUpdateDTO clientDTO) {
        return clientRepository.findById(id)
                .map(client -> {
                    Client updatedClient = ClientMapper.INSTANCE.map(clientDTO);

                    if (isPersonNotValid(updatedClient)) {
                        throw new IncorrectClientTypeDataException("Last name and birth date are required for person type!");
                    }

                    updatedClient.setId(client.getId());
                    updatedClient.setAddress(client.getAddress());
                    updatedClient.setPassword(client.getPassword());
                    updatedClient.setStatus(client.getStatus());
                    updatedClient.setRole(client.getRole());
                    return clientRepository.save(updatedClient);
                })
                .map(ClientMapper.INSTANCE::map)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    @Override
    public AddressResponseDTO updateClientAddress(Long id, AddressRequestDTO addressRequestDTO) {
        return addressRepository.findByClientId(id)
                .map(address -> {
                    Address newAddress = AddressMapper.INSTANCE.map(addressRequestDTO);
                    newAddress.setId(address.getId());
                    newAddress.setClient(address.getClient());
                    return addressRepository.save(newAddress);
                })
                .map(AddressMapper.INSTANCE::map)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }

    @Override
    public Long changeClientPassword(Long id, String oldPasswordToCheck, String password) {
        if (isPasswordNotValid(password)) {
            throw new IncorrectPasswordException("New password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character!");
        }

        return clientRepository.findById(id)
                .map(client -> {
                    if (!passwordEncoder.matches(oldPasswordToCheck, client.getPassword())) {
                        throw new IncorrectPasswordException("Old password is incorrect!");
                    }
                    client.setPassword(passwordEncoder.encode(password));
                    clientRepository.save(client);
                    return client.getId();
                })
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    @Override
    public Long deactivateClient(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        return optionalClient.map(client -> {
            if (client.getStatus() != ClientStatus.INACTIVE) {
                client.setStatus(ClientStatus.INACTIVE);
                Client deactivatedClient = clientRepository.save(client);
                return deactivatedClient.getId();
            }

            return client.getId();
        }).orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    private boolean isPersonNotValid(Client client) {
        return client.getClientType() == ClientType.PERSON && (Objects.isNull(client.getLastName()) || Objects.isNull(client.getBirthDate()));
    }

    private boolean isPasswordNotValid(String password) {
        return !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)(?=.*[!@#$%^&*/\\\\()\\-_=+]).{8,}$");
    }
}
