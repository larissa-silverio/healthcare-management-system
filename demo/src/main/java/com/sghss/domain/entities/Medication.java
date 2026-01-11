package com.sghss.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 50)
    private String dosage;

    @Column(length = 100)
    private String frequency;

    @Column(length = 50)
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String observations;
}
