package com.sghss.patterns.strategy;

import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.Doctor;
import com.sghss.domain.entities.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InPersonAppointmentStrategy implements AppointmentStrategy {

    @Override
    public void validateAppointment(Appointment appointment) {
        log.info("Validating in-person appointment for patient {}", appointment.getPatient().getName());

    }

    @Override
    public void processAppointment(Appointment appointment) {
        schedule(appointment);
    }

    @Override
    public void schedule(Appointment appointment) {
        log.info("Scheduling in-person appointment [Protocol: {}]", appointment.getProtocol());
        appointment.setModality("Presencial - Consultório 3");

    }
}
