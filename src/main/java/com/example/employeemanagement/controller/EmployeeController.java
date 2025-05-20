package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empregados")
@Api(tags = "Employee Management")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ApiOperation(value = "List employees with optional filtering and pagination", 
                 notes = "Returns a paginated list of employees. Use 'name', 'email' and 'roleName' to filter, and 'page' and 'size' for pagination.")
    public Page<EmployeeDTO> getAllEmployees(
            @ApiParam(value = "Filter by employee name") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "Filter by employee email") @RequestParam(value = "email", required = false) String email,
            @ApiParam(value = "Filter by role name") @RequestParam(value = "roleName", required = false) String roleName,
            @ApiParam(value = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size) {
        return employeeService.getAllEmployees(name, email, roleName, page, size);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get employee by ID")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@ApiParam(value = "Employee ID") @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a new employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an employee")
    public ResponseEntity<EmployeeDTO> updateEmployee(@ApiParam(value = "Employee ID") @PathVariable Long id, 
                                                     @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an employee")
    public ResponseEntity<Void> deleteEmployee(@ApiParam(value = "Employee ID") @PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}