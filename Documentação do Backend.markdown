# API Backend Gestão de Funcionários

Uma API RESTful desenvolvida com Spring Boot 3.4.5 e Java 21 para gerenciar cargos (`/cargos`) e empregados (`/empregados`). Suporta operações CRUD, paginação, filtragem, validação de entrada e integração com um frontend Vue.js.

## Funcionalidades
- **Operações CRUD**: Criar, ler, atualizar e excluir cargos e empregados.
- **Paginação e Filtragem**: Listagem paginada de empregados com filtros por nome e e-mail.
- **Validação de Entrada**: Usa DTOs com `@NotBlank`, `@NotNull` e restrições personalizadas.
- **Tratamento de Exceções**: Retorna `400 Bad Request` para erros de validação, `404 Not Found` para recursos inválidos, `409 Conflict` para duplicatas.
- **Banco de Dados H2**: Banco em memória para desenvolvimento e testes.
- **CORS**: Configurado para o frontend em `http://localhost:5173`.
- **Suporte a Docker**: Implantação em contêiner.

## Pré-requisitos
- Java 21
- Maven 3.8+
- Docker (opcional para implantação em contêiner)
- Postman (para testar endpoints)

## Instruções de Configuração

1. **Clonar o Repositório**:
   ```bash
   git clone <url-do-repositório>
   cd gestao-de-funcionarios/backend
   ```

2. **Compilar o Projeto**:
   ```bash
   mvn clean install
   ```

3. **Executar a Aplicação**:
   ```bash
   mvn spring-boot:run
   ```
   - A API estará disponível em `http://localhost:8080`.

4. **Acessar o Console do H2**:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:gestao`
   - Usuário: `sa`
   - Senha: (em branco)
   - Execute `SELECT * FROM ROLE;` ou `SELECT * FROM EMPLOYEE;` para verificar dados.

5. **Executar com Docker** (opcional):
   ```bash
   docker build -t gestao-funcionarios .
   docker run -p 8080:8080 gestao-funcionarios
   ```

## Endpoints da API

### Cargos (`/cargos`)

| Método | Endpoint            | Descrição                     | Corpo da Requisição                              | Status da Resposta | Corpo da Resposta                             |
|--------|---------------------|-------------------------------|-------------------------------------------|---------------------|-------------------------------------------|
| POST   | `/cargos`           | Criar um novo cargo           | `{"name": "Desenvolvedor", "descricao": "Desenvolvedor de software"}` | 201 Criado / 409 Conflito | `{"id": 1, "name": "Desenvolvedor", "descricao": "Desenvolvedor de software"}` ou `{"error": "Conflito", "details": "Cargo com nome já existe"}` |
| GET    | `/cargos`           | Listar todos os cargos        | -                                         | 200 OK          | `[{"id": 1, "name": "Desenvolvedor", "descricao": "Desenvolvedor de software"}]` |
| GET    | `/cargos/{id}`      | Obter um cargo por ID         | -                                         | 200 OK / 404    | `{"id": 1, "name": "Desenvolvedor", "descricao": "Desenvolvedor de software"}` |
| PUT    | `/cargos/{id}`      | Atualizar um cargo            | `{"name": "Desenvolvedor Senior", "descricao": "Desenvolvedor experiente"}` | 200 OK / 404 | `{"id": 1, "name": "Desenvolvedor Senior", "descricao": "Desenvolvedor experiente"}` |
| DELETE | `/cargos/{id}`      | Excluir um cargo              | -                                         | 204 Sem Conteúdo / 404 | - |

**Erros de Validação** (ex.: `name` ausente):
- Status: `400 Bad Request`
- Resposta: `{"error": "Validação falhou", "details": {"name": "Nome é obrigatório"}}`

**Erros de Conflito** (ex.: `name` duplicado):
- Status: `409 Conflito`
- Resposta: `{"error": "Conflito", "details": "Cargo com nome já existe"}`

### Empregados (`/empregados`)

| Método | Endpoint                     | Descrição                     | Corpo da Requisição / Parâmetros de Consulta                                   | Status da Resposta | Corpo da Resposta                             |
|--------|------------------------------|-------------------------------|---------------------------------------------------------------|---------------------|-------------------------------------------|
| POST   | `/empregados`                | Criar um novo empregado       | `{"nome": "João Silva", "email": "joao@example.com", "dataAdmissao": "2025-05-20", "cargo": {"id": 1}}` | 201 Criado / 409 Conflito | `{"id": 1, "nome": "João Silva", ...}` ou `{"error": "Conflito", "details": "Empregado com e-mail já existe"}` |
| GET    | `/empregados`                | Listar empregados (paginado)  | `?page=0&size=10&nome=João&email=joao@example.com`            | 200 OK          | `{"content": [{"id": 1, "nome": "João Silva", ...}], "totalPages": 1, ...}` |
| GET    | `/empregados/{id}`           | Obter um empregado por ID     | -                                                             | 200 OK / 404    | `{"id": 1, "nome": "João Silva", ...}`    |
| PUT    | `/empregados/{id}`           | Atualizar um empregado        | `{"nome": "João Silva Jr", "email": "joao.jr@example.com", ...}` | 200 OK / 404 | `{"id": 1, "nome": "João Silva Jr", ...}` |
| DELETE | `/empregados/{id}`           | Excluir um empregado          | -                                                             | 204 Sem Conteúdo / 404 | - |

**Paginação e Filtragem**:
- Parâmetros: `page` (base 0), `size` (itens por página), `nome` (correspondência parcial), `email` (correspondência exata).
- Exemplo: `GET /empregados?page=0&size=5&nome=João`

**Erros de Validação** (ex.: `nome` ausente):
- Status: `400 Bad Request`
- Resposta: `{"error": "Validação falhou", "details": {"nome": "Nome é obrigatório"}}`

**Erros de Conflito** (ex.: `email` duplicado):
- Status: `409 Conflito`
- Resposta: `{"error": "Conflito", "details": "Empregado com e-mail já existe"}`

## Tratamento de Erros
- **400 Bad Request**: Entrada inválida (ex.: campos obrigatórios ausentes).
- **404 Not Found**: Recurso não encontrado (ex.: ID inválido).
- **405 Method Not Allowed**: Métodos não suportados (ex.: `POST /`).
- **409 Conflict**: Recurso duplicado (ex.: nome de cargo ou e-mail de empregado já existe).
- **500 Internal Server Error**: Erros inesperados (registrados para depuração).

## Notas
- **CORS**: Configurado para aceitar requisições de `http://localhost:5173` (frontend Vue.js).
- **Banco H2**: Em memória, reiniciado a cada execução. Use o console H2 para inspecionar as tabelas `ROLE` e `EMPLOYEE`.
- **Problemas Resolvidos**:
  - `NoResourceFoundException` para `POST /` e `favicon.ico` (tratado por `HomeController` e `WebMvcConfig`).
  - `MethodArgumentNotValidException` para `POST /cargos` (retorna `400` com detalhes claros).

## Testes
- Use **Postman** para testar os endpoints.
- Exemplo de Coleção Postman:
  - `POST http://localhost:8080/cargos`
    - Corpo: `{"name": "Desenvolvedor", "descricao": "Desenvolvedor de software"}`
    - Verifique `201 Criado` ou `409 Conflito` (se duplicado).
  - `POST http://localhost:8080/empregados`
    - Corpo: `{"nome": "João Silva", "email": "joao@example.com", "dataAdmissao": "2025-05-20", "cargo": {"id": 1}}`
    - Verifique `201 Criado` ou `409 Conflito` (se duplicado).
  - `GET http://localhost:8080/empregados?page=0&size=10&nome=João`
    - Verifique `200 OK` com resposta paginada.
- Após cada requisição, use o console H2 para verificar os dados:
  - Execute `SELECT * FROM ROLE;` para cargos.
  - Execute `SELECT * FROM EMPLOYEE;` para empregados.
- Salve capturas de tela das requisições Postman e do console H2 na pasta `screenshots/`.

🖼️ Capturas de Tela
As capturas de tela das requisições e da interface do banco de dados foram registradas para validação e documentação. Você pode encontrá-las na pasta:

\gestão de funcionários\screenshots

## Estrutura do Projeto
```
backend/
├── src/main/java/com/example/employeemanagement/
│   ├── controller/ (RoleController, EmployeeController, HomeController)
│   ├── service/ (RoleService, EmployeeService)
│   ├── dto/ (RoleDTO, EmployeeDTO)
│   ├── entity/ (Role, Employee)
│   ├── repository/ (RoleRepository, EmployeeRepository)
│   ├── exception/ (GlobalExceptionHandler)
│   ├── config/ (CorsConfig, WebMvcConfig)
├── src/main/resources/
│   ├── application.properties
├── pom.xml
```
