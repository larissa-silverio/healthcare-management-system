-- V1__create_tables.sql

-- Tabela de usuários (base para herança)
CREATE TABLE users (
                       id BINARY(16) PRIMARY KEY,
                       cpf VARCHAR(11) UNIQUE NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(15),
                       birth_date DATE,
                       role VARCHAR(20) NOT NULL,
                       active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT chk_role CHECK (role IN ('PATIENT', 'DOCTOR', 'NURSE', 'ADMIN'))
);

-- Índices para users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_cpf ON users(cpf);

-- Tabela de pacientes
CREATE TABLE patients (
                          id BINARY(16) PRIMARY KEY,
                          sus_card VARCHAR(20) UNIQUE,
                          insurance VARCHAR(100),
                          blood_type VARCHAR(3),
                          allergies TEXT,
                          family_history TEXT,
                          emergency_contact VARCHAR(100),
                          FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela de profissionais de saúde
CREATE TABLE health_professionals (
                                      id BINARY(16) PRIMARY KEY,
                                      registration_number VARCHAR(20) UNIQUE NOT NULL,
                                      specialty VARCHAR(100),
                                      work_unit VARCHAR(100),
                                      FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela de médicos
CREATE TABLE doctors (
                         id BINARY(16) PRIMARY KEY,
                         crm VARCHAR(20) UNIQUE NOT NULL,
                         specialties TEXT,
                         accepts_telemedicine BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (id) REFERENCES health_professionals(id) ON DELETE CASCADE
);

-- Tabela de prontuários médicos
CREATE TABLE medical_records (
                                 id BINARY(16) PRIMARY KEY,
                                 patient_id BINARY(16) UNIQUE NOT NULL,
                                 last_updated TIMESTAMP,
                                 FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

-- Tabela de consultas
CREATE TABLE appointments (
                              id BINARY(16) PRIMARY KEY,
                              protocol VARCHAR(20) UNIQUE NOT NULL,
                              patient_id BINARY(16) NOT NULL,
                              doctor_id BINARY(16) NOT NULL,
                              appointment_date TIMESTAMP NOT NULL,
                              type ENUM('IN_PERSON','TELEMEDICINE') NOT NULL,
                              status ENUM('SCHEDULED','IN_PROGRESS','COMPLETED','CANCELLED') NOT NULL,
                              modality VARCHAR(50),
                              chief_complaint TEXT,
                              observations TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP,
                              FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE RESTRICT,
                              FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT
);

-- Índices para appointments
CREATE INDEX idx_appointments_patient ON appointments(patient_id);
CREATE INDEX idx_appointments_doctor ON appointments(doctor_id);
CREATE INDEX idx_appointments_date ON appointments(appointment_date);
CREATE INDEX idx_appointments_status ON appointments(status);
CREATE INDEX idx_appointments_protocol ON appointments(protocol);

-- Tabela de sessões de telemedicina
CREATE TABLE telemedicine_sessions (
                                       id BINARY(16) PRIMARY KEY,
                                       appointment_id BINARY(16) UNIQUE NOT NULL,
                                       session_link VARCHAR(500),
                                       start_time TIMESTAMP,
                                       end_time TIMESTAMP,
                                       duration INT,
                                       recording_url VARCHAR(500),
                                       FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);

-- Tabela de prescrições
CREATE TABLE prescriptions (
                               id BINARY(16) PRIMARY KEY,
                               appointment_id BINARY(16) NOT NULL,
                               doctor_id BINARY(16) NOT NULL,
                               patient_id BINARY(16) NOT NULL,
                               medical_record_id BINARY(16),
                               issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               validity DATE,
                               digital_signature TEXT,
                               FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
                               FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT,
                               FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE RESTRICT,
                               FOREIGN KEY (medical_record_id) REFERENCES medical_records(id) ON DELETE SET NULL
);

-- Índices para prescriptions
CREATE INDEX idx_prescriptions_patient ON prescriptions(patient_id);
CREATE INDEX idx_prescriptions_appointment ON prescriptions(appointment_id);

-- Tabela de medicamentos
CREATE TABLE medications (
                             id BINARY(16) PRIMARY KEY,
                             prescription_id BINARY(16) NOT NULL,
                             name VARCHAR(200) NOT NULL,
                             dosage VARCHAR(50),
                             frequency VARCHAR(100),
                             duration VARCHAR(50),
                             observations TEXT,
                             FOREIGN KEY (prescription_id) REFERENCES prescriptions(id) ON DELETE CASCADE
);

-- Tabela de exames
CREATE TABLE exams (
                       id BINARY(16) PRIMARY KEY,
                       patient_id BINARY(16) NOT NULL,
                       requesting_doctor_id BINARY(16) NOT NULL,
                       medical_record_id BINARY(16),
                       type VARCHAR(100) NOT NULL,
                       status ENUM('REQUESTED','COMPLETED','CANCELLED') NOT NULL,
                       request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       completion_date TIMESTAMP,
                       result TEXT,
                       file_data LONGBLOB,
                       FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE RESTRICT,
                       FOREIGN KEY (requesting_doctor_id) REFERENCES doctors(id) ON DELETE RESTRICT,
                       FOREIGN KEY (medical_record_id) REFERENCES medical_records(id) ON DELETE SET NULL
);

-- Índices para exams
CREATE INDEX idx_exams_patient ON exams(patient_id);
CREATE INDEX idx_exams_status ON exams(status);
