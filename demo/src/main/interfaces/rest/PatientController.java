package com.sghss.interfaces.rest;

import com.sghss.application.dto.response.PatientResponse;
import com.sghss.application.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN', 'PATIENT')")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable UUID id) {
        PatientResponse response = patientService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')")
    public ResponseEntity<PatientResponse> getPatientByCpf(@PathVariable String cpf) {
        PatientResponse response = patientService.getByCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        List<PatientResponse> responses = patientService.getAllPatients();
        return ResponseEntity.ok(responses);
    }
}
