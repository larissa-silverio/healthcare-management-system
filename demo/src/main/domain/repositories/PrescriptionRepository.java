package com.sass.domain.repositories;

import com.sass.domain.entities.Appointment;
import com.sass.domain.entities.Patient;
import com.sass.domain.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    List<Prescription> findByPatient(Patient patient);

    List<Prescription> findByAppointment(Appointment appointment);
}
