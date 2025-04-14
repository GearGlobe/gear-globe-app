package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class OfferCreatorImpl implements OfferCreator {
    private final CarOfferRepository carOfferRepository;
    @Override
    public Offer createOffer(CreateOfferRequestDTO createOfferRequestDTO, Long clientId) {
        OfferTypeDTO offerType = createOfferRequestDTO.getCreateBaseOfferRequest().getOfferType();
        switch (offerType) {
            case CAR -> {
                if (createOfferRequestDTO.getCreateCarOfferRequest() == null) {
                    throw new IllegalArgumentException("Car offer dto is missing");
                }
                CarOffer carOffer = carOfferRepository.save(CarOffer.createOffer(createOfferRequestDTO.getCreateCarOfferRequest(), createOfferRequestDTO.getCreateBaseOfferRequest(), clientId));
                return carOffer.getOffer();
            }
            default -> throw new IllegalArgumentException("Unknown offer type: " + offerType);
        }
    }

    @Override
    public List<CarOffer> getAllCarOffers() {
        return carOfferRepository.findAll();
    }
}
