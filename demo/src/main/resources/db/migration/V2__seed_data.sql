INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           UUID_TO_BIN(UUID(), true),
           '00000000001',
           'Admin System',
           'admin@hospital.com',
           '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- password123
           '11999999999',
           '1980-01-01',
           'ADMIN',
           TRUE,
           CURRENT_TIMESTAMP
       );


SET @doctor1_id = UUID_TO_BIN(UUID(), true);

INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           @doctor1_id,
           '11111111111',
           'Dr. João Silva',
           'joao.silva@hospital.com',
           '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
           '11988888888',
           '1975-05-15',
           'DOCTOR',
           TRUE,
           CURRENT_TIMESTAMP
       );

INSERT INTO health_professionals (id, registration_number, specialty, work_unit)
VALUES (@doctor1_id, 'CRM123456', 'Cardiologia', 'Unidade Central');

INSERT INTO doctors (id, crm, specialties, accepts_telemedicine)
VALUES (@doctor1_id, 'CRM123456-SP', 'Cardiologia, Medicina Interna', TRUE);

SET @doctor2_id = UUID_TO_BIN(UUID(), true);

INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           @doctor2_id,
           '22222222222',
           'Dra. Maria Santos',
           'maria.santos@hospital.com',
           '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
           '11977777777',
           '1982-08-20',
           'DOCTOR',
           TRUE,
           CURRENT_TIMESTAMP
       );

INSERT INTO health_professionals (id, registration_number, specialty, work_unit)
VALUES (@doctor2_id, 'CRM789012', 'Pediatria', 'Unidade Infantil');

INSERT INTO doctors (id, crm, specialties, accepts_telemedicine)
VALUES (@doctor2_id, 'CRM789012-SP', 'Pediatria', FALSE);

SET @patient1_id = UUID_TO_BIN(UUID(), true);

INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           @patient1_id,
           '33333333333',
           'Carlos Oliveira',
           'carlos.oliveira@email.com',
           '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
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


INSERT INTO medical_records (id, patient_id, last_updated)
VALUES (
           UUID_TO_BIN(UUID(), true),
           @patient1_id,
           CURRENT_TIMESTAMP
       );
