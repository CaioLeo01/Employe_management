package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.RoleDTO;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Role role = roleRepository.findById(employeeDTO.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + employeeDTO.getRole().getId()));
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setRole(role);
        employee = employeeRepository.save(employee);
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setRole(new RoleDTO(role.getId(), role.getName(), role.getDescription()));
        return employeeDTO;
    }

    public Page<EmployeeDTO> getAllEmployees(String roleName, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Employee> employees = employeeRepository.findByFilters(roleName, name);
        List<EmployeeDTO> dtos = employees.stream().map(employee -> {
            EmployeeDTO dto = new EmployeeDTO();
            BeanUtils.copyProperties(employee, dto);
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(employee.getRole(), roleDTO);
            dto.setRole(roleDTO);
            return dto;
        }).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        return new PageImpl<>(dtos.subList(start, end), pageable, dtos.size());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(employee.getRole(), roleDTO);
        dto.setRole(roleDTO);
        return dto;
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        Role role = roleRepository.findById(employeeDTO.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + employeeDTO.getRole().getId()));
        BeanUtils.copyProperties(employeeDTO, employee, "id");
        employee.setRole(role);
        employee = employeeRepository.save(employee);
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setRole(new RoleDTO(role.getId(), role.getName(), role.getDescription()));
        return employeeDTO;
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        employeeRepository.deleteById(id);
    }
}