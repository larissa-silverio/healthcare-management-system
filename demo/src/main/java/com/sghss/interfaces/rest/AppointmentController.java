package com.sghss.interfaces.rest;

import com.sghss.application.dto.request.CreateAppointmentRequest;
import com.sghss.application.dto.response.AppointmentResponse;
import com.sghss.application.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN', 'PATIENT')")
    public ResponseEntity<AppointmentResponse> createAppointment(
        @Valid @RequestBody CreateAppointmentRequest request
    ) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN', 'PATIENT')")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable UUID id) {
        AppointmentResponse response = appointmentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/protocol/{protocol}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN', 'PATIENT')")
    public ResponseEntity<AppointmentResponse> getAppointmentByProtocol(@PathVariable String protocol) {
        AppointmentResponse response = appointmentService.getByProtocol(protocol);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN', 'PATIENT')")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByPatient(@PathVariable UUID patientId) {
        List<AppointmentResponse> responses = appointmentService.getByPatientId(patientId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByDoctor(@PathVariable UUID doctorId) {
        List<AppointmentResponse> responses = appointmentService.getByDoctorId(doctorId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    public ResponseEntity<Void> cancelAppointment(
        @PathVariable UUID id,
        @RequestParam String reason
    ) {
        appointmentService.cancelAppointment(id, reason);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para Finalizar Consulta
    @PutMapping("/{id}/finish")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Void> finishAppointment(
        @PathVariable UUID id,
        @Valid @RequestBody FinishAppointmentRequest request
    ) {
        appointmentService.finishAppointment(id, request);
        return ResponseEntity.ok().build();
    }

    // Endpoint para Adicionar Prescrição
    @PostMapping("/{id}/prescriptions")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Void> addPrescription(
        @PathVariable UUID id,
        @Valid @RequestBody PrescriptionRequest request
    ) {
        appointmentService.addPrescription(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Endpoint para Solicitar Exame
    @PostMapping("/{id}/exams")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Void> requestExam(
        @PathVariable UUID id,
        @Valid @RequestBody ExamRequest request
    ) {
        appointmentService.requestExam(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
