package com.sghss.patterns.strategy;

import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.TelemedicineSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class TelemedicineAppointmentStrategy implements AppointmentStrategy {

    @Override
    public void validateAppointment(Appointment appointment) {
        log.info("Validating telemedicine appointment for protocol {}", appointment.getProtocol());
        if (appointment.getDoctor() == null || !appointment.getDoctor().getActive()) {
            throw new IllegalArgumentException("Doctor unavailable for telemedicine");
        }
    }

    @Override
    public void processAppointment(Appointment appointment) {
        schedule(appointment);
    }

    @Override
    public void schedule(Appointment appointment) {
        log.info("Scheduling telemedicine appointment [Protocol: {}]", appointment.getProtocol());

        TelemedicineSession session = new TelemedicineSession();
        session.setAppointment(appointment);
        session.setSessionLink("https://meet.hospital.com/" + UUID.randomUUID());

        appointment.setTelemedicineSession(session);
        appointment.setModality("Online - Google Meet");
    }
}
