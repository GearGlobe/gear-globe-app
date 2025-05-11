package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class OfferCreatorImpl implements OfferCreator {
    private final OfferRepository offerRepository;
    private final CarOfferRepository carOfferRepository;
    private final OfferCreationStrategyRegistry strategyRegistry;

    @Override
    public Offer createOffer(CreateOfferRequestDTO createOfferRequestDTO, Long clientId) {
        OfferCreationStrategy strategy = strategyRegistry.getStrategy(createOfferRequestDTO.getClass());
        Offer offer = strategy.createOffer(createOfferRequestDTO, clientId);
        offerRepository.save(offer);
        return offer;
    }

    @Override
    public List<CarOffer> getAllCarOffers() {
        return carOfferRepository.findAll();
    }
}
