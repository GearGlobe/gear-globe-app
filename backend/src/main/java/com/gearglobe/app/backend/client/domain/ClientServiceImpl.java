package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ClientServiceImpl implements ClientService{
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
        return clientRepository.findById(id).map(ClientMapper.INSTANCE::map).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    @Override
    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientDTO) {
        Client client = ClientMapper.INSTANCE.map(clientDTO);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setStatus(ClientStatus.ACTIVE);

        if (client.getClientType() == ClientType.PERSON){
            if (client.getLastName() == null || client.getBirthDate() == null){
                throw new IllegalArgumentException("Last name and birthdate are required for person type client");
            }
        }

        AddressDTO addressDTO = clientDTO.getAddress();
        Address address = AddressMapper.INSTANCE.map(addressDTO);

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
                    Client newClient = ClientMapper.INSTANCE.map(clientDTO);
                    newClient.setId(client.getId());
                    newClient.setAddress(client.getAddress());
                    newClient.setPassword(client.getPassword());
                    newClient.setStatus(client.getStatus());
                    return clientRepository.save(newClient);
                })
                .map(ClientMapper.INSTANCE::map)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    @Override
    public AddressDTO updateClientAddress(Long id, AddressDTO addressDTO) {
        return addressRepository.findByClientId(id)
                .map(address -> {
                    Address newAddress = AddressMapper.INSTANCE.map(addressDTO);
                    newAddress.setId(address.getId());
                    newAddress.setClient(address.getClient());
                    return addressRepository.save(newAddress);
                })
                .map(AddressMapper.INSTANCE::map)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    public Long changeClientPassword(Long id, String password) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setPassword(passwordEncoder.encode(password));
                    clientRepository.save(client);
                    return client.getId();
                })
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
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
        }).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }
}
