package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.offer.api.dtos.OfferDTO;
import com.gearglobe.app.backend.offer.api.dtos.OfferStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper.INSTANCE::offerToOfferDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OfferDTO> getOfferById(Long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);

        if (optionalOffer.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return optionalOffer.map(OfferMapper.INSTANCE::offerToOfferDTO);
    }

    @Override
    public OfferDTO createOffer(OfferDTO offerDTO, Long clientId) {
        Offer offer = OfferMapper.INSTANCE.offerDTOToOffer(offerDTO);
        offer.setClientId(clientId);
        Offer saveOffer = offerRepository.save(offer);
        return OfferMapper.INSTANCE.offerToOfferDTO(saveOffer);
    }

    @Override
    public OfferDTO updateOffer(OfferDTO offerDTO) {
        return offerRepository.findById(offerDTO.getId())
                .map(offer -> {
                    Offer newOffer = OfferMapper.INSTANCE.offerDTOToOffer(offerDTO);
                    newOffer.setId(offer.getId());
                    return offerRepository.save(newOffer);
                })
                .map(OfferMapper.INSTANCE::offerToOfferDTO)
                .orElseThrow(() -> new EntityNotFoundException("test"));
    }

    @Override
    public OfferDTO archiveOffer(Long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);

        return optionalOffer.map(offer -> {
            if (offer.getStatus() != OfferStatus.ARCHIVE) {
                offer.setStatus(OfferStatus.ARCHIVE);
                Offer archiveOffer = offerRepository.save(offer);
                return OfferMapper.INSTANCE.offerToOfferDTO(archiveOffer);
            }

            return OfferMapper.INSTANCE.offerToOfferDTO(offer);
        }).orElseThrow(EntityNotFoundException::new);
    }
}
