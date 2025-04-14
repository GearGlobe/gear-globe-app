package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.OfferTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class OfferResponseCreatorImpl implements OfferResponseCreator {
    private final CarOfferRepository carOfferRepository;

    @Override
    public OfferResponseDTO createOfferResponseByType(OfferTypeDTO offerType, Long offerId) {
        switch (offerType) {
            case CAR -> {
                CarOffer carOffer = carOfferRepository.findByOfferId(offerId);
                return OfferMapper.INSTANCE.map(carOffer);
            }
            default -> throw new IllegalArgumentException("Unknown offer type: " + offerType);
        }
    }
}
