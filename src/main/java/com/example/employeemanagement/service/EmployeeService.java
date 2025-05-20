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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getAdmissionDate() == null) {
            throw new IllegalArgumentException("Admission date is mandatory");
        }
        
        Role role = roleRepository.findById(employeeDTO.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + employeeDTO.getRole().getId()));
        
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setRole(role);
        
        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }

    public Page<EmployeeDTO> getAllEmployees(String name, String email, String roleName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeRepository.findByFilters(name, email, roleName, pageable);
        return employeePage.map(this::convertToDto);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return convertToDto(employee);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        
        if (employeeDTO.getAdmissionDate() == null) {
            employeeDTO.setAdmissionDate(employee.getAdmissionDate());
        }
        
        Role role = roleRepository.findById(employeeDTO.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + employeeDTO.getRole().getId()));
        
        BeanUtils.copyProperties(employeeDTO, employee, "id");
        employee.setRole(role);
        
        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(employee.getRole(), roleDTO);
        dto.setRole(roleDTO);
        
        return dto;
    }
}