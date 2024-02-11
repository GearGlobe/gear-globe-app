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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String mark;

    @NonNull
    private Long productionYear;

    @NonNull
    private Long millage;

    @NonNull
    private Double engineCapacity;

    private String description;

    @NonNull
    private Double price;

    @NonNull
    private LocalDateTime createDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @NonNull
    private Long clientId;
}
