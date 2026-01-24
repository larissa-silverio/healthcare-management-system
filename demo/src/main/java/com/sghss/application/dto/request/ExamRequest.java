package com.sghss.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExamRequest {
    @NotBlank(message = "The name of the exam is mandatory")
    private String name;
    private String observations;
}
