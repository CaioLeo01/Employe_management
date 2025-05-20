package com.example.employeemanagement;

import com.example.employeemanagement.dto.RoleDTO;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.repository.RoleRepository;
import com.example.employeemanagement.service.RoleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;
    private RoleDTO roleDTO;

    @BeforeEach
    void setUp() {
        role = new Role(1L, "Developer", "Software Developer");
        roleDTO = new RoleDTO(1L, "Developer", "Software Developer");
    }

    @Test
    void testCreateRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        RoleDTO result = roleService.createRole(roleDTO);
        assertEquals(roleDTO, result);
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void testGetAllRoles() {
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role));
        assertEquals(1, roleService.getAllRoles().size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetRoleById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        RoleDTO result = roleService.getRoleById(1L);
        assertEquals(roleDTO, result);
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRoleByIdNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roleService.getRoleById(1L));
    }
}