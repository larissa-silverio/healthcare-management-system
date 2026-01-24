package com.sghss.domain.entities;

import com.sghss.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data // Gera Getters, Setters, ToString, etc.
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String cpf;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    private String phone;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean active = true; // Garante que getActive() funcione

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
