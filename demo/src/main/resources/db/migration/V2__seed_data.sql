-- V2__seed_data.sql
-- Dados de exemplo para testes

-- Senha para todos: "password123" (hash BCrypt)
-- $2a$10$ZGfT5Q3mJH8Z.W5x5F5x5e5x5x5x5x5x5x5x5x5x5x5x5x5x5x5

-- Inserir Admin
INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           UNHEX(REPLACE(UUID(), '-', '')),
           '00000000001',
           'Admin System',
           'admin@hospital.com',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkm',
           '11999999999',
           '1980-01-01',
           'ADMIN',
           TRUE,
           CURRENT_TIMESTAMP
       );

-- Inserir Médico 1 (Cardiologista)
SET @doctor1_id = UNHEX(REPLACE(UUID(), '-', ''));
INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           @doctor1_id,
           '11111111111',
           'Dr. João Silva',
           'joao.silva@hospital.com',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkm',
           '11988888888',
           '1975-05-15',
           'DOCTOR',
           TRUE,
           CURRENT_TIMESTAMP
       );

INSERT INTO health_professionals (id, registration_number, specialty, work_unit)
VALUES (@doctor1_id, 'CRM123456', 'Cardiologia', 'Unidade Central');

INSERT INTO doctors (id, crm, specialties, accepts_telemedicine)
VALUES (@doctor1_id, 'CRM123456-SP', '["Cardiologia", "Medicina Interna"]', TRUE);

-- Inserir Médico 2 (Pediatra)
SET @doctor2_id = UNHEX(REPLACE(UUID(), '-', ''));
INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           @doctor2_id,
           '22222222222',
           'Dra. Maria Santos',
           'maria.santos@hospital.com',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkm',
           '11977777777',
           '1982-08-20',
           'DOCTOR',
           TRUE,
           CURRENT_TIMESTAMP
       );

INSERT INTO health_professionals (id, registration_number, specialty, work_unit)
VALUES (@doctor2_id, 'CRM789012', 'Pediatria', 'Unidade Infantil');

INSERT INTO doctors (id, crm, specialties, accepts_telemedicine)
VALUES (@doctor2_id, 'CRM789012-SP', '["Pediatria"]', FALSE);

-- Inserir Paciente de Exemplo
SET @patient1_id = UNHEX(REPLACE(UUID(), '-', ''));
INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           @patient1_id,
           '33333333333',
           'Carlos Oliveira',
           'carlos.oliveira@email.com',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkm',
           '11966666666',
           '1990-03-10',
           'PATIENT',
           TRUE,
           CURRENT_TIMESTAMP
       );

INSERT INTO patients (id, sus_card, insurance, blood_type, allergies, family_history, emergency_contact)
VALUES (
           @patient1_id,
           '123456789012345',
           'Unimed',
           'O+',
           'Penicilina',
           'Histórico de hipertensão na família',
           'Ana Oliveira - 11955555555'
       );

-- Criar prontuário para o paciente
INSERT INTO medical_records (id, patient_id, last_updated)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), @patient1_id, CURRENT_TIMESTAMP);
