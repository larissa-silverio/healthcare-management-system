package com.sghss.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "health_professionals")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class HealthProfessional extends User {

    @Column(nullable = false, unique = true, length = 20)
    private String registrationNumber;

    @Column(length = 100)
    private String specialty;

    @Column(name = "work_unit", length = 100)
    private String workUnit;
}
