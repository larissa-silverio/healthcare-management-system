package com.sghss.domain.repositories;

import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.TelemedicineSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TelemedicineSessionRepository extends JpaRepository<TelemedicineSession, UUID> {

    Optional<TelemedicineSession> findByAppointment(Appointment appointment);
}
