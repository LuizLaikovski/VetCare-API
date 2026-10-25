# VetCare API

Esta é uma API RESTful desenvolvida para o gerenciamento de registros clínicos em uma clínica veterinária, incluindo cadastro de animais, medicamentos, usuários, check-ups iniciais e controle de histórico de vacinação.

## 🚀 Tecnologias Utilizadas

*   **Java 21**
*   **Spring Boot 3.4.2**
*   **Spring Data JPA**
*   **Flyway** para migrações de banco de dados
*   **Spring Security**
*   **JWT (java-jwt)** para autenticação
*   **PostgreSQL** (Banco de dados)
*   **Lombok** (Produtividade)
*   **Swagger/OpenAPI** (Documentação de API)
*   **Docker & Docker Compose**

## 📋 Pré-requisitos

*   Java 21 ou superior instalado (caso vá rodar localmente).
*   Maven 3.x instalado.
*   Docker e Docker Compose instalados (caso vá rodar via containers).

## ⚙️ Configuração

1.  Clone o repositório.
2.  Crie um arquivo `.env` na raiz do projeto com as credenciais de banco de dados e segredos da aplicação (opcional se utilizar o Docker Compose padrão):
    ```env
    DB_URL=jdbc:postgresql://localhost:5432/vetcare
    DB_USER=seu_usuario
    DB_PASSWORD=sua_senha
    DB_DRIVER=org.postgresql.Driver
    API_SECURITY_TOKEN_SECRET=seu_segredo_jwt
    ```

## 🚀 Como Executar

### Localmente (Maven)
Utilize o Maven Wrapper para executar a aplicação:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### 🐳 Via Docker (Docker Compose)
Para executar a aplicação junto com o banco de dados PostgreSQL utilizando Docker Compose:

1. Gere o pacote executável (JAR) da aplicação:
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. Suba os containers com o Docker Compose:
   ```bash
   docker-compose up --build
   ```

O serviço da API estará disponível em `http://localhost:8080` e o PostgreSQL na porta `5432`.

## 📚 Documentação da API

A API conta com documentação interativa via **Swagger UI**. Após rodar a aplicação, acesse:
`http://localhost:8080/doc` ou `http://localhost:8080/swagger-ui.html`

## ✅ Como Rodar os Testes

Para executar todos os testes automatizados:

```bash
./mvnw test
```

## 🔒 Segurança e Padronização

*   **Autenticação**: O acesso às rotas é protegido por JWT. Utilize o endpoint de login para obter o token e inclua-o no header `Authorization: Bearer <seu_token>` nas demais requisições.
*   **Módulos Principais**:
    *   **Usuários / Médicos**: Gestão de acessos e perfis (Administrador e Médico).
    *   **Animais**: Cadastro de pacientes com informações de espécie, raça, sexo e peso.
    *   **Check-up Inicial**: Avaliação clínica detalhada (temperamento, mucosas, pulso, etc.).
    *   **Medicamentos**: Prescrições e controle de fármacos associados aos animais.
    *   **Vacinação (Vaccination Records)**: Registro detalhado de aplicações de vacinas por animal, vacina e médico veterinário responsável, com status de aplicação.
*   **Tratamento de Erros**: Erros são tratados globalmente via `GlobalExceptionHandler`, retornando objetos JSON padronizados.
