package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    @Override
    public List<ClientGetDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::clientToClientGetDTO)
                .toList();
    }

    @Override
    public Optional<ClientGetDTO> getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return optionalClient.map(ClientMapper.INSTANCE::clientToClientGetDTO);
    }

    @Override
    public ClientPostDTO createClient(ClientPostDTO clientDTO, AddressDTO addressDTO) {
        Client client = ClientMapper.INSTANCE.clientPostDTOToClient(clientDTO);
        Address address = AddressMapper.INSTANCE.addressDTOToAddress(addressDTO);

        if (client.getClientType() == ClientType.PERSON){
            if (client.getLastName() == null || client.getBirthDate() == null){
                throw new IllegalArgumentException("Last name and birthdate are required for person type client");
            }
        }

        client.setCreateDate(LocalDate.now());
        client.setAddress(addressRepository.save(address));

        Client saveClient = clientRepository.save(client);
        return ClientMapper.INSTANCE.clientToClientPostDTO(saveClient);
    }

    @Override
    public ClientPostDTO updateClient(ClientPostDTO clientDTO) throws EntityNotFoundException {
        return clientRepository.findById(clientDTO.getId())
                .map(client -> {
                    Client newClient = ClientMapper.INSTANCE.clientPostDTOToClient(clientDTO);
                    newClient.setId(client.getId());
                    newClient.setModifyDate(LocalDate.now());
                    return clientRepository.save(newClient);
                })
                .map(ClientMapper.INSTANCE::clientToClientPostDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ClientPostDTO updateClientAddress(Long id, AddressDTO addressDTO) throws EntityNotFoundException {
        return clientRepository.findById(id)
                .map(client -> {
                    Address address = AddressMapper.INSTANCE.addressDTOToAddress(addressDTO);
                    address.setClient(client);
                    client.setAddress(address);
                    client.setModifyDate(LocalDate.now());
                    return clientRepository.save(client);
                })
                .map(ClientMapper.INSTANCE::clientToClientPostDTO)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public ClientPostDTO changeClientPassword(Long id, String password) throws EntityNotFoundException {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setPassword(password);
                    client.setModifyDate(LocalDate.now());
                    return clientRepository.save(client);
                })
                .map(ClientMapper.INSTANCE::clientToClientPostDTO)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public ClientPostDTO deactivateClient(Long id) throws EntityNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(id);

        return optionalClient.map(client -> {
            if (client.getStatus() != ClientStatus.INACTIVE) {
                client.setStatus(ClientStatus.INACTIVE);
                client.setModifyDate(LocalDate.now());
                Client archiveOffer = clientRepository.save(client);
                return ClientMapper.INSTANCE.clientToClientPostDTO(archiveOffer);
            }

            return ClientMapper.INSTANCE.clientToClientPostDTO(client);
        }).orElseThrow(EntityNotFoundException::new);
    }
}
