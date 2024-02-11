package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

interface OfferService {
    List<OfferDTO> getAllOffers();
    Optional<OfferDTO> getOfferById(Long id);
    OfferDTO createOffer(OfferDTO offerDTO, Long clientId);
    OfferDTO updateOffer(OfferDTO offerDTO) throws EntityNotFoundException;
    OfferDTO archiveOffer(Long id) throws EntityNotFoundException;
}
