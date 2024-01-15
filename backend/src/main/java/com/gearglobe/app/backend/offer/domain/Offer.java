package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.app.backend.offer.api.dtos.OfferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
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

    @NonNull
    String mark;

    @NonNull
    Long year;

    @NonNull
    Long millage;

    @NonNull
    Double engineCapacity;

    String description;

    @NonNull
    Double price;

    @NonNull
    LocalDateTime createDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    OfferStatus status;
}
