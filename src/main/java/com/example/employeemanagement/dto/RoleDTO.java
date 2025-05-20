package com.example.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO{
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;
}

