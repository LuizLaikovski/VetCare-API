# VetCare API

Esta é uma API RESTful desenvolvida para o gerenciamento de registros clínicos em uma clínica veterinária, incluindo cadastro de animais, medicamentos, usuários e check-ups iniciais.

## 🚀 Tecnologias Utilizadas

*   **Java 21**
*   **Spring Boot 3.4.2**
*   **Spring Data JPA**
*   **Spring Security**
*   **JWT (java-jwt)** para autenticação
*   **PostgreSQL** (Banco de dados)
*   **Lombok** (Produtividade)
*   **Swagger/OpenAPI** (Documentação de API)

## 📋 Pré-requisitos

*   Java 21 ou superior instalado.
*   Maven 3.x instalado.
*   PostgreSQL em execução.

## ⚙️ Configuração

1.  Clone o repositório.
2.  Crie um arquivo `.env` na raiz do projeto com as credenciais de banco de dados e segredos da aplicação:
    ```env
    DB_URL=jdbc:postgresql://localhost:5432/vetcare
    DB_USERNAME=seu_usuario
    DB_PASSWORD=sua_senha
    JWT_SECRET=seu_segredo_jwt
    ```

## 🚀 Como Executar

Utilize o Maven Wrapper para executar a aplicação:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## 📚 Documentação da API

A API conta com documentação interativa via **Swagger UI**. Após rodar a aplicação, acesse:
`http://localhost:8080/swagger-ui.html`

## ✅ Como Rodar os Testes

Para executar todos os testes automatizados:

```bash
./mvnw test
```

## 🔒 Segurança e Padronização

*   **Autenticação**: O acesso às rotas é protegido por JWT. Utilize o endpoint `/user/login` para obter o token e inclua-o no header `Authorization: Bearer <seu_token>` nas demais requisições.
*   **Respostas**: Todas as rotas utilizam `ResponseEntity` para garantir retornos HTTP semânticos (ex: 201 para criações, 204 para exclusões, 404 para recursos não encontrados).
*   **Paginação**: Endpoints de listagem (`/all`) suportam paginação via parâmetros de query: `?page=0&size=10`.
*   **Tratamento de Erros**: Erros são tratados globalmente via `GlobalExceptionHandler`, retornando objetos JSON padronizados (`ErrorDTO`).
