# 🧠 Desafio Técnico – Sistema de Gestão de Projetos e Demandas

## 📘 Contexto

Desenvolvimento de uma API RESTful em Java com Spring Boot para gerenciar projetos e tarefas (demandas) de uma empresa. O sistema permite a organização de entregas, acompanhamento do status das tarefas e gerenciamento de usuários com diferentes níveis de acesso.

## ✨ Funcionalidades Implementadas

* **Autenticação e Autorização:**
    * Registro de usuários (`/register`).
    * Login com geração de token JWT (`/login`).
    * Segurança baseada em Roles (MANAGER, EMPLOYEE).
    * O primeiro usuário registrado recebe a role MANAGER.
    * Filtro JWT para proteger endpoints.

* **Gerenciamento de Usuários (CRUD):**
    * Criação (registro).
    * Busca por ID, Email e Nome (com paginação para listagem geral).
    * Atualização de dados do usuário (com permissões granulares: MANAGER pode editar todos, EMPLOYEE apenas a si mesmo).
    * Atualização de role do usuário (apenas MANAGER).
    * Exclusão de usuário (com permissões granulares).

* **Gerenciamento de Projetos (CRUD):**
    * Criação, Leitura (todos e por ID), Atualização e Exclusão de projetos (restrito ao MANAGER).
    * Busca de projetos por nome.
    * Listagem paginada de projetos.

* **Gerenciamento de Tarefas (CRUD):**
    * Criação de tarefas vinculadas a um projeto e usuário (apenas MANAGER).
    * Leitura de tarefas (todas, por ID, com filtros).
    * Filtro dinâmico por status, priority, projectId e title usando Spring Data JPA Specifications.
    * Atualização de status e outros dados da tarefa (MANAGER e EMPLOYEE).
    * Exclusão de tarefas (apenas MANAGER).
    * Listagem paginada de tarefas.

* **Infraestrutura e Boas Práticas:**
    * Uso de Docker Compose para orquestrar a aplicação, banco de dados (PostgreSQL), Flyway, **Redis, Prometheus e Grafana.**
    * Migrações de banco de dados gerenciadas pelo Flyway.
    * Perfis de Configuração (`local` para desenvolvimento na IDE, `docker` para execução no contêiner).
    * Validação de dados de entrada com Bean Validation.
    * Tratamento global de exceções com `@ControllerAdvice`.
    * Uso de DTOs (Records) para entrada e saída de dados.
    * Mapeamento entre Entidades e DTOs implementado manualmente.
    * Documentação da API com Swagger / OpenAPI.
    * Configuração de CORS para permitir acesso do frontend.
    * Externalização de configurações sensíveis (senhas, segredo JWT) via arquivo `.env`.
    * **Cache de Aplicação com Spring Boot Cache e Redis para otimizar consultas frequentes.**
    * **Monitoramento e Observabilidade com Spring Boot Actuator, Prometheus e Grafana.**

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3+
* **Persistência:** Spring Data JPA, Hibernate
* **Banco de Dados:** PostgreSQL (via Docker)
* **Cache:** **Spring Boot Cache, Redis (via Docker)**
* **Migrações:** Flyway
* **Segurança:** Spring Security, JWT (Auth0 Java JWT)
* **Monitoramento:** **Spring Boot Actuator, Micrometer, Prometheus, Grafana**
* **Testes:** **JUnit 5, Mockito**
* **Validação:** Bean Validation (Hibernate Validator)
* **Documentação:** Springdoc OpenAPI (Swagger UI)
* **Conteinerização:** Docker, Docker Compose
* **Build:** Maven

*(Observação: O mapeamento entre Entidades e DTOs foi implementado manualmente).*

---

## 🧱 Modelagem de Domínio (Final)

A modelagem original foi estendida para incluir a entidade User, necessária para a autenticação e atribuição de responsabilidades.

#### User

| Campo | Tipo | Descrição |
| :--- | :--- | :--- |
| id | UUID | Identificador |
| userName | String | Nome de usuário (obrigatório, min 4) |
| email | String | Email (obrigatório, único, formato válido) |
| password | String | Senha (obrigatório, hash BCrypt) |
| role | Enum (MANAGER/EMPLOYEE) | Papel do usuário (obrigatório) |
| createdAt | LocalDateTime | Data de criação |
| updatedAt | LocalDateTime | Data de atualização |
| projects | List<Project> | Projetos associados (OneToMany) |
| tasks | List<Task> | Tarefas associadas (OneToMany) |

#### Project

| Campo | Tipo | Descrição |
| :--- | :--- | :--- |
| id | Long | Identificador (Sequence) |
| name | String | Nome (obrigatório, min 3, max 100) |
| description | String | Opcional |
| startDate | LocalDateTime | Início do projeto (automático) |
| updateAt | LocalDateTime | Última atualização (automático) |
| endDate | LocalDateTime | Opcional |
| user | User | Usuário criador/responsável (ManyToOne, obrigatório) |
| tasks | List<Task> | Tarefas do projeto (OneToMany) |

#### Task

| Campo | Tipo | Descrição |
| :--- | :--- | :--- |
| id | Long | Identificador (Sequence) |
| title | String | Título (obrigatório, min 5, max 150) |
| description | String | Descrição (obrigatório) |
| status | Enum (TODO/DOING/DONE) | Status (padrão TODO) |
| priority | Enum (LOW/MEDIUM/HIGH) | Prioridade (obrigatório) |
| createdAt | LocalDateTime | Data de criação (automático) |
| updatedAt | LocalDateTime | Última atualização (automático) |
| dueDate | LocalDateTime | Data limite (obrigatório, futuro) |
| project | Project | Projeto ao qual pertence (ManyToOne, obrigatório) |
| user | User | Usuário atribuído (ManyToOne, obrigatório) |

---

## 🌐 Endpoints da API

A base da URL é `http://localhost:5050`.

#### Autenticação (`/api/v1/auth`)
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| POST | /register | Registra um novo usuário | Público |
| POST | /login | Efetua login e retorna um token JWT | Público |

#### Usuários (`/api/v1/users`)
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| GET | / | Lista todos os usuários (paginado) | MANAGER, EMPLOYEE |
| GET | /{id} | Busca usuário por ID | MANAGER, EMPLOYEE |
| GET | /email?email={email} | Busca usuário por email | MANAGER, EMPLOYEE |
| GET | /searchName?name={name} | Busca usuários por nome | MANAGER, EMPLOYEE |
| PUT | /{id} | Atualiza dados do usuário | MANAGER (todos), EMPLOYEE (apenas si mesmo) |
| PATCH | /{id}/role | Atualiza a role do usuário | MANAGER |
| DELETE | /{id} | Deleta usuário | MANAGER (todos), EMPLOYEE (apenas si mesmo) |

#### Projetos (`/api/v1/projects`)
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| POST | / | Cria novo projeto | MANAGER |
| GET | / | Lista todos os projetos (paginado) | MANAGER, EMPLOYEE |
| GET | /{id} | Busca projeto por ID | MANAGER, EMPLOYEE |
| GET | /search?name={name} | Busca projetos por nome | MANAGER, EMPLOYEE |
| PUT | /{id} | Atualiza projeto | MANAGER |
| DELETE | /{id} | Deleta projeto | MANAGER |

#### Tarefas (`/api/v1/tasks`)
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| POST | / | Cria nova tarefa | MANAGER |
| GET | / | Lista todas as tarefas (paginado) | MANAGER, EMPLOYEE |
| GET | /{id} | Busca tarefa por ID | MANAGER, EMPLOYEE |
| GET | /filter?params... | Busca tarefas com filtros (title, projectId, status, priority) | MANAGER, EMPLOYEE |
| PUT | /{id} | Atualiza dados da tarefa | MANAGER, EMPLOYEE |
| DELETE | /{id} | Deleta tarefa | MANAGER |

**#### Monitoramento (`/actuator`)**
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| GET | /actuator/health | Verifica a saúde da aplicação | Público |
| GET | /actuator/prometheus | Expõe métricas no formato Prometheus | Protegido (MANAGER) |

---

## ⚙️ Configuração do Ambiente

#### Variáveis de Ambiente
1.  Renomeie o arquivo `variaveis-de-ambiente.example.env` para `.env`.
2.  Preencha as variáveis neste arquivo:
    * `POSTGRES_DB`: Nome do banco de dados (ex: db-project-task).
    * `POSTGRES_USER`: Usuário do banco (ex: user_gustavo).
    * `POSTGRES_PASSWORD`: Senha do banco (ex: senha_gustavo).
    * **`REDIS_PASSWORD`**: **Senha para o Redis (ex: senha_redis).**
    * `PGADMIN_EMAIL`: Email para login no PgAdmin.
    * `PGADMIN_PASSWORD`: Senha para login no PgAdmin.
    * **`JWT_SECRET`**: **ESSENCIAL!** Defina uma chave secreta longa, aleatória e segura para assinar os tokens JWT.

---

## 🚀 Como Executar
Você pode executar a aplicação de duas maneiras:

#### 1. Usando Docker Compose (Recomendado)
Esta é a forma mais simples e garante que todos os serviços (aplicação, banco, migrações, **cache e monitoramento**) subam juntos.

1.  Certifique-se de ter o Docker e o Docker Compose instalados.
2.  Configure o arquivo `.env` como descrito acima.
3.  Abra um terminal na raiz do projeto e execute:
    ```bash
    docker compose up -d --build
    ```
4.  A aplicação estará disponível em `http://localhost:5050`.

#### 2. Localmente (IDE + Docker para Serviços)
Ideal para desenvolvimento e depuração.

1.  Certifique-se de ter o Docker e o Docker Compose instalados.
2.  Configure o arquivo `.env`.
3.  Suba apenas os serviços de infraestrutura (Banco, Flyway, PgAdmin, **Redis**):
    ```bash
    docker compose up -d postgres flyway pgadmin redis
    ```
4.  Configure sua IDE (IntelliJ, VS Code):
    * Instale o plugin `EnvFile`.
    * Vá em `Run -> Edit Configurations...`.
    * Selecione sua aplicação Spring Boot.
    * Na aba `EnvFile`, adicione o arquivo `.env` do projeto.
    * No campo **Active profiles**, digite `local`.
5.  Execute a aplicação (`Application.java`) a partir da sua IDE.
6.  A aplicação estará disponível em `http://localhost:5050`.

---

## 🧪 Testando a API com Arquivos .http
Este projeto inclui uma pasta `requests` na raiz contendo arquivos `.http`. Esses arquivos podem ser usados diretamente em IDEs como IntelliJ IDEA (com o HTTP Client embutido) ou VS Code (com a extensão REST Client) para testar os endpoints da API.

1.  **Obtenha um Token:** Primeiro, execute a requisição `auth_login.http` (após registrar um usuário com `auth_register.http`) para obter um token JWT.
2.  **Configure o Token:** Copie o token recebido e cole-o na variável `SEU_TOKEN_AQUI` dentro dos arquivos `.http` das requisições protegidas (ou configure um ambiente no seu HTTP Client).
3.  **Execute as Requisições:** Execute os arquivos `.http` desejados para interagir com a API.

---

## 🔗 Acesso à Aplicação
* **API Base URL:** `http://localhost:5050`
* **Swagger UI (Documentação):** `http://localhost:5050/swagger-ui.html`
* **PgAdmin (Gerenciador do Banco):** `http://localhost:5052` (use as credenciais do `.env`)
* **Prometheus (Métricas):** `http://localhost:5053`
* **Grafana (Dashboards):** `http://localhost:5054` (login padrão: admin/admin)

---

## 🧪 Testes Automatizados
**O projeto inclui testes unitários para a camada de serviço, utilizando JUnit 5 e Mockito para garantir a lógica de negócio e o isolamento dos componentes.**

---

## 🏅 Diferenciais Atendidos
* 🧭 Documentação Swagger / OpenAPI
* 🔐 Autenticação com JWT
* 🐳 Configuração de Docker / docker-compose
* **📈 Monitoramento e Métricas (Actuator, Prometheus, Grafana)**
* **⚡ Cache com Redis**
* **✅ Testes Unitários (JUnit 5, Mockito)**

*(Observação: MapStruct não foi utilizado, o mapeamento foi feito manualmente).*

---

## 🛠️ Tags
`#Java` `#SpringBoot` `#Backend` `#DesafioTecnico`
`#API` `#RestAPI` `#Docker` `#PostgreSQL` `#JPA` `#Flyway`
`#Swagger` `#JWT` `#SpringSecurity` `#BeanValidation`
`#CleanCode` `#SoftwareEngineering` **`#Redis` `#Cache` `#Actuator`**
**`#Prometheus` `#Grafana` `#JUnit` `#Mockito`**
