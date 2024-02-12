package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientResponseDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientRequestDTO;
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

    public ClientResponseDTO updateClient(Long id, ClientRequestDTO clientDTO) throws EntityNotFoundException {
        return clientService.updateClient(id, clientDTO);
    }

    public AddressDTO updateClientAddress(Long id, AddressDTO addressDTO) throws EntityNotFoundException {
        return clientService.updateClientAddress(id, addressDTO);
    }

    public Long changeClientPassword(Long id, String password) throws EntityNotFoundException {
        return clientService.changeClientPassword(id, password);
    }

    public Long deactivateClient(Long id) throws EntityNotFoundException {
        return clientService.deactivateClient(id);
    }
}
