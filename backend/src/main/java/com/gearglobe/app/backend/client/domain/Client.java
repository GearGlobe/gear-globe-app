package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.ClientStatus;
import com.gearglobe.app.backend.client.api.dtos.ClientType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "client")
class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String lastName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    private LocalDate birthDate;

    @NonNull
    private String email;

    @NonNull
    private String phoneNumber;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private Address address;

    @NonNull
    private String password;

    @NonNull
    private String role;

    @NonNull
    private LocalDate createDate;

    private LocalDate modifyDate;

    @NonNull
    private ClientStatus status;

}
