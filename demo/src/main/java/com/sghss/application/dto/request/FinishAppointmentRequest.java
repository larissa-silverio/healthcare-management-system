package com.sghss.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FinishAppointmentRequest {
    @NotBlank(message = "Diagnosis is mandatory")
    private String diagnosis;
    private String notes;
}
