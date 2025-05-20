package com.example.employeemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    
    @PastOrPresent(message = "Admission date cannot be in the future")
    private LocalDate admissionDate;

    @NotNull(message = "Role is mandatory")
    private RoleDTO role;
}
