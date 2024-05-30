package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.*;
import com.gearglobe.app.backend.configuration.exception.AddressNotFoundException;
import com.gearglobe.app.backend.configuration.exception.ClientNotFoundException;
import com.gearglobe.app.backend.configuration.exception.IncorrectClientTypeDataException;
import com.gearglobe.app.backend.configuration.exception.IncorrectPasswordException;
import com.gearglobe.dto.ClientIdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Client client = findClientById(id);
        return ClientMapper.INSTANCE.map(client);
    }

    @Override
    @Transactional
    public ClientResponseDTO createClient(CreateClientRequestDTO createClientRequestDTO) {
        Client clientToCreate = ClientMapper.INSTANCE.map(createClientRequestDTO);

        validateNewPassword(clientToCreate.getPassword());
        validatePerson(clientToCreate);

        clientToCreate.changePassword(encodePassword(clientToCreate.getPassword()));
        clientToCreate.setBasicClientData();
        Client client = clientRepository.save(clientToCreate);

        Address addressToCreate = AddressMapper.INSTANCE.map(createClientRequestDTO.getAddress());
        Address address = addressRepository.save(addressToCreate);

        client.assignAddress(address);
        address.assignClient(client);
        return ClientMapper.INSTANCE.map(client);
    }

    @Override
    public ClientResponseDTO updateClient(Long id, UpdateClientRequestDTO updateClientRequestDTO) {
        Client clientToUpdate = ClientMapper.INSTANCE.map(updateClientRequestDTO);

        validatePerson(clientToUpdate);

        Client client = findClientById(id);
        client.updateClient(updateClientRequestDTO);
        clientRepository.save(client);
        return ClientMapper.INSTANCE.map(client);
    }

    @Override
    public AddressResponseDTO updateClientAddress(Long id, UpdateAddressRequestDTO updateAddressRequestDTO) {
        Address address = findAddressByClientId(id);
        address.updateAddress(updateAddressRequestDTO);
        addressRepository.save(address);
        return AddressMapper.INSTANCE.map(address);
    }

    @Override
    public ClientIdResponseDTO changeClientPassword(Long id, String oldPasswordToCheck, String password) {
        Client client = findClientById(id);

        validateOldPassword(client, oldPasswordToCheck);
        validateNewPassword(password);

        client.changePassword(encodePassword(password));
        clientRepository.save(client);
        return ClientIdResponseDTO.builder().id(id).build();
    }

    @Override
    public ClientIdResponseDTO deactivateClient(Long id) {
        Client client = findClientById(id);

        if (client.isActive()){
            client.deactivateClient();
            clientRepository.save(client);
        }

        return ClientIdResponseDTO.builder().id(id).build();
    }

    private Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
    }

    private Address findAddressByClientId(Long id) {
        return addressRepository.findByClientId(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found for client with id: " + id));
    }

    private void validateNewPassword(String password) {
        if (Client.isPasswordNotValid(password)) {
            throw new IncorrectPasswordException("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character!");
        }
    }

    private void validateOldPassword(Client client, String oldPasswordToCheck) {
        if (!passwordEncoder.matches(oldPasswordToCheck, client.getPassword())) {
            throw new IncorrectPasswordException("Old password is incorrect!");
        }
    }

    private void validatePerson(Client client) {
        if (Client.isPersonNotValid(client)) {
            throw new IncorrectClientTypeDataException("Last name and birth date are required for person type!");
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
