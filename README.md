# Petmeds API

Esta é uma API desenvolvida para o gerenciamento de registros de animais e medicamentos em uma clínica veterinária.

## Stacks Utilizadas

*   **Java 21**
*   **Spring Boot 4.0.6**
*   **Spring Data JPA**
*   **PostgreSQL** (Banco de dados)
*   **Lombok** (Produtividade)
*   **Spring Dotenv** (Gerenciamento de variáveis de ambiente)

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

## Contribuidores
*   [Luiz Rodrigues](https://github.com/luizr) (Desenvolvedor)
