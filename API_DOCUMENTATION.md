# Documentação da API VetCare-API

Esta documentação detalha os endpoints, autenticação e contratos de dados para o desenvolvimento do Frontend da aplicação VetCare.

---

## 1. Configurações Gerais

- **Base URL**: `http://localhost:8080` (Ajustar conforme o ambiente)
- **Autenticação**: Stateless JWT. O token deve ser enviado no cabeçalho `Authorization` em todas as requisições protegidas.

---

## 2. Autenticação e Usuários (`/user`)

Endpoints públicos: `/user/login`, `/user/create`, `/user/logout`.
Os demais endpoints desta rota exigem autenticação.

| Método | Endpoint | Descrição | Autenticado |
| :--- | :--- | :--- | :--- |
| POST | `/user/login` | Realiza login e retorna token/dados. | Não |
| POST | `/user/create` | Cadastra novo usuário. | Não |
| DELETE | `/user/logout` | Realiza logout (requer token no header). | Não |
| GET | `/user/all` | Lista todos os usuários. | Sim |
| GET | `/user/{id}` | Busca usuário por ID. | Sim |
| GET | `/user/getEmail`| Busca usuário por email. | Sim |
| PUT | `/user/update/{id}`| Atualiza usuário. | Sim |
| DELETE | `/user/{id}` | Deleta usuário. | Sim |

---

## 3. Gestão de Animais (`/animal`)

Todos os endpoints exigem autenticação.

| Método | Endpoint | Descrição | Parâmetros |
| :--- | :--- | :--- | :--- |
| POST | `/animal/create` | Cria novo animal. | Body: `AnimalEntity` |
| POST | `/animal/createAll`| Cria múltiplos animais. | Body: `List<AnimalEntity>` |
| POST | `/animal/addMedicine`| Associa remédio a animal. | Body: idAnimal, idMedicine |
| GET | `/animal/id/{id}` | Busca animal por ID. | Path: `{id}` |
| GET | `/animal/all` | Lista animais (paginado). | Query: `page`, `size` |
| GET | `/animal/{name}` | Busca animal por nome. | Path: `{name}` |
| PUT | `/animal/edit/{id}` | Atualiza animal. | Path: `{id}` |
| DELETE | `/animal/{id}` | Deleta animal. | Path: `{id}` |

---

## 4. Gestão de Medicamentos (`/medicine`)

Todos os endpoints exigem autenticação.

| Método | Endpoint | Descrição | Parâmetros |
| :--- | :--- | :--- | :--- |
| GET | `/medicine/{id}` | Busca remédio por ID. | Path: `{id}` |
| GET | `/medicine/name/{name}`| Busca remédio por nome. | Path: `{name}`, Query: `medicineName` |
| GET | `/medicine/all` | Lista remédios (paginado). | Query: `page`, `size` |
| POST | `/medicine/create` | Cria novo remédio. | Body: `MedicineEntity` |
| POST | `/medicine/createAll`| Cria múltiplos remédios. | Body: `List<MedicineEntity>` |
| PUT | `/medicine/{id}` | Atualiza remédio. | Path: `{id}` |
| DELETE | `/medicine/{id}` | Deleta remédio. | Path: `{id}` |

---

## 5. Tratamento de Erros

A API retorna um objeto `ErrorDTO` em caso de erro, com a seguinte estrutura:

```json
{
  "timestamp": "yyyy-MM-dd",
  "message": "Mensagem de erro descritiva",
  "path": "Descrição da requisição/caminho"
}
```

---

## 6. Notas para o Frontend

1.  **Paginação**: Os endpoints `GET /animal/all` e `GET /medicine/all` utilizam `Pageable`. O frontend deve enviar parâmetros de query (`page`, `size`) para controlar a paginação (padrão: `page=0`, `size=10`).
2.  **Autenticação**: Armazene o JWT (retornado no login) de forma segura (ex: `localStorage` ou `sessionStorage`, considerando os riscos). Adicione ao header: `Authorization: Bearer <seu_token>`.
3.  **Content-Type**: Todas as requisições com `Body` devem definir `Content-Type: application/json`.
