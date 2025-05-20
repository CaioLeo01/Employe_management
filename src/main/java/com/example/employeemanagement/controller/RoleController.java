package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.RoleDTO;
import com.example.employeemanagement.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta classe é um controller REST
@RequestMapping("/cargos") // Define o caminho base para todos os endpoints deste controller
public class RoleController {
    private final RoleService roleService;

    // Injeção de dependência do RoleService via construtor
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Endpoint para listar todos os cargos/funções
    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Endpoint para buscar um cargo específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // Endpoint para criar um novo cargo
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.createRole(roleDTO));
    }

    // Endpoint para atualizar um cargo existente
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(
            @PathVariable Long id, 
            @Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.updateRole(id, roleDTO));
    }

    // Endpoint para deletar um cargo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}