# Petmeds API

Esta é uma API desenvolvida para o gerenciamento de registros de animais e medicamentos em uma clínica veterinária.

## Stacks Utilizadas

*   **Java 21**
*   **Spring Boot 4.0.6**
*   **Spring Data JPA**
*   **Spring Security**
*   **JWT (java-jwt)** para autenticação
*   **Spring Boot Validation**
*   **PostgreSQL** (Banco de dados)
*   **Lombok** (Produtividade)
*   **Spring Dotenv** (Gerenciamento de variáveis de ambiente)

## Funcionalidades de Segurança e Tratamento de Erro

*   **Hash de Senhas**: Todas as senhas de usuários são criptografadas utilizando `BCryptPasswordEncoder` antes de serem armazenadas no banco de dados.
*   **Tratamento de Erro Global**: A API utiliza `@ControllerAdvice` para capturar exceções automaticamente, retornando respostas padronizadas com os status HTTP adequados (ex: 404 para recursos não encontrados, 401 para credenciais inválidas) e uma mensagem explicativa no corpo da resposta (`ErrorDTO`).

## Pré-requisitos

*   Java 21 ou superior
*   Apache Maven 3.x
*   PostgreSQL configurado

## Como Rodar

1.  Clone o repositório.
2.  Crie um arquivo `.env` na raiz do projeto (certifique-se de configurar as variáveis necessárias para a conexão com o banco de dados e segredos da aplicação).
3.  Certifique-se que o banco de dados PostgreSQL esteja rodando conforme configurado no `.env`.
4.  Execute o comando para rodar a aplicação:
    ```bash
    ./mvnw spring-boot:run
    ```

## Rotas da API

### Animal (`/animal`)
*   `POST /animal/create`: Cadastra um novo animal.
*   `POST /animal/createAll`: Cadastra uma lista de animais.
*   `GET /animal/id/{id}`: Busca um animal pelo ID.
*   `GET /animal/all`: Lista todos os animais.
*   `GET /animal/{name}`: Busca um animal pelo nome.
*   `PUT /animal/edit/{id}`: Atualiza os dados de um animal.
*   `DELETE /animal/{id}`: Remove um animal.

### Medicine (`/medicine`)
*   `GET /medicine/{id}`: Busca um medicamento pelo ID.
*   `GET /medicine/{name}?medicineName={name}`: Busca medicamentos pelo nome.
*   `GET /medicine/all`: Lista todos os medicamentos.
*   `POST /medicine/create`: Cadastra um novo medicamento.
*   `POST /medicine/createAll`: Cadastra uma lista de medicamentos.
*   `PUT /medicine/{id}`: Atualiza um medicamento.
*   `DELETE /medicine/{id}`: Remove um medicamento.

### User (`/user`)
*   `GET /user/all`: Lista todos os usuários.
*   `POST /user/create`: Cadastra um novo usuário.
*   `GET /user/{id}`: Busca um usuário pelo ID.
*   `GET /user/getEmail`: Busca um usuário pelo e-mail.
*   `POST /user/login`: Realiza login.
*   `PUT /user/update`: Atualiza um usuário.
*   `DELETE /user/{id}`: Remove um usuário.
