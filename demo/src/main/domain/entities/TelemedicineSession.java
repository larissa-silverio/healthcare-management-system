package com.sass.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "telemedicine_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemedicineSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @Column(name = "session_link", length = 500)
    private String sessionLink;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "duration") // Em minutos
    private Integer duration;

    @Column(name = "recording_url", length = 500)
    private String recordingUrl;
}
