package com.gearglobe.app.backend.offer.domain;

import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

interface OfferService {
    List<OfferDTO> getAllOffers();
    Optional<OfferDTO> getOfferById(Long id);
    OfferDTO createOffer(OfferDTO offerDTO);
    OfferDTO updateOffer(OfferDTO offerDTO) throws ChangeSetPersister.NotFoundException;
    OfferDTO archiveOffer(Long id) throws ChangeSetPersister.NotFoundException;
}
