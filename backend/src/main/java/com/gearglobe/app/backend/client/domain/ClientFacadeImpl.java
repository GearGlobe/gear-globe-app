package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientGetDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientPostDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientFacadeImpl implements ClientFacade{
    private final ClientService clientService;


    public List<ClientGetDTO> getAllClients() {
        return clientService.getAllClients();
    }

    public Optional<ClientGetDTO> getClientById(Long id) {
        return clientService.getClientById(id);
    }

    public ClientPostDTO createClient(ClientPostDTO clientDTO, AddressDTO addressDTO) {
        return clientService.createClient(clientDTO, addressDTO);
    }

    public ClientPostDTO updateClient(ClientPostDTO clientDTO) throws EntityNotFoundException {
        return clientService.updateClient(clientDTO);
    }

    public ClientPostDTO updateClientAddress(Long id, AddressDTO addressDTO) throws EntityNotFoundException {
        return clientService.updateClientAddress(id, addressDTO);
    }

    public ClientPostDTO changeClientPassword(Long id, String password) throws EntityNotFoundException {
        return clientService.changeClientPassword(id, password);
    }

    public ClientPostDTO deactivateClient(Long id) throws EntityNotFoundException {
        return clientService.deactivateClient(id);
    }
}
