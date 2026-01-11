package com.sghss.application.services;

import com.sghss.application.dto.response.PatientResponse;
import com.sghss.domain.entities.Patient;
import com.sghss.domain.exceptions.ResourceNotFoundException;
import com.sghss.domain.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public PatientResponse getById(UUID id) {
        log.info("Getting patient by ID: {}", id);

        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));

        return mapToResponse(patient);
    }

    @Transactional(readOnly = true)
    public PatientResponse getByCpf(String cpf) {
        log.info("Getting patient by CPF: {}", cpf);

        Patient patient = patientRepository.findByCpf(cpf)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "cpf", cpf));

        return mapToResponse(patient);
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        log.info("Getting all patients");

        return patientRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private PatientResponse mapToResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setCpf(patient.getCpf());
        response.setName(patient.getName());
        response.setEmail(patient.getEmail());
        response.setPhone(patient.getPhone());
        response.setBirthDate(patient.getBirthDate());
        response.setSusCard(patient.getSusCard());
        response.setInsurance(patient.getInsurance());
        response.setBloodType(patient.getBloodType());
        response.setAllergies(patient.getAllergies());
        response.setEmergencyContact(patient.getEmergencyContact());
        return response;
    }
}
