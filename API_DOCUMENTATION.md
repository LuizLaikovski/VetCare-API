# Documentação da API VetCare-API

Esta documentação detalha os endpoints, autenticação, modelos de dados (schemas) e contratos para o desenvolvimento do Frontend da aplicação VetCare.

---

## 1. Configurações Gerais

- **Base URL**: `http://localhost:8080` (Ajustar conforme o ambiente)
- **Autenticação**: Stateless JWT. O token deve ser enviado no cabeçalho `Authorization` em todas as requisições protegidas.
  - **Formato**: `Authorization: Bearer <seu_token>`
  - **Tipo**: `Bearer Token`

---

## 2. Dicionário de Enums (Tipos Estritos)

Para garantir a consistência nos formulários e nas seleções do Frontend, utilize os seguintes enums estritos exatamente como definidos no backend:

### Usuários
* **TypeUser**: `ADM` (Administrador), `MED` (Médico Veterinário), `CLIENT` (Tutor/Cliente)

### Animais
* **Specie**: `Cachorro`, `Gato`, `Ave`
* **Sex**: `MAS` (Macho), `FEM` (Fêmea)

### Medicamentos
* **TypeMedicine**: `pill` (Comprimido), `utilities` (Pomada/Outros), `liquid` (Líquido)

### Check-Ups Iniciais
* **ArterialPulse**: `REGULAR`, `IRREGULAR`, `WEAK`
* **MucosaStatus**: `PINK_NORMAL` (Rosada), `CONGESTED_HYPEREMIC` (Congesta), `PALE_HYPOCHROMIC` (Pálida), `CYANOTIC` (Cianótica), `ICTERIC` (Ictérica)
* **ConsciousnessLevel**: `ALERT` (Alerta), `DEPRESSED` (Deprimido), `COMA` (Comatoso)
* **NutritionalStatus**: `CACHEXIC` (Caquético), `THIN` (Magro), `NORMAL` (Normal), `OVERWEIGHT` (Sobrepeso), `OBESITY_GRADE_1`, `OBESITY_GRADE_2`, `OBESITY_GRADE_3`
* **Behavior**: `DOCILE` (Dócil), `RESTLESS` (Inquieto), `AGGRESSIVE` (Agressivo), `FEARFUL` (Medroso)
* **ExamStatus**: `NORMAL`, `ABNORMAL` (Utilizado para avaliar sistemas e órgãos individuais como olhos, orelhas, sistema respiratório, etc.)

---

## 3. Esquemas de Dados (JSON Payloads)

### 3.1. Usuário / Autenticação
#### Login Request (`LoginRequestDTO`)
```json
{
  "email": "vet@vetcare.com",
  "password": "senha"
}
```

#### User Cadastro (`UserDTO`)
```json
{
  "name": "Dr. João Silva",
  "email": "joao@vetcare.com",
  "password": "senha",
  "typeUser": "MED"
}
```

#### Login / Logout Response (`ResponseLoginDTO`)
```json
{
  "response": "Login realizado com sucesso",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "Dr. João Silva",
    "email": "joao@vetcare.com",
    "typeUser": "MED"
  }
}
```

#### Usuário Completo (`UserEntity`)
```json
{
  "id": 1,
  "name": "Dr. João Silva",
  "email": "joao@vetcare.com",
  "typeUser": "MED",
  "animals": []
}
```

---

### 3.2. Animais
#### Animal Entity / Cadastro (`AnimalEntity`)
*Nota: Ao realizar o cadastro (`POST`), o campo `id` deve ser omitido.*
```json
{
  "name": "Rex",
  "specie": "Cachorro",
  "race": "Vira-lata",
  "age": 3,
  "weight": 14.2,
  "gender": "MAS",
  "owner": {
    "id": 2
  }
}
```

#### Animal Resposta Simplificada (`AnimalResponseDTO`)
```json
{
  "id": 5,
  "name": "Rex",
  "specie": "Cachorro",
  "race": "Vira-lata",
  "gender": "MAS"
}
```

---

### 3.3. Medicamentos
#### Medicamento Entity / Cadastro (`MedicineEntity`)
*Nota: Ao realizar o cadastro (`POST`), o campo `id` deve ser omitido.*
```json
{
  "name": "Amoxicilina 250mg",
  "manufacturer": "Nacional Farma",
  "type": "pill",
  "indicatedSpecies": "Cachorro, Gato",
  "dosage": "1 comprimido a cada 12 horas",
  "animal": {
    "id": 5
  }
}
```

---

### 3.4. Check-Up Inicial
#### Check-Up Cadastro / Atualização (`InitialCheckUpDTO`)
```json
{
  "examinationDate": "2026-09-13",
  "species": "Cachorro",
  "sex": "MAS",
  "animalId": 5,
  "veterinarianId": 1,
  "crt": "Menor que 2s",
  "rr": "22 mpm",
  "hr": "110 bpm",
  "tpc": "Mucosas rosadas",
  "hydration": "Normohidratado",
  "arterialPulse": "REGULAR",
  "mucousMembranes": "PINK_NORMAL",
  "consciousnessLevel": "ALERT",
  "nutritionalStatus": "NORMAL",
  "behavior": "DOCILE",
  "hasEctoparasites": false,
  "ectoparasitesDetails": "Nenhum parasita detectado",
  "postureMovement": "NORMAL",
  "eyes": "NORMAL",
  "ears": "NORMAL",
  "oralCavity": "NORMAL",
  "respiratory": "NORMAL",
  "circulatory": "NORMAL",
  "hemolymphatic": "NORMAL",
  "digestive": "NORMAL",
  "genital": "NORMAL",
  "urinary": "NORMAL",
  "nervous": "NORMAL",
  "skinAndAppendages": "NORMAL",
  "otherStatus": "NORMAL",
  "otherObservations": "Paciente ativo e saudável."
}
```

#### Check-Up Resposta Completa (`InitialCheckUpResponseDTO`)
```json
{
  "id": 1,
  "examinationDate": "2026-09-13T15:30:00",
  "species": "Cachorro",
  "sex": "MAS",
  "animal": {
    "id": 5,
    "name": "Rex",
    "specie": "Cachorro",
    "race": "Vira-lata",
    "gender": "MAS"
  },
  "veterinarian": {
    "id": 1,
    "name": "Dr. João Silva",
    "email": "joao@vetcare.com",
    "typeUser": "MED"
  },
  "crt": "Menor que 2s",
  "rr": "22 mpm",
  "hr": "110 bpm",
  "tpc": "Mucosas rosadas",
  "hydration": "Normohidratado",
  "arterialPulse": "REGULAR",
  "mucousMembranes": "PINK_NORMAL",
  "consciousnessLevel": "ALERT",
  "nutritionalStatus": "NORMAL",
  "behavior": "DOCILE",
  "hasEctoparasites": false,
  "ectoparasitesDetails": "Nenhum parasita detectado",
  "postureMovement": "NORMAL",
  "eyes": "NORMAL",
  "ears": "NORMAL",
  "oralCavity": "NORMAL",
  "respiratory": "NORMAL",
  "circulatory": "NORMAL",
  "hemolymphatic": "NORMAL",
  "digestive": "NORMAL",
  "genital": "NORMAL",
  "urinary": "NORMAL",
  "nervous": "NORMAL",
  "skinAndAppendages": "NORMAL",
  "otherStatus": "NORMAL",
  "otherObservations": "Paciente ativo e saudável."
}
```

---

## 4. Endpoints por Módulo

### 4.1. Autenticação e Usuários (`/user`)

Os endpoints `/user/login`, `/user/create` e `/user/logout` são **públicos** (livres de validação de token do Spring Security). No entanto, o endpoint `/user/logout` necessita que você envie o header de autorização para invalidar o respectivo token. Os demais exigem token JWT no cabeçalho.

| Método | Endpoint | Descrição | Requer Auth | Payloads / Detalhes |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/user/login` | Autenticação no sistema | Não | **Body**: `LoginRequestDTO`<br>**Response**: `ResponseLoginDTO` |
| **POST** | `/user/create` | Cadastro de novo usuário | Não | **Body**: `UserDTO`<br>**Response**: `ResponseLoginDTO` |
| **DELETE**| `/user/logout` | Encerra a sessão | Não (Spring)* | **Headers**: `Authorization: Bearer <token>`<br>**Response**: `ResponseLoginDTO` |
| **GET** | `/user/all` | Lista paginada de usuários | Sim | **Query**: `page`, `size` (Paginado)<br>**Response**: `Page<UserEntity>` |
| **GET** | `/user/{id}` | Detalhes do usuário por ID | Sim | **Path**: `id`<br>**Response**: `UserEntity` |
| **GET** | `/user/email/{email}`| Detalhes do usuário por e-mail| Sim | **Path**: `email`<br>**Response**: `UserEntity` |
| **PUT** | `/user/update/{id}`| Atualiza um usuário | Sim | **Path**: `id`<br>**Body**: `UserEntity`<br>**Response**: `ResponseLoginDTO` |
| **DELETE**| `/user/{id}` | Deleta um usuário | Sim | **Path**: `id`<br>**Response**: `ResponseLoginDTO` |

---

### 4.2. Gestão de Animais (`/animal`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/animal/create` | Cadastra um novo animal | **Body**: `AnimalEntity`<br>**Response**: `ResponseDTO` (sucesso) |
| **POST** | `/animal/createAll` | Cadastra lista de novos animais | **Body**: `List<AnimalEntity>`<br>**Response**: `ResponseDTO` (sucesso) |
| **POST** | `/animal/addMedicine` | Vincula medicamento existente a um animal | **Query/Request Params**:<br>• `idAnimal` (Long)<br>• `idMedicine` (Long)<br>**Response**: `ResponseDTO` |
| **GET** | `/animal/id/{id}` | Detalhes do animal por ID | **Path**: `id`<br>**Response**: `AnimalEntity` |
| **GET** | `/animal/all` | Lista paginada de animais | **Query**: `page`, `size` (Paginado)<br>**Response**: `Page<AnimalEntity>` |
| **GET** | `/animal/{name}` | Busca animal pelo nome | **Path**: `name`<br>**Response**: `AnimalEntity` (ou `404 Not Found` caso inexistente) |
| **PUT** | `/animal/edit/{id}` | Atualiza dados do animal | **Path**: `id`<br>**Body**: `AnimalEntity`<br>**Response**: `ResponseDTO` (sucesso) |
| **DELETE**| `/animal/{id}` | Exclui animal do sistema | **Path**: `id`<br>**Response**: Status `204 No Content` (sem corpo de resposta) |

---

### 4.3. Gestão de Medicamentos (`/medicine`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **GET** | `/medicine/{id}` | Detalhes do medicamento por ID | **Path**: `id`<br>**Response**: `MedicineEntity` |
| **GET** | `/medicine/name/{name}`| Busca medicamentos pelo nome | **Path**: `name`<br>**Response**: `List<MedicineEntity>` |
| **GET** | `/medicine/all` | Lista paginada de medicamentos | **Query**: `page`, `size` (Paginado)<br>**Response**: `Page<MedicineEntity>` |
| **POST** | `/medicine/create` | Cadastra novo medicamento | **Body**: `MedicineEntity`<br>**Response**: `MedicineEntity` cadastrado |
| **POST** | `/medicine/createAll` | Cadastra múltiplos medicamentos | **Body**: `List<MedicineEntity>`<br>**Response**: `ResponseLoginDTO` |
| **PUT** | `/medicine/{id}` | Atualiza medicamento por ID | **Path**: `id`<br>**Body**: `MedicineEntity`<br>**Response**: `MedicineEntity` atualizado |
| **DELETE**| `/medicine/{id}` | Exclui medicamento por ID | **Path**: `id`<br>**Response**: `ResponseLoginDTO` |

---

### 4.4. Gestão de Check-Ups Iniciais (`/initialCheckUp`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/initialCheckUp/create` | Cadastra novo check-up inicial | **Body**: `InitialCheckUpDTO`<br>**Response**: `InitialCheckUpResponseDTO` |
| **PUT** | `/initialCheckUp/edit/{id}`| Atualiza check-up existente | **Path**: `id`<br>**Body**: `InitialCheckUpDTO`<br>**Response**: `InitialCheckUpResponseDTO` |
| **GET** | `/initialCheckUp/all` | Lista paginada de check-ups | **Query**: `page`, `size` (Paginado)<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **GET** | `/initialCheckUp/{id}` | Detalhes de um check-up por ID | **Path**: `id`<br>**Response**: `InitialCheckUpResponseDTO` |
| **GET** | `/initialCheckUp/animal/{animalId}`| Lista paginada de checkups por Animal | **Path**: `animalId`<br>**Query**: `page`, `size`<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **GET** | `/initialCheckUp/vet/{veterinarianId}`| Lista paginada de checkups por Veterinário | **Path**: `veterinarianId`<br>**Query**: `page`, `size`<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **GET** | `/initialCheckUp/date-range` | Busca check-ups por período | **Query**:<br>• `start` (Data inicial `yyyy-MM-dd`) <br>• `end` (Data final `yyyy-MM-dd`) <br>• `page`, `size` (Paginado)<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **DELETE**| `/initialCheckUp/{id}` | Remove um check-up | **Path**: `id`<br>**Response**: `ResponseDTO` (sucesso com mensagem) |

---

## 5. Respostas Padrão do Sistema

### 5.1. Resposta de Sucesso Comum (`ResponseDTO`)
Retornada em operações de sucesso como criações, atualizações e exclusões em algumas rotas:
```json
{
  "timestamp": "2026-09-13",
  "message": "Operação realizada com sucesso",
  "description": "Detalhes sobre o resultado da operação"
}
```

### 5.2. Tratamento de Erros (`ErrorDTO`)
Em caso de falha de validação, recurso não encontrado ou erro de permissão, a API retornará o seguinte objeto:
```json
{
  "timestamp": "2026-09-13",
  "message": "Mensagem descritiva do erro ocorrido",
  "description": "Detalhes técnicos ou de contexto da exceção (ex: path acessado)"
}
```

---

## 6. Guia Prático para Integração do Frontend

### 6.1. Como Consumir Rotas Paginadas (`Spring Pageable`)
As rotas de listagem (`/all`) retornam um objeto contendo metadados de paginação estruturados pelo Spring Framework. 
**Importante**: O conteúdo real das entidades estará sempre dentro da chave `content`.

**Estrutura completa de uma resposta paginada:**
```json
{
  "content": [
    { "id": 1, "name": "Rex", "specie": "Cachorro" },
    { "id": 2, "name": "Mimi", "specie": "Gato" }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 5,
  "totalElements": 48,
  "last": false,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}
```

#### Parâmetros que o Frontend deve enviar:
* `page`: O número da página (indexado em `0`. Ex: página 1 é `0`, página 2 é `1`).
* `size`: A quantidade de registros retornados por página (ex: `10` ou `20`).

*Exemplo de requisição:* `GET http://localhost:8080/animal/all?page=0&size=10`

---

### 6.2. Gerenciamento do Token de Sessão (JWT)
1. **Armazenamento Seguro**: Após fazer o `POST /user/login`, armazene o `token` de maneira segura no cliente (por exemplo, em `localStorage` ou `sessionStorage`, ou cookies HTTP-Only no caso de BFF).
2. **Interceptors**: Utilize interceptores em sua biblioteca de requisições HTTP (como o `Axios Interceptor` no React/Vue ou `HttpInterceptor` no Angular) para adicionar o token automaticamente em todas as chamadas subsequentes:
   ```javascript
   // Exemplo com Axios
   axios.interceptors.request.use(config => {
     const token = localStorage.getItem('token');
     if (token) {
       config.headers.Authorization = `Bearer ${token}`;
     }
     return config;
   });
   ```
3. **Tratamento de Expirado (401/403)**: Se uma chamada de API falhar com `401 Unauthorized`, o Frontend deve redirecionar o usuário imediatamente para a tela de login e limpar as credenciais salvas localmente.
