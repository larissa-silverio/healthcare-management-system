package com.sghss.domain.repositories;

import com.sghss.domain.entities.Exam;
import com.sghss.domain.entities.Patient;
import com.sghss.domain.enums.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {

    List<Exam> findByAppointmentPatient(Patient patient);

    List<Exam> findByStatus(ExamStatus status);

    List<Exam> findByAppointmentPatientAndStatus(Patient patient, ExamStatus status);
}
