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
    @Column(name = "production_year")
    private Long productionYear;

    @NonNull
    private Long millage;

    @NonNull
    @Column(name = "engine_capacity")
    private Double engineCapacity;

    private String description;

    @NonNull
    private Double price;

    @NonNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @NonNull
    @Column(name = "client_id")
    private Long clientId;
}
