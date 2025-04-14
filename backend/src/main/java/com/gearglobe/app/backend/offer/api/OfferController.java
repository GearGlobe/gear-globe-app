package com.gearglobe.app.backend.offer.api;

import com.gearglobe.api.OfferApi;
import com.gearglobe.app.backend.offer.domain.OfferFacade;
import com.gearglobe.dto.*;
import com.gearglobe.dto.OfferResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class OfferController implements OfferApi {
    public static final String OFFER_URL = "/api/offers";

    private final OfferFacade offerFacade;

    @Override
    public ResponseEntity<List<CarOfferResponseDTO>> getAllCarOffers() {
        return ResponseEntity.ok(offerFacade.getAllCarOffers());
    }

    @Override
    public ResponseEntity<OfferResponseDTO> getOfferById(Long id) {
        return ResponseEntity.ok(offerFacade.getOfferById(id));
    }

    @Override
    public ResponseEntity<OfferIdResponseDTO> createOffer(CreateOfferRequestDTO createOfferRequestDTO) {
        return ResponseEntity.ok(offerFacade.createOffer(createOfferRequestDTO));
    }

    @Override
    public ResponseEntity<OfferResponseDTO> updateOfferById(Long id, UpdateOfferRequestDTO updateOfferRequestDTO) {
        return ResponseEntity.ok(offerFacade.updateOffer(id, updateOfferRequestDTO));
    }

    @Override
    public ResponseEntity<OfferIdResponseDTO> archiveOfferById(Long id) {
        return ResponseEntity.ok(offerFacade.archiveOffer(id));
    }
}
