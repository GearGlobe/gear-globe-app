package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.offer.api.dtos.OfferStatus;
import com.gearglobe.dto.CreateOfferRequestDTO;
import com.gearglobe.dto.OfferIdResponseDTO;
import com.gearglobe.dto.OfferResponseDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
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
        Offer saveOffer = offerRepository.save(offer);
        return OfferMapper.INSTANCE.map(saveOffer);
    }

    @Override
    public OfferResponseDTO updateOffer(Long id, UpdateOfferRequestDTO updateOfferRequestDTO) {
        return offerRepository.findById(id)
                .map(offer -> {
                    Offer newOffer = OfferMapper.INSTANCE.map(updateOfferRequestDTO);
                    newOffer.setId(offer.getId());
                    return offerRepository.save(newOffer);
                })
                .map(OfferMapper.INSTANCE::map)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public OfferIdResponseDTO archiveOffer(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found."));
        if (offer.getStatus() != OfferStatus.ARCHIVE) {
            offer.setStatus(OfferStatus.ARCHIVE);
            offerRepository.save(offer);
        }
        return OfferIdResponseDTO.builder().id(id).build();
    }
}
