package com.gearglobe.app.backend.client.api;

import com.gearglobe.app.backend.client.api.dtos.AddressDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientGetDTO;
import com.gearglobe.app.backend.client.api.dtos.ClientPostDTO;
import com.gearglobe.app.backend.client.domain.ClientFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ClientController.CLIENT_URL)
@RequiredArgsConstructor
class ClientController {
    public static final String CLIENT_URL = "/api/clients";
    public final ClientFacade clientFacade;

    @GetMapping
    public List<ClientGetDTO> getAllClients() {
        return clientFacade.getAllClients();
    }

    @GetMapping("/{id}")
    public Optional<ClientGetDTO> getClientById(@PathVariable Long id) {
        return clientFacade.getClientById(id);
    }

    @PostMapping
    public ClientPostDTO createClient(@RequestBody ClientPostDTO clientDTO, @RequestBody AddressDTO addressDTO) {
        return clientFacade.createClient(clientDTO, addressDTO);
    }

    @PutMapping("/{id}")
    public ClientPostDTO updateClient(@RequestBody ClientPostDTO clientDTO) {
        return clientFacade.updateClient(clientDTO);
    }

    @PatchMapping("/{id}/address")
    public ClientPostDTO updateClientAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return clientFacade.updateClientAddress(id, addressDTO);
    }

    @PatchMapping("/{id}/password")
    public ClientPostDTO changeClientPassword(@PathVariable Long id, @RequestBody String password) {
        return clientFacade.changeClientPassword(id, password);
    }

    @DeleteMapping("/{id}")
    public ClientPostDTO deactivateClient(@PathVariable Long id) {
        return clientFacade.deactivateClient(id);
    }
}

