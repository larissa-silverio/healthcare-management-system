-- V2__seed_data.sql
-- Dados de exemplo para testes

-- Senha para todos: "password123" (hash BCrypt)
-- $2a$10$ZGfT5Q3mJH8Z.W5x5F5x5e5x5x5x5x5x5x5x5x5x5x5x5x5x5x5

-- Inserir Admin
INSERT INTO users (id, cpf, name, email, password, phone, birth_date, role, active, created_at)
VALUES (
           UUID_TO_BIN(UUID(), true),
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

-- Médico 1
SET @doctor1_id = UUID_TO_BIN(UUID(), true);

INSERT INTO users VALUES (
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

INSERT INTO health_professionals VALUES
    (@doctor1_id, 'CRM123456', 'Cardiologia', 'Unidade Central');

INSERT INTO doctors VALUES
    (@doctor1_id, 'CRM123456-SP', 'Cardiologia, Medicina Interna', TRUE);

-- Médico 2
SET @doctor2_id = UUID_TO_BIN(UUID(), true);

INSERT INTO users VALUES (
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

INSERT INTO health_professionals VALUES
    (@doctor2_id, 'CRM789012', 'Pediatria', 'Unidade Infantil');

INSERT INTO doctors VALUES
    (@doctor2_id, 'CRM789012-SP', 'Pediatria', FALSE);

-- Paciente
SET @patient1_id = UUID_TO_BIN(UUID(), true);

INSERT INTO users VALUES (
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

INSERT INTO patients VALUES (
                                @patient1_id,
                                '123456789012345',
                                'Unimed',
                                'O+',
                                'Penicilina',
                                'Histórico de hipertensão na família',
                                'Ana Oliveira - 11955555555'
                            );

INSERT INTO medical_records
VALUES (UUID_TO_BIN(UUID(), true), @patient1_id, CURRENT_TIMESTAMP);
