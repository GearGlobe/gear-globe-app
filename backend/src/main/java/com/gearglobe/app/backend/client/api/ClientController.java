package com.gearglobe.app.backend.client.api;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientResponseDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientRequestDTO;
import com.gearglobe.app.backend.client.domain.ClientFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ClientController.CLIENT_URL)
@RequiredArgsConstructor
class ClientController {
    public static final String CLIENT_URL = "/api/clients";
    public final ClientFacade clientFacade;

    @GetMapping
    public List<ClientResponseDTO> getAllClients() {
        return clientFacade.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientResponseDTO getClientById(@PathVariable Long id) {
        return clientFacade.getClientById(id);
    }

    @PostMapping
    public ClientResponseDTO createClient(@Valid @RequestBody ClientRequestDTO clientDTO) {
        return clientFacade.createClient(clientDTO);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO updateClient(@PathVariable Long id ,@Valid @RequestBody ClientRequestDTO clientDTO) {
        return clientFacade.updateClient(id, clientDTO);
    }

    @PatchMapping("/{id}/address/")
    public AddressDTO updateClientAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO addressDTO) {
        return clientFacade.updateClientAddress(id, addressDTO);
    }

    @PatchMapping("/{id}/password")
    public Long changeClientPassword(@PathVariable Long id, @Valid @RequestBody String password) {
        return clientFacade.changeClientPassword(id, password);
    }

    @DeleteMapping("/{id}")
    public Long deactivateClient(@PathVariable Long id) {
        return clientFacade.deactivateClient(id);
    }
}

