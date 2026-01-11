package com.sghss.domain.repositories;

import com.sghss.domain.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    Optional<Doctor> findByCrm(String crm);

    List<Doctor> findByAcceptsTelemedicine(Boolean acceptsTelemedicine);

    List<Doctor> findBySpecialtyContaining(String specialty);

    Boolean existsByCrm(String crm);
}
