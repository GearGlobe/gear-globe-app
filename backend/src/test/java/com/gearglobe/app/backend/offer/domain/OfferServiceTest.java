package com.gearglobe.app.backend.offer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gearglobe.app.backend.configuration.exception.OfferNotFoundException;
import com.gearglobe.dto.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {
    @Mock
    private OfferRepository offerRepository;
    @InjectMocks
    private OfferServiceImpl offerService;

    @BeforeEach
    public void setup(){
        offerRepository.saveAll(prepareOffers());
    }

    @Test
    void getAllOffers_ShouldReturnListOfOfferDTOs() {
        // GIVEN
        final List<Offer> expectedOffers = prepareOffers();

        // WHEN
        when(offerRepository.findAll()).thenReturn(expectedOffers);

        List<OfferResponseDTO> result = offerService.getAllOffers();

        // THEN
        assertFalse(result.isEmpty());

        assertAll("Verify returned list of DTO offers",
                () -> assertEquals(expectedOffers.size(), result.size()),
                () -> assertThat(result).extracting(OfferResponseDTO::getId).contains(1L, 2L, 3L),
                () -> assertThat(result).extracting(OfferResponseDTO::getMark).contains("Mark1", "Mark2", "Mark3"),
                () -> assertThat(result).extracting(OfferResponseDTO::getProductionYear).contains(2010L, 2011L, 2012L),
                () -> assertThat(result).extracting(OfferResponseDTO::getMillage).contains(100000L, 100001L, 100002L),
                () -> assertThat(result).extracting(OfferResponseDTO::getEngineCapacity).contains(2.0, 2.1, 2.2),
                () -> assertThat(result).extracting(OfferResponseDTO::getDescription).contains("Description1", "Description2", "Description3"),
                () -> assertThat(result).extracting(OfferResponseDTO::getPrice).contains(10000.0, 10001.0, 10002.0),
                () -> assertThat(result).extracting(OfferResponseDTO::getCreateDate).isNotNull(),
                () -> assertThat(result).extracting(OfferResponseDTO::getStatus).contains(OfferStatusDTO.ACTIVE, OfferStatusDTO.ACTIVE, OfferStatusDTO.ACTIVE)
        );
    }

    @Test
    void getOfferById_ExistingId_ShouldReturnOfferDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Offer expectedOffer = prepareOffers().getFirst();


        // WHEN
        when(offerRepository.findById(existingId)).thenReturn(Optional.of(expectedOffer));

        OfferResponseDTO result = offerService.getOfferById(existingId);

        // THEN
        assertAll("Verify returned DTO offer",
                () -> assertEquals(expectedOffer.getId(), result.getId()),
                () -> assertEquals(expectedOffer.getMark(), result.getMark()),
                () -> assertEquals(expectedOffer.getProductionYear(), result.getProductionYear()),
                () -> assertEquals(expectedOffer.getMillage(), result.getMillage()),
                () -> assertEquals(expectedOffer.getEngineCapacity(), result.getEngineCapacity()),
                () -> assertEquals(expectedOffer.getDescription(), result.getDescription()),
                () -> assertEquals(expectedOffer.getPrice(), result.getPrice()),
                () -> assertEquals(expectedOffer.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(expectedOffer.getStatus(), result.getStatus())
        );
    }

    @Test
    void getOfferById_NonExistingId_ShouldThrowOfferNotFoundException() {
        // GIVEN
        final Long nonExistingId = 999L;

        // WHEN
        when(offerRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(OfferNotFoundException.class, () -> offerService.getOfferById(nonExistingId));
    }

    @Test
    void createOffer_ShouldReturnOfferDTO() {
        // GIVEN
        final CreateOfferRequestDTO offerDTO = prepareOfferDTOToCreate();
        final Offer expectedOffer = prepareOffers().getFirst();
        final Long clientId = 1L;

        // WHEN
        when(offerRepository.save(any(Offer.class))).thenReturn(expectedOffer);

        OfferResponseDTO result = offerService.createOffer(offerDTO);

        // THEN
        assertAll("Verify returned DTO offer",
                () -> assertNotNull(result.getId()),
                () -> assertEquals(offerDTO.getMark(), result.getMark()),
                () -> assertEquals(offerDTO.getProductionYear(), result.getProductionYear()),
                () -> assertEquals(offerDTO.getMillage(), result.getMillage()),
                () -> assertEquals(offerDTO.getEngineCapacity(), result.getEngineCapacity()),
                () -> assertEquals(offerDTO.getDescription(), result.getDescription()),
                () -> assertEquals(offerDTO.getPrice(), result.getPrice())
        );
    }

    @Test
    void updateOffer_ShouldReturnUpdatedOfferDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Offer offer = prepareOffers().getFirst();
        final UpdateOfferRequestDTO updatedOfferDTO = prepareOfferDTOToUpdate();

        // WHEN
        when(offerRepository.findById(existingId)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(OfferMapper.INSTANCE.map(updatedOfferDTO));

        OfferResponseDTO result = offerService.updateOffer(existingId, updatedOfferDTO);

        // THEN
        assertAll("Verify returned updated DTO offer",
                () -> assertEquals(updatedOfferDTO.getMark(), result.getMark()),
                () -> assertEquals(updatedOfferDTO.getProductionYear(), result.getProductionYear()),
                () -> assertEquals(updatedOfferDTO.getMillage(), result.getMillage()),
                () -> assertEquals(updatedOfferDTO.getEngineCapacity(), result.getEngineCapacity()),
                () -> assertEquals(updatedOfferDTO.getDescription(), result.getDescription()),
                () -> assertEquals(updatedOfferDTO.getPrice(), result.getPrice())
        );
    }

    @Test
    void archiveOffer_ShouldReturnArchivedOfferDTO() {
        // GIVEN
        final Long existingId = 1L;
        final Offer offer = prepareOffers().getFirst();

        // WHEN
        when(offerRepository.findById(existingId)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenAnswer(
                invocation -> {
                    offer.archiveOffer();
                    return offer;
                }
        );

        OfferIdResponseDTO result = offerService.archiveOffer(existingId);

        // THEN
        assertAll("Verify returned DTO offer with changed status to ARCHIVE",
                () -> assertEquals(offer.getId(), result.getId())
        );
    }

    private static List<Offer> prepareOffers() {
        return List.of(
                new Offer(1L, "Mark1", 2010L, 100000L, 2.0, "Description1", 10000.0, LocalDateTime.now(), LocalDateTime.now(), OfferStatusDTO.ACTIVE, 1L),
                new Offer(2L, "Mark2", 2011L, 100001L, 2.1, "Description2", 10001.0, LocalDateTime.now(), LocalDateTime.now(), OfferStatusDTO.ACTIVE, 2L),
                new Offer(3L, "Mark3", 2012L, 100002L, 2.2, "Description3", 10002.0, LocalDateTime.now(), LocalDateTime.now(), OfferStatusDTO.ACTIVE, 3L)
        );
    }

    private static CreateOfferRequestDTO prepareOfferDTOToCreate() {
        return CreateOfferRequestDTO.builder()
                .productionYear(2010L)
                .mark("Mark1")
                .millage(100000L)
                .engineCapacity(2.0)
                .description("Description1")
                .price(10000.0)
                .build();
    }

    private static UpdateOfferRequestDTO prepareOfferDTOToUpdate() {
        return UpdateOfferRequestDTO.builder()
                .productionYear(2013L)
                .mark("Mark4")
                .millage(100003L)
                .engineCapacity(2.3)
                .description("Description4")
                .price(10003.0)
                .build();
    }
}