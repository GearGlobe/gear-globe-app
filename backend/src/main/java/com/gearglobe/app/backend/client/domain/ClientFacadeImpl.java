package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientFacadeImpl implements ClientFacade{
    private final ClientService clientService;

    public List<ClientResponseDTO> getAllClients() {
        return clientService.getAllClients();
    }

    public ClientResponseDTO getClientById(Long id) {
        return clientService.getClientById(id);
    }

    public ClientResponseDTO createClient(ClientRequestDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }

    public ClientResponseDTO updateClient(Long id, ClientRequestUpdateDTO clientDTO) throws EntityNotFoundException {
        return clientService.updateClient(id, clientDTO);
    }

    public AddressResponseDTO updateClientAddress(Long id, AddressRequestDTO addressRequestDTO) throws EntityNotFoundException {
        return clientService.updateClientAddress(id, addressRequestDTO);
    }

    public Long changeClientPassword(Long id, String oldPasswordToCheck, String password) throws EntityNotFoundException {
        return clientService.changeClientPassword(id, oldPasswordToCheck, password);
    }

    public Long deactivateClient(Long id) throws EntityNotFoundException {
        return clientService.deactivateClient(id);
    }
}
