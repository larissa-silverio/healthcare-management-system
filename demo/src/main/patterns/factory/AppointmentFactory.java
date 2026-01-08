package com.sghss.patterns.factory;

import com.sghss.application.dto.request.CreateAppointmentRequest;
import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.Doctor;
import com.sghss.domain.entities.Patient;
import com.sghss.domain.enums.AppointmentStatus;
import com.sghss.domain.enums.AppointmentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Padrão Factory para criar diferentes tipos de consulta
 */
@Slf4j
@Component
public class AppointmentFactory {

    public Appointment createAppointment(
        CreateAppointmentRequest request,
        Patient patient,
        Doctor doctor
    ) {
        log.info("Creating appointment of type: {}", request.getType());

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setType(request.getType());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setChiefComplaint(request.getChiefComplaint());
        appointment.setObservations(request.getObservations());

        // Configurações específicas por tipo
        if (request.getType() == AppointmentType.IN_PERSON) {
            appointment.setModality(request.getModality());
        }

        log.info("Appointment created: {}", appointment.getProtocol());
        return appointment;
    }
}
