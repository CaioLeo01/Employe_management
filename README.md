# 🏢 Employee Management API

API REST para gestão de empregados e cargos com Spring Boot 3.x.

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java 21** (Verifique com `java -version`)
- **Maven 3.4.5** (Verifique com `mvn -v`)
- **Postman** ou similar (para testar endpoints)

---

### Passo a Passo

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/employee-management.git
   cd employee-management
Configure o banco de dados (H2 embutido):

O projeto já inclui configuração automática

Acesse o console do H2 em:
http://localhost:8080/h2-console
Credenciais (em application.properties):

JDBC URL: jdbc:h2:mem:employeedb
User: sa
Password: (vazio)
Execute o projeto:

bash
mvn spring-boot:run
Ou pelo seu IDE favorito (Eclipse/IntelliJ).

Acesse a documentação:

Swagger UI:
http://localhost:8080/swagger-ui.html
(Se não funcionar, tente /swagger-ui/index.html)

Endpoint JSON:
http://localhost:8080/v3/api-docs

Teste os endpoints:

bash
# Listar empregados
curl http://localhost:8080/empregados

# Criar novo empregado
curl -X POST http://localhost:8080/empregados \
-H "Content-Type: application/json" \
-d '{
  "name": "João Silva",
  "email": "joao@empresa.com",
  "admissionDate": "2023-08-20",
  "role": { "id": 1 }
}'
🔧 Solução de Problemas Comuns
Swagger Não Abre?
Verifique as dependências no pom.xml:

xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
URLs alternativas para teste:

http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs
Se usar Spring Security, libere os endpoints no SecurityConfig:

java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        .anyRequest().authenticated()
    );
    return http.build();
}
Erros no Banco de Dados?
Verifique se o H2 está ativo no application.properties:

properties
spring.datasource.url=jdbc:h2:mem:employeedb
spring.h2.console.enabled=true
📦 Estrutura do Projeto
src/
├── main/
│   ├── java/
│   │   └── com/example/employeemanagement/
│   │       ├── config/       # Configurações (OpenAPI, Security)
│   │       ├── controller/   # Endpoints REST
│   │       ├── dto/          # Objetos de transferência
│   │       ├── model/        # Entidades JPA
│   │       ├── repository/   # Interfaces do banco
│   │       └── service/      # Regras de negócio
│   └── resources/
│       ├── application.properties
│       └── data.sql          # Dados iniciais
🐳 Executando com Docker
Construa a imagem:

bash
docker build -t employee-api .
Inicie o container:

bash
docker run -p 8080:8080 employee-api
✨ Dúvidas? Consulte a documentação completa em Swagger UI ou abra uma issue.


### Principais Melhorias:
1. **Passos detalhados** desde o clone até execução
2. **Solução de problemas** com Swagger e H2
3. **Comandos curl** prontos para testar a API
4. **Docker** incluído como opção
5. **Estrutura de pastas** documentada