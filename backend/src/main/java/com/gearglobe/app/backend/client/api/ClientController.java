package com.gearglobe.app.backend.client.api;

import com.gearglobe.dto.*;
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
    public ClientResponseDTO createClient(@Valid @RequestBody CreateClientRequestDTO clientDTO) {
        return clientFacade.createClient(clientDTO);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO updateClient(@PathVariable Long id , @Valid @RequestBody UpdateClientRequestDTO clientDTO) {
        return clientFacade.updateClient(id, clientDTO);
    }

    @PatchMapping("/{id}/address")
    public AddressResponseDTO updateClientAddress(@PathVariable Long id, @Valid @RequestBody UpdateAddressRequestDTO addressRequestDTO) {
        return clientFacade.updateClientAddress(id, addressRequestDTO);
    }

    @PatchMapping("/{id}/password")
    public ClientIdResponseDTO changeClientPassword(@PathVariable Long id, @Valid @RequestBody UpdatePasswordRequestDTO passwordDTO){
        return clientFacade.changeClientPassword(id, passwordDTO.getOldPassword(), passwordDTO.getNewPassword());
    }

    @DeleteMapping("/{id}")
    public ClientIdResponseDTO deactivateClient(@PathVariable Long id) {
        return clientFacade.deactivateClient(id);
    }
}

