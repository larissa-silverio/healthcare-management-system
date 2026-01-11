package com.sghss.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends HealthProfessional {

    @Column(nullable = false, unique = true, length = 20)
    private String crm;

    @Column(name = "specialties", columnDefinition = "TEXT")
    private String specialties; // Pode ser JSON array

    @Column(name = "accepts_telemedicine")
    private Boolean acceptsTelemedicine = false;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions = new ArrayList<>();
}
