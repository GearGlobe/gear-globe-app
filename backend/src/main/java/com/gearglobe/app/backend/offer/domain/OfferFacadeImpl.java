package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferIdResponseDTO;
import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
class OfferFacadeImpl implements OfferFacade{
    private final OfferService offerService;

    @Override
    public List<OfferResponseDTO> getAllOffers() {
        return offerService.getAllOffers();
    }

    @Override
    public OfferResponseDTO getOfferById(Long id) {
        return offerService.getOfferById(id);
    }

    @Override
    public OfferResponseDTO createOffer(CreateOfferRequestDTO createOfferRequestDTO) {
        return offerService.createOffer(createOfferRequestDTO);
    }

    @Override
    public OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO) {
        return offerService.updateOffer(id, updateOfferRequestDTO);
    }

    @Override
    public OfferIdResponseDTO archiveOffer(Long id) {
        return offerService.archiveOffer(id);
    }
}
