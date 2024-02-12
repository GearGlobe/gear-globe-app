package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.AddressStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "address")
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    private String houseNumber;

    private String apartmentNumber;

    @NonNull
    private String country;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AddressStatus status;
}
