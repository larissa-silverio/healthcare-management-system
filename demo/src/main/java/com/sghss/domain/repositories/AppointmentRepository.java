package com.sghss.domain.repositories;

import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.Doctor;
import com.sghss.domain.entities.Patient;
import com.sghss.domain.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    Optional<Appointment> findByProtocol(String protocol);

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByPatientAndStatus(Patient patient, AppointmentStatus status);

    List<Appointment> findByDoctorAndStatus(Doctor doctor, AppointmentStatus status);

    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);

    List<Appointment> findByDoctorAndAppointmentDateBetween(
        Doctor doctor,
        LocalDateTime start,
        LocalDateTime end
    );
}
