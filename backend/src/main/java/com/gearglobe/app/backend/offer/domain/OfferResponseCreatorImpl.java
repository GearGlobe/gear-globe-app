package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.OfferResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class OfferResponseCreatorImpl implements OfferResponseCreator {
    private final OfferMappingStrategyRegistry offerMappingStrategyRegistry;

    @Override
    public OfferResponseDTO createOfferResponse(Offer offer) {
        OfferMappingStrategy<Offer, ?> strategy = (OfferMappingStrategy<Offer, ?>) offerMappingStrategyRegistry.getStrategy(offer.getClass());
        return strategy.map(offer);
    }
}
