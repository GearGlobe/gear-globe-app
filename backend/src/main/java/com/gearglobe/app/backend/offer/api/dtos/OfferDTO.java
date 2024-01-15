package com.gearglobe.app.backend.offer.api.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OfferDTO {
    private Long id;

    private String mark;

    private Long year;

    private Long millage;

    private Double engineCapacity;

    private String description;

    private Double price;

    private LocalDateTime createDate;

    private OfferStatus status;
}