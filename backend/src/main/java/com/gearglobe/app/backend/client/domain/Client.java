package com.gearglobe.app.backend.client.domain;


import com.gearglobe.app.backend.configuration.exception.IncorrectClientTypeDataException;
import com.gearglobe.app.backend.configuration.exception.IncorrectPasswordException;
import com.gearglobe.dto.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private ClientTypeDTO clientType;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientRoleDTO role;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientStatusDTO status;

    public static boolean isPersonNotValid(Client client) {
        return client.getClientType() == ClientTypeDTO.PERSON && (Objects.isNull(client.getLastName()) || Objects.isNull(client.getBirthDate()));
    }

    public static boolean isPasswordNotValid(String password) {
        return !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)(?=.*[!@#$%^&*/\\\\()\\-_=+]).{8,}$");
    }

    public static Client createClient(CreateClientRequestDTO createClientRequestDTO, String encodedPassword) {
//        Client client = new Client();
//        client.name = createClientRequestDTO.getName();
//        client.lastName = createClientRequestDTO.getLastName();
//        client.clientType = createClientRequestDTO.getClientType();
//        client.birthDate = createClientRequestDTO.getBirthDate();
//        client.email = createClientRequestDTO.getEmail();
//        client.phoneNumber = createClientRequestDTO.getPhoneNumber();
//        client.password = encodedPassword;
//        client.role = ClientRoleDTO.CLIENT;
//        client.status = ClientStatusDTO.ACTIVE;
//        client.address = Address.createAddress(createClientRequestDTO.getAddress());
//        return client;
//
        return Client.builder()
                .name(createClientRequestDTO.getName())
                .lastName(createClientRequestDTO.getLastName())
                .clientType(createClientRequestDTO.getClientType())
                .birthDate(createClientRequestDTO.getBirthDate())
                .email(createClientRequestDTO.getEmail())
                .phoneNumber(createClientRequestDTO.getPhoneNumber())
                .password(encodedPassword)
                .role(ClientRoleDTO.CLIENT)
                .status(ClientStatusDTO.ACTIVE)
                .address(Address.createAddress(createClientRequestDTO.getAddress()))
                .build();
    }

    public void updateClient(UpdateClientRequestDTO updateClientRequestDTO) {
        this.name = updateClientRequestDTO.getName();
        this.lastName = updateClientRequestDTO.getLastName();
        this.clientType = updateClientRequestDTO.getClientType();
        this.birthDate = updateClientRequestDTO.getBirthDate();
        this.email = updateClientRequestDTO.getEmail();
        this.phoneNumber = updateClientRequestDTO.getPhoneNumber();
    }

    public void deactivateClient() {
        this.status = ClientStatusDTO.INACTIVE;
    }

    public void changePassword(String password) {
        if (isPasswordNotValid(password)){
            throw new IncorrectPasswordException("New password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character!");
        }
        this.password = password;
    }

    public boolean isActive(){
        return this.status == ClientStatusDTO.ACTIVE;
    }

}
