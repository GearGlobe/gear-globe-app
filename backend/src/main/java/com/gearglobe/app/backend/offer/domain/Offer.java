package com.gearglobe.app.backend.offer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "offer")
class Offer {
    @Id
    Long id;

    String mark;

    Long year;

    Long millage;

    Double engineCapacity;

    String description;

    Double price;

    LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    OfferStatus status;
}
