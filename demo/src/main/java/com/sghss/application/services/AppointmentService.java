package com.sghss.application.services;

import com.sghss.application.dto.request.*;
import com.sghss.application.dto.response.AppointmentResponse;
import com.sghss.domain.entities.*;
import com.sghss.domain.enums.AppointmentStatus;
import com.sghss.domain.enums.ExamStatus;
import com.sghss.domain.enums.AppointmentType;
import com.sghss.domain.exceptions.BusinessException;
import com.sghss.domain.exceptions.ResourceNotFoundException;
import com.sghss.domain.repositories.*;
import com.sghss.patterns.factory.AppointmentFactory;
import com.sghss.patterns.strategy.AppointmentStrategy;
import com.sghss.patterns.strategy.InPersonAppointmentStrategy;
import com.sghss.patterns.strategy.TelemedicineAppointmentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentFactory appointmentFactory;
    private final InPersonAppointmentStrategy inPersonStrategy;
    private final TelemedicineAppointmentStrategy telemedicineStrategy;
    private final PrescriptionRepository prescriptionRepository;
    private final ExamRepository examRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        log.info("Creating appointment for patient ID: {}", request.getPatientId());

        Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
            .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", request.getDoctorId()));

        Appointment appointment = appointmentFactory.createAppointment(request, patient, doctor);

        AppointmentStrategy strategy = getStrategy(appointment.getType());
        strategy.schedule(appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment created: {}", savedAppointment.getProtocol());

        return mapToResponse(savedAppointment);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getById(UUID id) {
        return mapToResponse(appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id)));
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getByProtocol(String protocol) {
        return mapToResponse(appointmentRepository.findByProtocol(protocol)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "protocol", protocol)));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getByPatientId(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId));
        return appointmentRepository.findByPatient(patient).stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getByDoctorId(UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId));
        return appointmentRepository.findByDoctor(doctor).stream().map(this::mapToResponse).toList();
    }

    @Transactional
    public void cancelAppointment(UUID id, String reason) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setObservations("Motivo cancelamento: " + reason);
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void finishAppointment(UUID id, FinishAppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setObservations(request.getDiagnosis() + " - " + request.getNotes());
        appointmentRepository.save(appointment);

        MedicalRecord record = medicalRecordRepository.findByPatient(appointment.getPatient())
                .orElseGet(() -> {
                    MedicalRecord newRecord = new MedicalRecord();
                    newRecord.setPatient(appointment.getPatient());
                    newRecord.setAppointment(appointment);
                    return newRecord;
                });

        medicalRecordRepository.save(record);
    }

// Arquivo: src/main/java/com/sghss/application/services/AppointmentService.java

@Transactional
public void addPrescription(UUID id, PrescriptionRequest request) {
    Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

    Prescription prescription = new Prescription();
    prescription.setAppointment(appointment);
    prescription.setPatient(appointment.getPatient()); // Define o paciente da consulta
    prescription.setDoctor(appointment.getDoctor());   // Define o médico da consulta
    prescription.setNotes(request.getNotes());

    if (request.getMedications() != null) {
        List<Medication> medications = request.getMedications().stream().map(medReq -> {
            Medication medication = new Medication();
            medication.setName(medReq.getName());
            medication.setDosage(medReq.getDosage());
            medication.setObservations(medReq.getInstructions()); // Mapeia instruções
            medication.setPrescription(prescription);
            return medication;
        }).toList();
        prescription.getMedications().addAll(medications);
    }

    prescriptionRepository.save(prescription);
}
    @Transactional
    public void requestExam(UUID id, ExamRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        Exam exam = new Exam();
        exam.setAppointment(appointment);
        exam.setName(request.getName());
        exam.setResult(request.getObservations());
        exam.setStatus(ExamStatus.REQUESTED);
        examRepository.save(exam);
    }

    private AppointmentStrategy getStrategy(AppointmentType type) {
        return type == AppointmentType.TELEMEDICINE ? telemedicineStrategy : inPersonStrategy;
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setProtocol(appointment.getProtocol());
        response.setPatientId(appointment.getPatient().getId());
        response.setPatientName(appointment.getPatient().getName());
        response.setDoctorId(appointment.getDoctor().getId());
        response.setDoctorName(appointment.getDoctor().getName());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setType(appointment.getType());
        response.setStatus(appointment.getStatus());
        response.setChiefComplaint(appointment.getChiefComplaint());
        response.setObservations(appointment.getObservations());

        if (appointment.getTelemedicineSession() != null) {
            response.setSessionLink(appointment.getTelemedicineSession().getSessionLink());
        }
        return response;
    }
}
