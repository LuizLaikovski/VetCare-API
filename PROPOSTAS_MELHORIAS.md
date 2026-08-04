# Propostas de Melhorias e Novas Funcionalidades - VetCare PetMeds

Este documento apresenta sugestões de evolução para o sistema **VetCare PetMeds**, com foco em torná-lo um ERP veterinário robusto, escalável e de fácil manutenção.

---

## 1. Novas Funcionalidades de Negócio

*   **Prontuário Eletrônico Completo:**
    *   Substituir a simples vinculação de medicamentos por um histórico clínico estruturado.
    *   Adicionar campos para: Anamnese, Exame Físico, Diagnóstico, Prescrições, Evoluções diárias.
*   **Gestão de Agendamento:**
    *   Módulo de agendamento de consultas e procedimentos.
    *   Status de agendamento (Agendado, Confirmado, Realizado, Cancelado).
*   **Controle de Estoque:**
    *   Monitoramento automático de entrada e saída de medicamentos.
    *   Alertas de estoque mínimo para reposição.
*   **Financeiro Básico:**
    *   Cadastro de serviços prestados e seus respectivos preços.
    *   Emissão de recibos/faturas simples para clientes.
*   **Notificações (Lembretes):**
    *   Lembretes de vacinas e retornos via e-mail ou WhatsApp (integração externa).

## 2. Arquitetura e Código

*   **Refatoração da Camada de Serviço:**
    *   Utilizar *Interfaces* para todas as classes de serviço (`AnimalService` -> `IAnimalService`) para facilitar a implementação de decoradores (para logs, auditoria, cache) e mocks em testes.
*   **Padronização de DTOs e Mappers:**
    *   Introduzir o uso de bibliotecas como **MapStruct** para realizar a conversão automática entre `Entity` e `DTO`, reduzindo código *boilerplate*.
*   **Versionamento da API:**
    *   Implementar versionamento nas rotas (ex: `/api/v1/animal`) para evitar quebras futuras.
*   **Documentação da API:**
    *   Integrar **SpringDoc OpenAPI (Swagger)** para documentação automática das rotas.

## 3. Segurança

*   **Auditoria:**
    *   Implementar logs de auditoria para ações críticas (ex: exclusão de registros, alteração de prontuários) registrando **quem** fez, **quando** fez e **o que** alterou.
*   **RBAC (Role-Based Access Control) Fino:**
    *   Refinar as permissões no `SecurityConfig` para garantir que um `CLIENT` só acesse seus próprios animais/prescrições e um `MED` não possa excluir usuários, por exemplo.
*   **Validação de Dados:**
    *   Aumentar o uso de *Bean Validation* (`@NotNull`, `@Size`, `@Email`, `@Positive`) nas classes de DTO para garantir a integridade antes de chegar ao banco de dados.

## 4. Experiência de Desenvolvedor e DevOps

*   **Testes Automatizados:**
    *   Aumentar a cobertura de testes de integração, focando nos cenários de negócio mais críticos (ex: fluxo completo de prescrição).
*   **Monitoramento e Observabilidade:**
    *   Integrar **Spring Boot Actuator** para monitorar a saúde da aplicação, métricas de JVM e uso de memória.
*   **Dockerização:**
    *   Criar um `Dockerfile` e `docker-compose.yml` para facilitar o ambiente de desenvolvimento e deploy em ambiente de produção (ou cloud).

---

*Gerado como parte da análise técnica para o projeto VetCare PetMeds.*
