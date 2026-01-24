package com.sghss.application.dto.request;

import com.sghss.domain.enums.AppointmentType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateAppointmentRequest {
    @NotNull
    private UUID patientId;

    @NotNull
    private UUID doctorId;

    @NotNull
    private LocalDateTime appointmentDate;

    @NotNull
    private AppointmentType type;

    private String chiefComplaint;
    private String observations;

    // Campo adicionado para corrigir erro na Factory
    private String modality;
}
