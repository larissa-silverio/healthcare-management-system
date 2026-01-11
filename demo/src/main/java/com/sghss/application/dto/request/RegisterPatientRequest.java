package com.sghss.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPatientRequest {

    @NotBlank(message = "CPF is required")
    @Size(min = 11, max = 11, message = "CPF must have 11 digits")
    private String cpf;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private String susCard;

    private String insurance;

    private String bloodType;

    private String allergies;

    private String familyHistory;

    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;
}
