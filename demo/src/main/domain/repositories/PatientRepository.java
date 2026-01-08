package com.sass.domain.repositories;

import com.sass.domain.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByCpf(String cpf);

    Optional<Patient> findBySusCard(String susCard);

    Boolean existsBySusCard(String susCard);
}
