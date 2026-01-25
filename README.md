SGHSS - Sistema de Gestão Hospitalar e de Serviços de Saúde
O SGHSS é uma plataforma de backend desenvolvida para a instituição hospitalar VidaPlus. O sistema foi projetado sob uma estratégia de microsserviços e implementado utilizando os princípios da Clean Architecture, focando na escalabilidade, segurança e interoperabilidade no setor de saúde.

🚀 Tecnologias Utilizadas
Linguagem: Java 17

Framework: Spring Boot 3.2.0

Persistência: Spring Data JPA / Hibernate 6.3.1

Banco de Dados: MySQL 8.0

Migração de Dados: Flyway 9.22.3

Segurança: Spring Security com JWT (JSON Web Token)

Documentação: SpringDoc OpenAPI (Swagger)

Utilitários: Lombok, MapStruct

🏗️ Arquitetura e Padrões de Projeto
O projeto segue a Clean Architecture, dividindo as responsabilidades em camadas claras:

Domain: Entidades de negócio e regras puras.

Application: Serviços que orquestram os casos de uso.

Infrastructure: Implementações técnicas, persistência e segurança.

Interfaces: Pontos de entrada da API (REST Controllers).

Design Patterns Implementados
Strategy Pattern: Utilizado para gerenciar diferentes tipos de agendamento (Presencial vs. Telemedicina) de forma extensível.

Factory Pattern: Centraliza a lógica de criação de agendamentos complexos.

🛠️ Funcionalidades Principais
Gestão de Pacientes: Cadastro e consulta de prontuário.

Agendamentos: Criação de consultas presenciais e por telemedicina com geração de protocolos únicos.

Atendimento Médico: Finalização de consultas, emissão de prescrições com múltiplos medicamentos e solicitações de exames.

Segurança: Autenticação baseada em perfis de acesso (Admin, Doctor, Patient, Nurse).

🧪 Tratamento de Erros (RFC 7807)
A API implementa o padrão RFC 7807 (Problem Details for HTTP APIs). Em caso de erro, o sistema retorna um JSON padronizado:

JSON

{
  "type": "about:blank",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "Patient not found with id: '...'",
  "instance": "/api/patients/...",
  "timestamp": "2026-01-25T..."
}
⚙️ Como Executar o Projeto
Pré-requisitos
JDK 17 ou superior

MySQL 8.0

Configuração do Banco de Dados
Crie o esquema no MySQL:

SQL

CREATE DATABASE sghss_dev;
Configure as credenciais no arquivo src/main/resources/application-dev.yml.

Execução
No terminal, utilize o Maven Wrapper:

Bash

./mvnw spring-boot:run
O sistema utilizará o Flyway para criar automaticamente as tabelas e popular os dados iniciais (Seed).

📖 Documentação da API
Após iniciar o servidor, a documentação interativa (Swagger) estará disponível em: http://localhost:8080/swagger-ui.html
