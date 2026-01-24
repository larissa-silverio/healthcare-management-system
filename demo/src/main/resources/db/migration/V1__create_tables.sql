-- V1__create_tables.sql - VERSÃO FINAL COM TODAS AS COLUNAS

CREATE TABLE users (
                       id BINARY(16) PRIMARY KEY,
                       cpf VARCHAR(11) UNIQUE,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(15),
                       birth_date DATE,
                       role ENUM('PATIENT','DOCTOR','NURSE','ADMIN') NOT NULL,
                       active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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

CREATE TABLE health_professionals (
                                      id BINARY(16) PRIMARY KEY,
                                      registration_number VARCHAR(20) UNIQUE NOT NULL,
                                      specialty VARCHAR(100),
                                      work_unit VARCHAR(100),
                                      FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE doctors (
                         id BINARY(16) PRIMARY KEY,
                         crm VARCHAR(20) UNIQUE NOT NULL,
                         specialties TEXT,
                         accepts_telemedicine BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (id) REFERENCES health_professionals(id) ON DELETE CASCADE
);

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

CREATE TABLE telemedicine_sessions (
                                       id BINARY(16) PRIMARY KEY,
                                       appointment_id BINARY(16) UNIQUE NOT NULL,
                                       session_link VARCHAR(500),
                                       FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);

CREATE TABLE prescriptions (
                               id BINARY(16) PRIMARY KEY,
                               appointment_id BINARY(16) NOT NULL,
                               notes TEXT,
                               digital_signature TEXT, -- COLUNA ADICIONADA AQUI
                               date_issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);

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

CREATE TABLE exams (
                       id BINARY(16) PRIMARY KEY,
                       appointment_id BINARY(16) NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       result TEXT,
                       status VARCHAR(20),
                       FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);

CREATE TABLE medical_records (
                                 id BINARY(16) PRIMARY KEY,
                                 patient_id BINARY(16) NOT NULL,
                                 appointment_id BINARY(16),
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_updated TIMESTAMP,
                                 FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
                                 FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE SET NULL
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_appointments_date ON appointments(appointment_date);
