package com.sghss.domain.repositories;

import com.sghss.domain.entities.MedicalRecord;
import com.sghss.domain.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

    Optional<MedicalRecord> findByPatient(Patient patient);
}
