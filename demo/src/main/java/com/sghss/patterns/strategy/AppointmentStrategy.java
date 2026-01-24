package com.sghss.patterns.strategy;

import com.sghss.domain.entities.Appointment;

public interface AppointmentStrategy {
    void validateAppointment(Appointment appointment);
    void processAppointment(Appointment appointment);
    void schedule(Appointment appointment);
}