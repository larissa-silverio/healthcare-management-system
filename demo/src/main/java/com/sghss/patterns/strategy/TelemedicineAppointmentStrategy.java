package com.sghss.patterns.strategy;

import com.sghss.domain.entities.Appointment;
import com.sghss.domain.entities.TelemedicineSession;
import com.sghss.domain.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Estratégia para teleconsultas
 */
@Slf4j
@Component
public class TelemedicineAppointmentStrategy implements AppointmentStrategy {

    @Override
    public void processAppointment(Appointment appointment) {
        log.info("Processing telemedicine appointment: {}", appointment.getProtocol());

        // Lógica específica para teleconsultas
        if (appointment.getTelemedicineSession() == null) {
            // Criar sessão de telemedicina
            TelemedicineSession session = new TelemedicineSession();
            session.setAppointment(appointment);
            session.setSessionLink(generateSessionLink());
            appointment.setTelemedicineSession(session);
        }

        log.info("Telemedicine appointment processed successfully");
    }

    @Override
    public void validateAppointment(Appointment appointment) {
        log.info("Validating telemedicine appointment: {}", appointment.getProtocol());

        if (!appointment.getDoctor().getAcceptsTelemedicine()) {
            throw new BusinessException("Doctor does not accept telemedicine appointments");
        }

        // Outras validações específicas
        log.info("Telemedicine appointment validated successfully");
    }

    private String generateSessionLink() {
        // Aqui seria integração com plataforma externa (Adapter)
        return "https://meet.hospital.com/session-" + System.currentTimeMillis();
    }
}
