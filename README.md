**SGHSS - Build & Run Guide**

Este guia descreve os passos técnicos para compilar e executar o backend do Sistema de Gestão Hospitalar e Serviços de Saúde.

📋 Pré-requisitos
Java JDK 17
Maven 3.8+ (ou utilizar o wrapper incluso)
MySQL 8.0

🗄️ Configuração do Banco de Dados
Acesse seu terminal MySQL e crie o esquema:

```CREATE DATABASE sghss_dev;```

As tabelas e dados iniciais são gerenciados automaticamente pelo Flyway na inicialização.

O script de estrutura localiza-se em: ```src/main/resources/db/migration/V1__create_tables.sql```
O script de dados iniciais localiza-se em: ```src/main/resources/db/migration/V2__seed_data.sql```

⚙️ Configuração de Ambiente
Edite o arquivo src/main/resources/application-dev.yml para ajustar as credenciais do seu banco de dados local:

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sghss_dev
    username: seu_usuario
    password: sua_senha
```

🛠️ Build e Execução
1. Limpar e Compilar

Utilize o Maven Wrapper para baixar as dependências e compilar o projeto:

```
./mvnw clean install
```

2. Executar a Aplicação
   
```
./mvnw spring-boot:run
```
A aplicação estará disponível em http://localhost:8080.

📍 Endpoints Úteis
Swagger UI (Documentação): http://localhost:8080/swagger-ui.html

API Docs (JSON): http://localhost:8080/v3/api-docs

🧪 Testes
Para executar a suíte de testes unitários e de integração:

./mvnw test
