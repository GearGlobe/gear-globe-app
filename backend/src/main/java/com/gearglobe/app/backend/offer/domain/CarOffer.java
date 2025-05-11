package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "car_offer")
class CarOffer extends Offer {

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarBodyTypeDTO body;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String generation;

    @Column(nullable = false)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelTypeDTO fuel;

    @Column(nullable = false)
    private Integer power;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GearBoxDTO gearbox;

    @Column(nullable = false, name = "inside_city_fuel_consumption")
    private Double insideCityFuelConsumption;

    @Column(nullable = false, name = "outside_city_fuel_consumption")
    private Double outsideCityFuelConsumption;

    @Column(nullable = false, name = "door_count")
    private Integer doorCount;

    @Column(nullable = false)
    private String color;

    @Column(name = "paint_finish")
    private String paintFinish;

    @Column(name = "production_country")
    private String productionCountry;

    @Column(nullable = false, name = "production_year")
    private Long productionYear;

    @Column(nullable = false, name = "registered_in_poland")
    private Boolean isRegisteredInPoland;

    @Column(nullable = false, name = "after_incident")
    private Boolean isAfterIncident;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "wear_stage")
    private WearStageDTO wearStage;

    @Column(nullable = false)
    private Long millage;

    @Column(nullable = false, name = "engine_capacity")
    private Double engineCapacity;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public static CarOffer createOffer(CreateCarOfferRequestDTO createCarOfferRequestDTO, Long clientId) {
        return CarOffer.builder()
                .offerTypeDTO(createCarOfferRequestDTO.getOfferType())
                .title(createCarOfferRequestDTO.getTitle())
                .description(createCarOfferRequestDTO.getDescription())
                .price(createCarOfferRequestDTO.getPrice())
                .negotiable(createCarOfferRequestDTO.getNegotiable())
                .status(OfferStatusDTO.ACTIVE)
                .clientId(clientId)
                .make(createCarOfferRequestDTO.getMake())
                .model(createCarOfferRequestDTO.getModel())
                .body(createCarOfferRequestDTO.getBody())
                .version(createCarOfferRequestDTO.getVersion())
                .generation(createCarOfferRequestDTO.getGeneration())
                .vin(createCarOfferRequestDTO.getVin())
                .fuel(createCarOfferRequestDTO.getFuel())
                .power(createCarOfferRequestDTO.getPower())
                .gearbox(createCarOfferRequestDTO.getGearbox())
                .insideCityFuelConsumption(createCarOfferRequestDTO.getInsideCityFuelConsumption())
                .outsideCityFuelConsumption(createCarOfferRequestDTO.getOutsideCityFuelConsumption())
                .doorCount(createCarOfferRequestDTO.getDoorCount())
                .color(createCarOfferRequestDTO.getColor())
                .paintFinish(createCarOfferRequestDTO.getPaintFinish())
                .productionCountry(createCarOfferRequestDTO.getProductionCountry())
                .productionYear(createCarOfferRequestDTO.getProductionYear())
                .isRegisteredInPoland(createCarOfferRequestDTO.getIsRegisteredInPoland())
                .isAfterIncident(createCarOfferRequestDTO.getIsAfterIncident())
                .wearStage(createCarOfferRequestDTO.getWearStage())
                .millage(createCarOfferRequestDTO.getMillage())
                .engineCapacity(createCarOfferRequestDTO.getEngineCapacity())
                .build();
    }

    public void updateOffer(UpdateOfferRequestDTO updateOfferRequestDTO) {

    }
}
