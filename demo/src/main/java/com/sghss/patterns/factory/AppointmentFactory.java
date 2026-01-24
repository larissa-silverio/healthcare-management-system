package com.sghss.patterns.factory;

import com.sghss.application.dto.request.CreateAppointmentRequest;
import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.Doctor;
import com.sghss.domain.entities.Patient;
import com.sghss.domain.enums.AppointmentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j // Adicionado
public class AppointmentFactory {

    public Appointment createAppointment(CreateAppointmentRequest request, Patient patient, Doctor doctor) {
        log.info("Creating appointment via Factory for type: {}", request.getType());

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setType(request.getType());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setChiefComplaint(request.getChiefComplaint());
        appointment.setObservations(request.getObservations());

        // Se modality for usado para algo específico, configure aqui
        // appointment.setModality(request.getModality());

        return appointment;
    }
}
