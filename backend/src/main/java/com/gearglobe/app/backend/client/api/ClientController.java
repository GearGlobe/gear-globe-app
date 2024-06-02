package com.gearglobe.app.backend.client.api;

import com.gearglobe.api.ClientApi;
import com.gearglobe.dto.*;
import com.gearglobe.app.backend.client.domain.ClientFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class ClientController implements ClientApi {
    public static final String CLIENT_URL = "/api/clients";
    public final ClientFacade clientFacade;

    @Override
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        return ResponseEntity.ok(clientFacade.getAllClients());
    }

    @Override
    public ResponseEntity<ClientResponseDTO> getClientById(Long id) {
        return ResponseEntity.ok(clientFacade.getClientById(id));
    }

    @Override
    public ResponseEntity<ClientResponseDTO> createClient(@Valid CreateClientRequestDTO clientDTO) {
        return ResponseEntity.ok(clientFacade.createClient(clientDTO));
    }

    @Override
    public ResponseEntity<ClientResponseDTO> updateClientById(Long id, @Valid UpdateClientRequestDTO clientDTO) {
        return ResponseEntity.ok(clientFacade.updateClient(id, clientDTO));
    }

    @Override
    public ResponseEntity<AddressResponseDTO> updateClientAddress(Long id, @Valid UpdateAddressRequestDTO addressRequestDTO) {
        return ResponseEntity.ok(clientFacade.updateClientAddress(id, addressRequestDTO));
    }

    @Override
    public ResponseEntity<ClientIdResponseDTO> changeClientPassword(Long id, @Valid UpdatePasswordRequestDTO passwordDTO) {
        return ResponseEntity.ok(clientFacade.changeClientPassword(id, passwordDTO.getOldPassword(), passwordDTO.getNewPassword()));
    }

    @Override
    public ResponseEntity<ClientIdResponseDTO> deactivateClientById(Long id) {
        return ResponseEntity.ok(clientFacade.deactivateClient(id));
    }

//
//    @GetMapping
//    public List<ClientResponseDTO> getAllClients() {
//        return clientFacade.getAllClients();
//    }
//
//    @GetMapping("/{id}")
//    public ClientResponseDTO getClientById(@PathVariable Long id) {
//        return clientFacade.getClientById(id);
//    }
//
//    @PostMapping
//    public ClientResponseDTO createClient(@Valid @RequestBody CreateClientRequestDTO clientDTO) {
//        return clientFacade.createClient(clientDTO);
//    }
//
//    @PutMapping("/{id}")
//    public ClientResponseDTO updateClient(@PathVariable Long id , @Valid @RequestBody UpdateClientRequestDTO clientDTO) {
//        return clientFacade.updateClient(id, clientDTO);
//    }
//
//    @PatchMapping("/{id}/address")
//    public AddressResponseDTO updateClientAddress(@PathVariable Long id, @Valid @RequestBody UpdateAddressRequestDTO addressRequestDTO) {
//        return clientFacade.updateClientAddress(id, addressRequestDTO);
//    }
//
//    @PatchMapping("/{id}/password")
//    public ClientIdResponseDTO changeClientPassword(@PathVariable Long id, @Valid @RequestBody UpdatePasswordRequestDTO passwordDTO){
//        return clientFacade.changeClientPassword(id, passwordDTO.getOldPassword(), passwordDTO.getNewPassword());
//    }
//
//    @DeleteMapping("/{id}")
//    public ClientIdResponseDTO deactivateClient(@PathVariable Long id) {
//        return clientFacade.deactivateClient(id);
//    }
}

