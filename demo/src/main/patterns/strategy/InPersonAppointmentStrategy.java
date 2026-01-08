package com.sghss.patterns.strategy;

import com.sghss.domain.entities.Appointment;
import com.sghss.domain.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Estratégia para consultas presenciais
 */
@Slf4j
@Component
public class InPersonAppointmentStrategy implements AppointmentStrategy {

    @Override
    public void processAppointment(Appointment appointment) {
        log.info("Processing in-person appointment: {}", appointment.getProtocol());

        // Lógica específica para consultas presenciais
        // Ex: verificar disponibilidade de sala, confirmar presença, etc.

        log.info("In-person appointment processed successfully");
    }

    @Override
    public void validateAppointment(Appointment appointment) {
        log.info("Validating in-person appointment: {}", appointment.getProtocol());

        if (appointment.getModality() == null || appointment.getModality().isBlank()) {
            throw new BusinessException("Modality is required for in-person appointments");
        }

        // Outras validações específicas
        log.info("In-person appointment validated successfully");
    }
}
