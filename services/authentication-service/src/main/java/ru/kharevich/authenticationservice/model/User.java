package ru.kharevich.authenticationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "user_external_person_merge")
public class User {

    @Id
    @Column(name = "keycloak_user_id")
    private UUID keycloakId;

    @Column(name = "external_id")
    private UUID externalId;

    String firstname;

    String lastname;

    @Email
    String email;

}
