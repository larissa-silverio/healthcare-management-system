package com.sass.domain.repositories;

import com.sass.domain.entities.Appointment;
import com.sass.domain.entities.TelemedicineSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TelemedicineSessionRepository extends JpaRepository<TelemedicineSession, UUID> {

    Optional<TelemedicineSession> findByAppointment(Appointment appointment);
}
