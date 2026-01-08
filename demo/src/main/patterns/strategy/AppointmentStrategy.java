package com.sghss.patterns.strategy;

import com.sghss.domain.entities.Appointment;

/**
 * Padrão Strategy para processar diferentes tipos de consulta
 */
public interface AppointmentStrategy {

    void processAppointment(Appointment appointment);

    void validateAppointment(Appointment appointment);
}
