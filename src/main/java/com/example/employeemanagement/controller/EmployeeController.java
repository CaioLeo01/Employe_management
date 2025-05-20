package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que esta classe é um controller REST
@RequestMapping("/empregados") // Mapeia todas as rotas deste controller para o path /empregados
@Api(tags = "Employee Management") // Tag para agrupar os endpoints no Swagger
public class EmployeeController {
    private final EmployeeService employeeService;

    // Injeção de dependência do EmployeeService via construtor
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Endpoint para listar funcionários com filtros e paginação
    @GetMapping
    @ApiOperation(
        value = "List employees with optional filtering and pagination", 
        notes = "Returns a paginated list of employees. Use 'name', 'email' and 'roleName' to filter, and 'page' and 'size' for pagination."
    )
    public Page<EmployeeDTO> getAllEmployees(
            @ApiParam(value = "Filter by employee name") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "Filter by employee email") @RequestParam(value = "email", required = false) String email,
            @ApiParam(value = "Filter by role name") @RequestParam(value = "roleName", required = false) String roleName,
            @ApiParam(value = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size) {
        return employeeService.getAllEmployees(name, email, roleName, page, size);
    }

    // Endpoint para buscar um funcionário por ID
    @GetMapping("/{id}")
    @ApiOperation(value = "Get employee by ID")
    public ResponseEntity<EmployeeDTO> getEmployeeById(
            @ApiParam(value = "Employee ID") @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Endpoint para criar um novo funcionário
    @PostMapping
    @ApiOperation(value = "Create a new employee")
    public ResponseEntity<EmployeeDTO> createEmployee(
            @ApiParam(value = "Employee data to create", required = true) 
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    // Endpoint para atualizar um funcionário existente
    @PutMapping("/{id}")
    @ApiOperation(value = "Update an employee")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @ApiParam(value = "Employee ID", required = true) @PathVariable Long id, 
            @ApiParam(value = "Employee data to update", required = true) 
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    // Endpoint para deletar um funcionário
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an employee")
    public ResponseEntity<Void> deleteEmployee(
            @ApiParam(value = "Employee ID", required = true) @PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}