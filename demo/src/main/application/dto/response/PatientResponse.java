package com.sghss.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private UUID id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String susCard;
    private String insurance;
    private String bloodType;
    private String allergies;
    private String emergencyContact;
}
