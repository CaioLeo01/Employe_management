Employee Management API
A RESTful API for managing employees and roles, built with Spring Boot.
Features

CRUD operations for Roles and Employees
Filtering employees by role and name
Pagination support
Input validation
Swagger/OpenAPI documentation
Docker support
Automated tests

Prerequisites

Java 21
Maven
Docker (optional)

Setup

Clone the repository:git clone <repository-url>


Navigate to the project directory:cd employee-management


Build the project:mvn clean install


Run the application:mvn spring-boot:run



Running with Docker

Build the Docker image:docker build -t employee-management .


Run the container:docker run -p 8080:8080 employee-management



API Documentation
Access the Swagger UI at: http://localhost:8080/swagger-ui.html
Testing
Run tests with:
mvn test

Endpoints

Roles: /cargos
POST: Create a role
GET: List all roles
GET /{id}: Get role by ID
PUT /{id}: Update role
DELETE /{id}: Delete role


Employees: /empregados
POST: Create an employee
GET: List all employees (supports ?cargo=, ?nome=, ?page=, ?size=)
GET /{id}: Get employee by ID
PUT /{id}: Update employee
DELETE /{id}: Delete employee



Example Requests
Create Role
curl -X POST http://localhost:8080/cargos -H "Content-Type: application/json" -d '{"name":"Developer","description":"Software Developer"}'

Create Employee
curl -X POST http://localhost:8080/empregados -H "Content-Type: application/json" -d '{"name":"John Doe","email":"john.doe@example.com","admissionDate":"2023-01-01","role":{"id":1}}'

List Employees with Filters
curl "http://localhost:8080/empregados?cargo=Developer&nome=John&page=0&size=10"

Postman Screenshots
(Include screenshots of Postman requests in the repository under /screenshots.)
Deployment
The application can be deployed to a cloud provider like Heroku or AWS. Update this section with the deployment URL if applicable.
