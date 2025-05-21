# API de Gestão de Funcionários

API REST para gerenciamento de empregados e cargos, desenvolvida com Spring Boot.

## 📋 Requisitos Atendidos

✅ CRUD completo para Cargos  
✅ CRUD completo para Empregados  
✅ Validações de campos obrigatórios  
✅ Tratamento de exceções customizadas  
✅ Documentação com Swagger/OpenAPI  
✅ Dockerização do projeto  
✅ Testes automatizados  
✅ Filtros e paginação  

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven
- Docker (opcional)

### Localmente
```bash
mvn spring-boot:run
```

### Com Docker
```bash
docker build -t gestao-funcionarios .
docker run -p 8080:8080 gestao-funcionarios
```

## 🔍 Endpoints

### Cargos
- `POST /api/cargos` - Cria um novo cargo
- `GET /api/cargos` - Lista todos os cargos
- `GET /api/cargos/{id}` - Busca cargo por ID
- `PUT /api/cargos/{id}` - Atualiza um cargo
- `DELETE /api/cargos/{id}` - Remove um cargo

### Empregados
- `POST /api/empregados` - Cria um novo empregado
- `GET /api/empregados` - Lista todos os empregados (com filtros)
- `GET /api/empregados/{id}` - Busca empregado por ID
- `PUT /api/empregados/{id}` - Atualiza um empregado
- `DELETE /api/empregados/{id}` - Remove um empregado

## 📚 Documentação da API

#Front-end: \gestão de funcionários\frontend\Documentação do Frontend.markdown
#Back-end: \gestão de funcionários\Documentação do Backend.markdown

## 🧪 Testes
```bash
mvn test
```

## 🐳 Docker
O arquivo Dockerfile está configurado para criar uma imagem otimizada:
```bash
docker-compose up --build
```

## 📦 Estrutura do Projeto
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── exemplo/
│   │           ├── controller/
│   │           ├── dto/
│   │           ├── exception/
│   │           ├── model/
│   │           ├── repository/
│   │           └── service/
│   └── resources/
├── test/
└── Dockerfile
```
