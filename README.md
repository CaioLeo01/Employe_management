# 🏢 Employee Management API

API REST para gestão de empregados e cargos, desenvolvida em **Spring Boot** com operações CRUD, filtros, paginação e validações.

## 📋 Requisitos Implementados

### CRUD de Cargos
- `POST /cargos` - Criar cargo  
- `GET /cargos` - Listar todos  
- `GET /cargos/{id}` - Buscar por ID  
- `PUT /cargos/{id}` - Atualizar  
- `DELETE /cargos/{id}` - Remover  

### CRUD de Empregados
- `POST /empregados` - Criar empregado  
- `GET /empregados` - Listar com filtros  
- `GET /empregados/{id}` - Buscar por ID  
- `PUT /empregados/{id}` - Atualizar  
- `DELETE /empregados/{id}` - Remover  

### ✅ Funcionalidades Extras
- **Filtros** em `GET /empregados`:
  ```http
  GET /empregados?name=João&roleName=Desenvolvedor
Paginação:

http
GET /empregados?page=0&size=5
Validações:

Email único e formato válido

Nome obrigatório

Data de admissão não pode ser futura

🛠 Tecnologias
Java 21

Spring Boot 3.x

Spring Data JPA

H2 Database (em memória para desenvolvimento)

Swagger/OpenAPI (Documentação)

Lombok (Redução de boilerplate)

📦 Estrutura do Projeto
src/
├── main/
│   ├── java/
│   │   └── com/example/employeemanagement/
│   │       ├── controller/       # Endpoints REST
│   │       ├── dto/              # Objetos de transferência
│   │       ├── model/            # Entidades JPA
│   │       ├── repository/       # Interfaces JPA
│   │       ├── service/          # Lógica de negócio
│   │       └── exception/        # Tratamento de erros
│   └── resources/
│       ├── application.properties
│       └── data.sql              # Dados iniciais
🚀 Como Executar
Pré-requisitos
JDK 17+

Maven 3.8+

P

bash
mvn spring-boot:run
Endpoints Disponíveis
API: http://localhost:8080/empregados

Swagger UI: http://localhost:8080/swagger-ui.html

H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:employeedb)

📚 Documentação da API
Acesse o Swagger UI para:

Ver todos os endpoints

Testar requisições diretamente

Consultar modelos de dados

Swagger Preview

🧪 Testes Automatizados
bash
mvn test
Cobertura:

Testes de serviço

Validações de entrada

🐳 Docker (Opcional)
Para rodar em container:

bash
docker build -t employee-api .
docker run -p 8080:8080 employee-api
📝 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para detalhes.

✨ Dúvidas? Abra uma issue ou contate o desenvolvedor.


### 🔍 Detalhes Incluídos:
1. **Organização clara** dos requisitos atendidos  
2. **Instruções passo a passo** para execução  
3. **Links úteis** (Swagger, H2 Console)  
4. **Estrutura de pastas** documentada  
5. **Bônus**: Docker e testes mencionados