package com.sass.domain.repositories;

import com.sass.domain.entities.Exam;
import com.sass.domain.entities.Patient;
import com.sass.domain.enums.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {

    List<Exam> findByPatient(Patient patient);

    List<Exam> findByStatus(ExamStatus status);

    List<Exam> findByPatientAndStatus(Patient patient, ExamStatus status);
}
