# Documentação da API VetCare-API

Esta documentação detalha todos os endpoints, autenticação, modelos de dados (schemas) e contratos atualizados para o desenvolvimento do Frontend e integração com a aplicação VetCare.

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

### Registros de Vacinação
* **ApplicationStatus**: `APPLIED` (Aplicada), `PENDING` (Pendente), `LATE` (Atrasada)

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

#### Usuário Perfil Detalhado (`UserProfileDTO`)
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

#### Animal Descrição Completa (`AnimalDescriptionDTO`)
```json
{
  "name": "Rex",
  "specie": "Cachorro",
  "race": "Vira-lata",
  "age": 3,
  "weight": 14.2,
  "gender": "MAS",
  "nameOwner": "Maria Souza"
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

#### Medicamento Resposta (`MedicineResponseDTO`)
```json
{
  "id": 1,
  "name": "Amoxicilina 250mg",
  "manufacturer": "Nacional Farma",
  "type": "pill",
  "indicatedSpecies": "Cachorro, Gato",
  "dosage": "1 comprimido a cada 12 horas",
  "animal": {
    "id": 5,
    "name": "Rex",
    "specie": "Cachorro",
    "race": "Vira-lata",
    "gender": "MAS"
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

### 3.5. Histórico Clínico
#### Histórico Requisição (`HistoryRequestDTO`)
```json
{
  "idAnimal": 5,
  "idLastCheckup": 1
}
```

#### Histórico Resposta (`HistoryResponseDTO`)
```json
{
  "id": 1,
  "idAnimal": 5,
  "nameAnimal": "Rex",
  "descriptionAnimal": {
    "name": "Rex",
    "specie": "Cachorro",
    "race": "Vira-lata",
    "age": 3,
    "weight": 14.2,
    "gender": "MAS",
    "nameOwner": "Maria Souza"
  },
  "idCheckup": 1,
  "descriptionCheckup": {
    "examinationDate": "2026-09-13",
    "species": "Cachorro",
    "sex": "MAS",
    "animalId": 5,
    "veterinarianId": 1
  },
  "dateCheckup": "2026-09-13T15:30:00",
  "idMedic": 1
}
```

---

### 3.6. Registros de Vacinação
#### Cadastro de Vacinação (`VaccinationRecordCreateDTO`)
```json
{
  "animalId": 5,
  "vaccineId": 2,
  "medicId": 1,
  "applicationDate": "2026-09-13 10:00:00",
  "numOfDoses": 1,
  "observation": "Primeira dose da vacina antirrábica aplicada sem intercorrências.",
  "applicationStatus": "APPLIED"
}
```

#### Edição de Vacinação (`RequestEditVaccinationRecord`)
```json
{
  "applicationDate": "2026-09-13 10:00:00",
  "numOfDoses": 1,
  "observation": "Atualização na observação da aplicação.",
  "applicationStatus": "APPLIED"
}
```

#### Resposta de Vacinação (`VaccinationRecordResponseDTO`)
```json
{
  "id": 1,
  "animal": {
    "id": 5,
    "name": "Rex",
    "specie": "Cachorro",
    "race": "Vira-lata",
    "gender": "MAS"
  },
  "vaccine": {
    "id": 2,
    "vaccineName": "Antirrábica",
    "description": "Vacina contra raiva",
    "numOfDoses": 1,
    "dayInterval": 365,
    "specie": "Cachorro"
  },
  "medic": {
    "id": 1,
    "name": "Dr. João Silva",
    "email": "joao@vetcare.com",
    "typeUser": "MED"
  },
  "applicationDate": "2026-09-13 10:00:00",
  "numOfDoses": 1,
  "observation": "Aplicada com sucesso.",
  "applicationStatus": "APPLIED"
}
```

---

### 3.7. Vacinas
#### Vacina Entity / Cadastro (`VaccineEntity`)
*Nota: Ao realizar o cadastro (`POST`), o campo `id` deve ser omitido.*
```json
{
  "vaccineName": "V10 / Múltipla",
  "description": "Protege contra cinomose, parvovirose, hepatite, etc.",
  "numOfDoses": 3,
  "dayInterval": 21,
  "specie": "Cachorro"
}
```

---

### 3.8. Imagens e Arquivos
#### Resposta de Imagem (`ImageResponseDTO`)
```json
{
  "name": "avatar.png",
  "typeContent": "image/png",
  "data": "base64_or_binary_bytes"
}
```

#### Resposta de Upload de Arquivo (`UploadFileResponseDTO`)
```json
{
  "fileName": "documento.pdf",
  "fileDownloadUri": "http://localhost:8080/files-test/download/documento.pdf",
  "fileType": "application/pdf",
  "size": 102400
}
```

---

## 4. Endpoints por Módulo

### 4.1. Autenticação e Usuários (`/user`)

Os endpoints `/user/login`, `/user` (`POST` - cadastro) e `/user/logout` possuem regras específicas de acesso. O login e cadastro são públicos, enquanto o logout exige cabeçalho de autorização. Os demais exigem token JWT.

| Método | Endpoint | Descrição | Requer Auth | Payloads / Detalhes |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/user/login` | Autenticação no sistema | Não | **Body**: `LoginRequestDTO`<br>**Response**: `ResponseLoginDTO` |
| **POST** | `/user` | Cadastro de novo usuário | Não | **Body**: `UserDTO`<br>**Response**: `ResponseLoginDTO` |
| **DELETE**| `/user/logout` | Encerra a sessão atual | Sim (Header)* | **Headers**: `Authorization: Bearer <token>`<br>**Response**: `ResponseLoginDTO` |
| **GET** | `/user/all` | Lista paginada de usuários | Sim | **Query**: `page`, `size`<br>**Response**: `Page<UserEntity>` |
| **GET** | `/user/{id}` | Perfil detalhado do usuário por ID | Sim | **Path**: `id`<br>**Response**: `UserProfileDTO` |
| **GET** | `/user/email/{email}`| Busca usuário por e-mail | Sim | **Path**: `email`<br>**Response**: `UserEntity` |
| **PUT** | `/user/update/{id}`| Atualiza um usuário existente | Sim | **Path**: `id`, **Body**: `UserEntity`<br>**Response**: `ResponseLoginDTO` |
| **DELETE**| `/user/{id}` | Remove um usuário do sistema | Sim | **Path**: `id`<br>**Response**: `ResponseLoginDTO` |
| **POST** | `/user/image/{id}` | Adiciona imagem de perfil ao usuário | Sim | **Path**: `id`, **Param**: `image` (MultipartFile)<br>**Response**: `ImageUserResponseDTO` |
| **PATCH**| `/user/image/{id}` | Atualiza imagem de perfil do usuário | Sim | **Path**: `id`, **Param**: `image` (MultipartFile)<br>**Response**: `ImageUserResponseDTO` |
| **GET** | `/user/image/{id}` | Recupera arquivo de imagem do usuário | Sim | **Path**: `id`<br>**Response**: `Resource` (Binary Image) |
| **DELETE**| `/user/image/{id}`| Remove imagem de perfil do usuário | Sim | **Path**: `id`<br>**Response**: Status `204 No Content` |

---

### 4.2. Gestão de Animais (`/animal`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/animal` | Cadastra um novo animal | **Body**: `AnimalEntity`<br>**Response**: `ResponseDTO` |
| **POST** | `/animal/addMedicine`| Vincula medicamento existente a um animal | **Query Params**: `idAnimal`, `idMedicine`<br>**Response**: `ResponseDTO` |
| **POST** | `/animal/image/{id}`| Insere imagem ao cadastro do animal | **Path**: `id`, **Param**: `image` (MultipartFile)<br>**Response**: `AnimalSimpleDescriptionDTO` |
| **GET** | `/animal/id/{id}` | Detalhes do animal por ID | **Path**: `id`<br>**Response**: `AnimalEntity` |
| **GET** | `/animal/all` | Lista paginada de animais (com resumo) | **Query**: `page`, `size`<br>**Response**: `Page<AnimalDescriptionDTO>` |
| **GET** | `/animal/name/{name}`| Busca animais por nome (paginado) | **Path**: `name`, **Query**: `page`, `size`<br>**Response**: `Page<AnimalEntity>` |
| **GET** | `/animal/image/{id}`| Recupera arquivo de imagem do animal | **Path**: `id`<br>**Response**: `Resource` (Binary Image) |
| **PUT** | `/animal/{id}` | Atualiza dados de um animal | **Path**: `id`, **Body**: `AnimalEntity`<br>**Response**: `ResponseDTO` |
| **PATCH**| `/animal/image/{id}`| Atualiza a imagem do animal | **Path**: `id`, **Param**: `image` (MultipartFile)<br>**Response**: `Resource` |
| **DELETE**| `/animal/{id}` | Remove animal do sistema | **Path**: `id`<br>**Response**: Status `204 No Content` |
| **DELETE**| `/animal/image/{id}`| Remove a imagem do animal | **Path**: `id`<br>**Response**: Status `204 No Content` |

---

### 4.3. Gestão de Medicamentos (`/medicine`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **GET** | `/medicine/{id}` | Detalhes do medicamento por ID | **Path**: `id`<br>**Response**: `MedicineResponseDTO` |
| **GET** | `/medicine/name/{name}`| Busca medicamentos por nome (paginado)| **Path**: `name`, **Query**: `page`, `size`<br>**Response**: `Page<MedicineResponseDTO>` |
| **GET** | `/medicine/all` | Lista paginada de medicamentos | **Query**: `page`, `size`<br>**Response**: `Page<MedicineResponseDTO>` |
| **POST** | `/medicine` | Cadastra novo medicamento | **Body**: `MedicineEntity`<br>**Response**: `MedicineResponseDTO` |
| **PUT** | `/medicine/{id}` | Atualiza medicamento existente | **Path**: `id`, **Body**: `MedicineEntity`<br>**Response**: `MedicineResponseDTO` |
| **DELETE**| `/medicine/{id}` | Exclui medicamento por ID | **Path**: `id`<br>**Response**: `ResponseDTO` |

---

### 4.4. Gestão de Check-Ups Iniciais (`/initialCheckUp`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/initialCheckUp` | Cadastra novo check-up inicial | **Body**: `InitialCheckUpDTO`<br>**Response**: `InitialCheckUpResponseDTO` |
| **PUT** | `/initialCheckUp/edit/{id}`| Atualiza check-up existente | **Path**: `id`, **Body**: `InitialCheckUpDTO`<br>**Response**: `InitialCheckUpResponseDTO` |
| **GET** | `/initialCheckUp/all` | Lista paginada de check-ups | **Query**: `page`, `size`<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **GET** | `/initialCheckUp/{id}` | Detalhes de um check-up por ID | **Path**: `id`<br>**Response**: `InitialCheckUpResponseDTO` |
| **GET** | `/initialCheckUp/animal/{animalId}`| Lista check-ups por Animal (paginado) | **Path**: `animalId`, **Query**: `page`, `size`<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **GET** | `/initialCheckUp/vet/{veterinarianId}`| Lista check-ups por Veterinário (paginado) | **Path**: `veterinarianId`, **Query**: `page`, `size`<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **GET** | `/initialCheckUp/date-range` | Busca check-ups por período | **Query**: `start` (`yyyy-MM-dd`), `end` (`yyyy-MM-dd`), `page`, `size`<br>**Response**: `Page<InitialCheckUpResponseDTO>` |
| **DELETE**| `/initialCheckUp/{id}` | Remove um check-up | **Path**: `id`<br>**Response**: `ResponseDTO` |

---

### 4.5. Gestão de Históricos Clínicos (`/history`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/history` | Cadastra novo registro de histórico | **Body**: `HistoryRequestDTO`<br>**Response**: `HistoryResponseDTO` |
| **PUT** | `/history/edit/{id}` | Atualiza histórico existente | **Path**: `id`, **Body**: `HistoryRequestDTO`<br>**Response**: `HistoryResponseDTO` |
| **GET** | `/history/all` | Lista paginada de históricos | **Query**: `page`, `size`<br>**Response**: `Page<HistoryResponseDTO>` |
| **GET** | `/history/{id}` | Busca histórico por ID | **Path**: `id`<br>**Response**: `HistoryResponseDTO` |
| **DELETE**| `/history/{id}` | Remove histórico do sistema | **Path**: `id`<br>**Response**: `ResponseDTO` |

---

### 4.6. Gestão de Registros de Vacinação (`/vaccination-record`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/vaccination-record` | Cadastra novo registro de vacinação | **Body**: `VaccinationRecordCreateDTO`<br>**Response**: `ResponseDTO` |
| **GET** | `/vaccination-record/id/{id}` | Busca registro de vacina por ID | **Path**: `id`<br>**Response**: `VaccinationRecordResponseDTO` |
| **GET** | `/vaccination-record/all` | Lista paginada de registros de vacina | **Query**: `page`, `size`<br>**Response**: `Page<VaccinationRecordResponseDTO>` |
| **GET** | `/vaccination-record/animal/{idAnimal}`| Lista registros por ID do animal | **Path**: `idAnimal`, **Query**: `page`, `size`<br>**Response**: `Page<VaccinationRecordResponseDTO>` |
| **GET** | `/vaccination-record/medic/{medic}` | Lista registros por ID do médico | **Path**: `medic`, **Query**: `page`, `size`<br>**Response**: `Page<VaccinationRecordResponseDTO>` |
| **GET** | `/vaccination-record/vaccine/{vaccine}`| Lista registros por ID da vacina | **Path**: `vaccine`, **Query**: `page`, `size`<br>**Response**: `Page<VaccinationRecordResponseDTO>` |
| **GET** | `/vaccination-record/date` | Busca registros por data específica | **Query**: `date` (`yyyy-MM-dd`), `page`, `size`<br>**Response**: `Page<VaccinationRecordResponseDTO>` |
| **GET** | `/vaccination-record/between-date` | Busca registros por intervalo de datas | **Query**: `startDate`, `endDate`, `page`, `size`<br>**Response**: `Page<VaccinationRecordResponseDTO>` |
| **GET** | `/vaccination-record/status` | Busca registros por status de aplicação | **Query**: `status`, `page`, `size`<br>**Response**: `Page<VaccinationRecordResponseDTO>` |
| **PUT** | `/vaccination-record/{id}` | Atualiza registro de vacinação | **Path**: `id`, **Body**: `RequestEditVaccinationRecord`<br>**Response**: `VaccinationRecordResponseDTO` |

---

### 4.7. Gestão de Vacinas (`/vaccine`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **GET** | `/vaccine/{id}` | Busca vacina por ID | **Path**: `id`<br>**Response**: `VaccineEntity` |
| **GET** | `/vaccine/name/{name}` | Busca vacinas por nome (paginado) | **Path**: `name`, **Query**: `page`, `size`<br>**Response**: `Page<VaccineEntity>` |
| **GET** | `/vaccine/all` | Lista paginada de vacinas | **Query**: `page`, `size`<br>**Response**: `Page<VaccineEntity>` |
| **POST** | `/vaccine` | Cadastra nova vacina | **Body**: `VaccineEntity`<br>**Response**: `VaccineEntity` |
| **PUT** | `/vaccine/{id}` | Atualiza vacina existente | **Path**: `id`, **Body**: `VaccineEntity`<br>**Response**: `VaccineEntity` |
| **DELETE**| `/vaccine/{id}` | Remove vacina por ID | **Path**: `id`<br>**Response**: Status `204 No Content` |

---

### 4.8. Gestão de Imagens (`/image`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/image` | Cadastra/faz upload de nova imagem | **Param**: `data` (MultipartFile)<br>**Response**: `ImageResponseDTO` |
| **GET** | `/image/{id}` | Busca metadados da imagem por ID | **Path**: `id`<br>**Response**: `ImageResponseDTO` |
| **GET** | `/image/file/{id}` | Baixa/exibe o arquivo de imagem | **Path**: `id`<br>**Response**: `Resource` (Binary Image) |
| **DELETE**| `/image/{id}` | Deleta imagem por ID | **Path**: `id`<br>**Response**: Status `200/204` |

---

### 4.9. Testes de Armazenamento de Arquivos (`/files-test`)

Todos os endpoints requerem autenticação por token JWT.

| Método | Endpoint | Descrição | Payloads / Detalhes |
| :--- | :--- | :--- | :--- |
| **POST** | `/files-test/upload`| Faz upload de arquivo de teste | **Param**: `file` (MultipartFile)<br>**Response**: `UploadFileResponseDTO` |

---

## 5. Respostas Padrão do Sistema

### 5.1. Resposta de Sucesso Comum (`ResponseDTO`)
Retornada em operações como criações, atualizações e exclusões:
```json
{
  "timestamp": "2026-09-13",
  "message": "Operação realizada com sucesso",
  "description": "Detalhes sobre o resultado da operação"
}
```

### 5.2. Tratamento de Erros (`ErrorDTO`)
Em caso de falha de validação, recurso não encontrado ou erro de permissão:
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
As rotas de listagem (`/all`, busca por nome/status/data) retornam metadados de paginação estruturados pelo Spring Framework. Os itens da página ficam sempre na chave `content`.

**Exemplo de requisição:** `GET http://localhost:8080/animal/all?page=0&size=10`

### 6.2. Gerenciamento do Token de Sessão (JWT)
1. **Armazenamento Seguro**: Após autenticar via `POST /user/login`, armazene o `token` (ex: `localStorage` ou `sessionStorage`).
2. **Interceptors**: Adicione o token automaticamente nas requisições HTTP protegidas:
   ```javascript
   axios.interceptors.request.use(config => {
     const token = localStorage.getItem('token');
     if (token) {
       config.headers.Authorization = `Bearer ${token}`;
     }
     return config;
   });
   ```
3. **Tratamento de Expirado (401/403)**: Redirecione o usuário para a tela de login ao receber `401 Unauthorized` ou `403 Forbidden`.
