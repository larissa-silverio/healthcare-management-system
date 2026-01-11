package com.sghss.application.dto.request;

import com.sghss.domain.enums.AppointmentType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {

    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotNull(message = "Doctor ID is required")
    private UUID doctorId;

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;

    @NotNull(message = "Appointment type is required")
    private AppointmentType type;

    private String modality;

    @NotBlank(message = "Chief complaint is required")
    private String chiefComplaint;

    private String observations;
}
