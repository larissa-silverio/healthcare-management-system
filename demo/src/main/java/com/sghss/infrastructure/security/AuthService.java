package com.sghss.application.services;

import com.sghss.application.dto.request.LoginRequest;
import com.sghss.application.dto.request.RegisterPatientRequest;
import com.sghss.application.dto.response.AuthResponse;
import com.sghss.domain.entities.MedicalRecord;
import com.sghss.domain.entities.Patient;
import com.sghss.domain.enums.UserRole;
import com.sghss.domain.exceptions.BusinessException;
import com.sghss.domain.repositories.MedicalRecordRepository;
import com.sghss.domain.repositories.PatientRepository;
import com.sghss.domain.repositories.UserRepository;
import com.sghss.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Transactional
    public AuthResponse registerPatient(RegisterPatientRequest request) {
        log.info("Registering new patient: {}", request.getEmail());

        // Validações
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        if (userRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("CPF already exists");
        }

        if (request.getSusCard() != null && patientRepository.existsBySusCard(request.getSusCard())) {
            throw new BusinessException("SUS card already exists");
        }

        // Criar paciente
        Patient patient = new Patient();
        patient.setCpf(request.getCpf());
        patient.setName(request.getName());
        patient.setEmail(request.getEmail());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setPhone(request.getPhone());
        patient.setBirthDate(request.getBirthDate());
        patient.setRole(UserRole.PATIENT);
        patient.setSusCard(request.getSusCard());
        patient.setInsurance(request.getInsurance());
        patient.setBloodType(request.getBloodType());
        patient.setAllergies(request.getAllergies());
        patient.setFamilyHistory(request.getFamilyHistory());
        patient.setEmergencyContact(request.getEmergencyContact());

        Patient savedPatient = patientRepository.save(patient);

        // Criar prontuário
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatient(savedPatient);
        medicalRecordRepository.save(medicalRecord);

        // Gerar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(patient.getEmail());
        String token = jwtService.generateToken(userDetails);

        log.info("Patient registered successfully: {}", patient.getId());

        return new AuthResponse(
            token,
            patient.getEmail(),
            patient.getName(),
            patient.getRole().name()
        );
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Authenticating user: {}", request.getEmail());

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessException("Invalid credentials"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        log.info("User authenticated successfully: {}", user.getId());

        return new AuthResponse(
            token,
            user.getEmail(),
            user.getName(),
            user.getRole().name()
        );
    }
}
