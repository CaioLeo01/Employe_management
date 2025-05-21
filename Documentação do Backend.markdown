API Backend Gestão de Funcionários
Uma API RESTful desenvolvida com Spring Boot 3.4.5 e Java 21 para gerenciar cargos (/cargos) e empregados (/empregados). Suporta operações CRUD, paginação, filtragem, validação de entrada e integração com um frontend Vue.js.

⚙️ Funcionalidades
Operações CRUD: Criar, ler, atualizar e excluir cargos e empregados.

Paginação e Filtragem: Listagem paginada de empregados com filtros por nome e e-mail.

Validação de Entrada: Usa DTOs com @NotBlank, @NotNull e restrições personalizadas.

Tratamento de Exceções: Retorna 400 Bad Request para erros de validação, 404 Not Found para recursos inválidos, 409 Conflict para duplicatas.

Banco de Dados H2: Banco em memória para desenvolvimento e testes.

CORS: Configurado para o frontend em http://localhost:5173.

Suporte a Docker: Implantação em contêiner.

✅ Pré-requisitos
Java 21

Maven 3.8+

Docker (opcional para implantação em contêiner)

Postman (para testar endpoints)

🚀 Instruções de Configuração
Clonar o Repositório:

bash
Copy
Edit
git clone <url-do-repositório>
cd gestao-de-funcionarios/backend
Compilar o Projeto:

bash
Copy
Edit
mvn clean install
Executar a Aplicação:

bash
Copy
Edit
mvn spring-boot:run
A API estará disponível em http://localhost:8080.

Acessar o Console do H2:

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:gestao

Usuário: sa

Senha: (em branco)

Executar com Docker (opcional):

bash
Copy
Edit
docker build -t gestao-funcionarios .
docker run -p 8080:8080 gestao-funcionarios
📌 Endpoints da API
🔹 Cargos (/cargos)
Método	Endpoint	Descrição	Corpo da Requisição	Status da Resposta	Corpo da Resposta
POST	/cargos	Criar cargo	{"name": "Desenvolvedor", "descricao": "Desenvolvedor de software"}	201 / 409	Detalhes do cargo criado ou erro
GET	/cargos	Listar todos	-	200	Lista de cargos
GET	/cargos/{id}	Buscar por ID	-	200 / 404	Cargo específico
PUT	/cargos/{id}	Atualizar cargo	{"name": "...", "descricao": "..."}	200 / 404	Cargo atualizado
DELETE	/cargos/{id}	Excluir cargo	-	204 / 404	-

Erros comuns:

400 Bad Request para validações

409 Conflict para nomes duplicados

🔹 Empregados (/empregados)
Método	Endpoint	Descrição	Parâmetros / Corpo	Status da Resposta	Corpo da Resposta
POST	/empregados	Criar empregado	{"nome": "João", ...}	201 / 409	Empregado criado ou erro
GET	/empregados	Listar empregados (paginado)	?page=0&size=10&nome=...	200	Página de empregados
GET	/empregados/{id}	Buscar por ID	-	200 / 404	Detalhes do empregado
PUT	/empregados/{id}	Atualizar empregado	{"nome": "...", ...}	200 / 404	Empregado atualizado
DELETE	/empregados/{id}	Excluir empregado	-	204 / 404	-

Paginação e filtragem:

page (base 0), size, nome, email

❗ Tratamento de Erros
Código	Significado	Quando ocorre
400	Requisição inválida	Campos obrigatórios ausentes
404	Não encontrado	ID inexistente
405	Método não permitido	Método não suportado
409	Conflito de dados	Email ou cargo duplicado
500	Erro interno do servidor	Erros inesperados

🧪 Testes com Postman
Exemplos de testes:

POST /cargos – Criação de cargo

POST /empregados – Criação de empregado

GET /empregados?page=0&size=5 – Listagem paginada

Console H2 para inspecionar as tabelas:

SELECT * FROM ROLE;

SELECT * FROM EMPLOYEE;

🖼️ Capturas de Tela
As capturas de tela das requisições e da interface do banco de dados foram registradas para validação e documentação. Você pode encontrá-las na pasta:

makefile
Copy
Edit
\gestão de funcionários\screenshots
Conteúdo da pasta:

Requisições Postman (POST, GET, PUT, DELETE)

Console do H2 exibindo os dados nas tabelas ROLE e EMPLOYEE

Exemplos de mensagens de erro (validação e conflitos)

Essas imagens demonstram o funcionamento esperado da API em diferentes cenários.

📁 Estrutura do Projeto
css
Copy
Edit
backend/
├── src/main/java/com/example/employeemanagement/
│   ├── controller/
│   ├── service/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   ├── exception/
│   ├── config/
├── src/main/resources/
│   ├── application.properties
├── pom.xml
