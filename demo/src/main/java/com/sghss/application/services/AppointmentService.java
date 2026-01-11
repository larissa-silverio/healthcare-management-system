package com.sghss.application.services;

import com.sghss.application.dto.request.CreateAppointmentRequest;
import com.sghss.application.dto.response.AppointmentResponse;
import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.Doctor;
import com.sghss.domain.entities.Patient;
import com.sghss.domain.enums.AppointmentStatus;
import com.sghss.domain.enums.AppointmentType;
import com.sghss.domain.exceptions.BusinessException;
import com.sghss.domain.exceptions.ResourceNotFoundException;
import com.sghss.domain.repositories.AppointmentRepository;
import com.sghss.domain.repositories.DoctorRepository;
import com.sghss.domain.repositories.PatientRepository;
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
import java.util.stream.Collectors;

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

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        log.info("Creating appointment for patient ID: {}", request.getPatientId());

        // Buscar paciente e médico
        Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
            .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", request.getDoctorId()));

        // Usar Factory para criar consulta
        Appointment appointment = appointmentFactory.createAppointment(request, patient, doctor);

        // Usar Strategy para processar e validar
        AppointmentStrategy strategy = getStrategy(appointment.getType());
        strategy.validateAppointment(appointment);
        strategy.processAppointment(appointment);

        // Salvar
        Appointment savedAppointment = appointmentRepository.save(appointment);

        log.info("Appointment created successfully: {}", savedAppointment.getProtocol());

        return mapToResponse(savedAppointment);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getById(UUID id) {
        log.info("Getting appointment by ID: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        return mapToResponse(appointment);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getByProtocol(String protocol) {
        log.info("Getting appointment by protocol: {}", protocol);

        Appointment appointment = appointmentRepository.findByProtocol(protocol)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "protocol", protocol));

        return mapToResponse(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getByPatientId(UUID patientId) {
        log.info("Getting appointments for patient ID: {}", patientId);

        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId));

        return appointmentRepository.findByPatient(patient)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getByDoctorId(UUID doctorId) {
        log.info("Getting appointments for doctor ID: {}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId));

        return appointmentRepository.findByDoctor(doctor)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void cancelAppointment(UUID id, String reason) {
        log.info("Cancelling appointment ID: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BusinessException("Cannot cancel a completed appointment");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BusinessException("Appointment is already cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setObservations(
            (appointment.getObservations() != null ? appointment.getObservations() + "\n" : "") +
            "Cancelled: " + reason
        );

        appointmentRepository.save(appointment);

        log.info("Appointment cancelled successfully: {}", appointment.getProtocol());
    }

    private AppointmentStrategy getStrategy(AppointmentType type) {
        return switch (type) {
            case IN_PERSON -> inPersonStrategy;
            case TELEMEDICINE -> telemedicineStrategy;
        };
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
