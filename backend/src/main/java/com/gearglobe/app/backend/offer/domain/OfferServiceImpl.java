package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Override
    public List<OfferResponseDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper.INSTANCE::map)
                .toList();
    }

    @Override
    public OfferResponseDTO getOfferById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + id));
        return OfferMapper.INSTANCE.map(offer);
    }

    @Override
    public OfferResponseDTO createOffer(CreateOfferRequestDTO createOfferRequestDTO) {
        Offer offer = OfferMapper.INSTANCE.map(createOfferRequestDTO);
        offer.setStatus(OfferStatusDTO.ACTIVE);
        offer.setClientId(666L); //TODO
        Offer saveOffer = offerRepository.save(offer);
        return OfferMapper.INSTANCE.map(saveOffer);
    }

    @Override
    public OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        offer.updateOffer(updateOfferRequestDTO);
        offerRepository.save(offer);
        return OfferMapper.INSTANCE.map(offer);
    }

    @Override
    public OfferIdResponseDTO archiveOffer(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found."));
        if (offer.getStatus() != OfferStatusDTO.ARCHIVE) {
            offer.setStatus(OfferStatusDTO.ARCHIVE);
            offerRepository.save(offer);
        }
        return OfferIdResponseDTO.builder().id(id).build();
    }
}
