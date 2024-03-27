package com.gearglobe.app.backend.client.domain;

import com.gearglobe.app.backend.client.api.dtos.enums.ClientRole;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientStatus;
import com.gearglobe.app.backend.client.api.dtos.enums.ClientType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
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

    @Column(nullable = false)
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "client")
    private Address address;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientRole role;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientStatus status;

}
