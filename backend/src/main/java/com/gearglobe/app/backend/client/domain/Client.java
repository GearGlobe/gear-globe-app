package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.ClientStatus;
import com.gearglobe.app.backend.client.api.dtos.ClientType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
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

    @OneToOne(mappedBy = "client")
    private Address address;

    @NonNull
    private String password;

    @NonNull
    private String role;

    @CreatedDate()
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ClientStatus status;

}
