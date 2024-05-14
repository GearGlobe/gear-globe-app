package com.gearglobe.app.backend.offer.domain;

import com.gearglobe.dto.OfferStatusDTO;
import com.gearglobe.dto.UpdateOfferRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime createDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private OfferStatusDTO status;

    @NonNull
    @Column(name = "client_id")
    private Long clientId;

    public void updateOffer(UpdateOfferRequestDTO updateOfferRequestDTO) {
        this.mark = updateOfferRequestDTO.getMark();
        this.productionYear= updateOfferRequestDTO.getProductionYear();
        this.millage = updateOfferRequestDTO.getMillage();
        this.engineCapacity = updateOfferRequestDTO.getEngineCapacity();
        this.description = updateOfferRequestDTO.getDescription();
        this.price = updateOfferRequestDTO.getPrice();
    }
}
