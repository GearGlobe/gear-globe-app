package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OfferMapperTest {

//    @Test
    void testCarOfferToCarOfferResponseDTO() {
        // GIVEN
        Offer offer = prepareOfferWithSimpleData();

        CarOffer carOffer = prepareCarOfferWithSimpleData(offer);

        // WHEN
//        CarOfferResponseDTO carOfferResponseDTO = OfferMapper.INSTANCE.map(carOffer);

        CarOfferResponseDTO carOfferResponseDTO = null;
        // THEN
        assertAll("Verify mapping properties to OfferDTO",
                () -> assertEquals(offer. getTitle(), carOfferResponseDTO.getTitle()),
                () -> assertEquals(offer.getPrice(), carOfferResponseDTO.getPrice()),
                () -> assertEquals(offer.getDescription(), carOfferResponseDTO.getDescription()),
                () -> assertEquals(offer.getCreateDate(), carOfferResponseDTO.getCreateDate()),
                () -> assertEquals(offer.getStatus(), carOfferResponseDTO.getStatus()),
                () -> assertEquals(carOffer.getMake(), carOfferResponseDTO.getMake()),
                () -> assertEquals(carOffer.getModel(), carOfferResponseDTO.getModel()),
                () -> assertEquals(carOffer.getBody(), carOfferResponseDTO.getBody()),
                () -> assertEquals(carOffer.getVersion(), carOfferResponseDTO.getVersion()),
                () -> assertEquals(carOffer.getGeneration(), carOfferResponseDTO.getGeneration()),
                () -> assertEquals(carOffer.getVin(), carOfferResponseDTO.getVin()),
                () -> assertEquals(carOffer.getFuel(), carOfferResponseDTO.getFuel()),
                () -> assertEquals(carOffer.getPower(), carOfferResponseDTO.getPower()),
                () -> assertEquals(carOffer.getGearbox(), carOfferResponseDTO.getGearbox()),
                () -> assertEquals(carOffer.getInsideCityFuelConsumption(), carOfferResponseDTO.getInsideCityFuelConsumption()),
                () -> assertEquals(carOffer.getOutsideCityFuelConsumption(), carOfferResponseDTO.getOutsideCityFuelConsumption()),
                () -> assertEquals(carOffer.getDoorCount(), carOfferResponseDTO.getDoorCount()),
                () -> assertEquals(carOffer.getColor(), carOfferResponseDTO.getColor()),
                () -> assertEquals(carOffer.getPaintFinish(), carOfferResponseDTO.getPaintFinish()),
                () -> assertEquals(carOffer.getProductionCountry(), carOfferResponseDTO.getProductionCountry()),
                () -> assertEquals(carOffer.getIsRegisteredInPoland(), carOfferResponseDTO.getIsRegisteredInPoland()),
                () -> assertEquals(carOffer.getIsAfterIncident(), carOfferResponseDTO.getIsAfterIncident()),
                () -> assertEquals(carOffer.getWearStage(), carOfferResponseDTO.getWearStage()),
                () -> assertEquals(carOffer.getProductionYear(), carOfferResponseDTO.getProductionYear()),
                () -> assertEquals(carOffer.getMillage(), carOfferResponseDTO.getMillage()),
                () -> assertEquals(carOffer.getEngineCapacity(), carOfferResponseDTO.getEngineCapacity())
        );
    }

//    @Test
//    void testOfferDTOToOffer() {
//        // GIVEN
//        OfferResponseDTO offerDTO = OfferResponseDTO.builder()
//                .description("Sample Description DTO")
//                .price(88.88)
//                .mark("Sample Mark DTO")
//                .productionYear(2021L)
//                .millage(2000L)
//                .engineCapacity(2.0)
//                .build();
//
//        // WHEN
//        Offer offer = OfferMapper.INSTANCE.map(offerDTO);
//
//        // THEN
//        assertAll("Verify mapping properties to Offer",
//                () -> assertEquals(offerDTO.getDescription(), offer.getDescription()),
//                () -> assertEquals(offerDTO.getPrice(), offer.getPrice()),
//                () -> assertEquals(offerDTO.getMark(), offer.getMark()),
//                () -> assertEquals(offerDTO.getProductionYear(), offer.getProductionYear()),
//                () -> assertEquals(offerDTO.getMillage(), offer.getMillage()),
//                () -> assertEquals(offerDTO.getEngineCapacity(), offer.getEngineCapacity()));
//    }

    private Offer prepareOfferWithSimpleData() {
//        return Offer.builder()
//                .id(1L)
//                .offerTypeDTO(OfferTypeDTO.CAR)
//                .description("Sample Description")
//                .price(99.99)
//                .title("Sample Title")
//                .negotiable(true)
//                .createDate(LocalDateTime.now())
//                .modifiedDate(LocalDateTime.now())
//                .status(OfferStatusDTO.ARCHIVE)
//                .build();
        return null;
    }

    private CarOffer prepareCarOfferWithSimpleData(Offer offer) {
        return CarOffer.builder()
//                .id(1L)
                .make("Sample Mark")
                .model("Sample Model")
                .body(CarBodyTypeDTO.COUPE)
                .version("Sample Version")
                .generation("Sample Generation")
                .vin("Sample VIN")
                .fuel(FuelTypeDTO.DIESEL)
                .power(100)
                .gearbox(GearBoxDTO.AUTOMATIC)
                .insideCityFuelConsumption(5.0)
                .outsideCityFuelConsumption(4.0)
                .doorCount(3)
                .color("Sample Color")
                .paintFinish("Sample Paint Finish")
                .productionCountry("Sample Production Country")
                .isRegisteredInPoland(true)
                .isAfterIncident(false)
                .wearStage(WearStageDTO.NEW)
                .productionYear(2021L)
                .millage(2000L)
                .engineCapacity(2.0)
//                .offer(offer)
                .build();
    }
}