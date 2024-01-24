package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OfferFacadeImpl implements OfferFacade{
    private final OfferService offerService;

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerService.getAllOffers();
    }

    @Override
    public Optional<OfferDTO> getOfferById(Long id) {
        return offerService.getOfferById(id);
    }

    @Override
    public OfferDTO createOffer(OfferDTO offerDTO) {
        return offerService.createOffer(offerDTO);
    }

    @Override
    public OfferDTO updateOffer(OfferDTO offerDTO) throws EntityNotFoundException {
        return offerService.updateOffer(offerDTO);
    }

    @Override
    public OfferDTO archiveOffer(Long id) throws EntityNotFoundException {
        return offerService.archiveOffer(id);
    }
}
