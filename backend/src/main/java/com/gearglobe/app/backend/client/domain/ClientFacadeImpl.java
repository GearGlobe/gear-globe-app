package com.gearglobe.app.backend.client.domain;

import com.gearglobe.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ClientFacadeImpl implements ClientFacade{
    private final ClientService clientService;

    public List<ClientResponseDTO> getAllClients() {
        return clientService.getAllClients();
    }

    public ClientResponseDTO getClientById(Long id) {
        return clientService.getClientById(id);
    }

    public ClientResponseDTO createClient(CreateClientRequestDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }

    public ClientResponseDTO updateClient(Long id, UpdateClientRequestDTO clientDTO) {
        return clientService.updateClient(id, clientDTO);
    }

    public AddressResponseDTO updateClientAddress(Long id, UpdateAddressRequestDTO addressRequestDTO) {
        return clientService.updateClientAddress(id, addressRequestDTO);
    }

    public ClientIdResponseDTO changeClientPassword(Long id, String oldPasswordToCheck, String password) {
        return clientService.changeClientPassword(id, oldPasswordToCheck, password);
    }

    public ClientIdResponseDTO deactivateClient(Long id) throws EntityNotFoundException {
        return clientService.deactivateClient(id);
    }
}
