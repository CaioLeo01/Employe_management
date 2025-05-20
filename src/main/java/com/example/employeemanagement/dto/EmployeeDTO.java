// Define o pacote onde a classe está localizada
package com.example.employeemanagement.dto;

// Importa as anotações de validação do Jakarta
import jakarta.validation.constraints.*;
// Importa as anotações do Lombok para geração automática de código
import lombok.*;
// Importa a classe para trabalhar com datas
import java.time.LocalDate;

/**
 * EmployeeDTO (Data Transfer Object) representa os dados de um funcionário
 * para transferência entre camadas da aplicação, especialmente para a API REST.
 * 
 * Utiliza anotações para validação de dados e Lombok para redução de código boilerplate.
 */

// @Data - Anotação do Lombok que gera automaticamente:
// - Getters e Setters para todos os campos
// - toString(), equals() e hashCode()
@Data

// @NoArgsConstructor - Gera um construtor sem argumentos
@NoArgsConstructor

// @AllArgsConstructor - Gera um construtor com todos os argumentos (um para cada campo)
@AllArgsConstructor

public class EmployeeDTO {
    // ID único do funcionário
    private Long id;

    /**
     * Nome completo do funcionário
     * 
     * Validações:
     * @NotBlank - Não pode ser nulo ou string vazia (mensagem customizada)
     * @Size - Deve ter entre 2 e 100 caracteres (mensagem customizada)
     */
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    /**
     * Endereço de e-mail do funcionário
     * 
     * Validações:
     * @NotBlank - Não pode ser nulo ou string vazia
     * @Email - Deve estar em formato de e-mail válido
     */
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Data de admissão do funcionário
     * 
     * Validação:
     * @PastOrPresent - A data não pode ser futura (deve ser hoje ou anterior)
     */
    @PastOrPresent(message = "Admission date cannot be in the future")
    private LocalDate admissionDate;

    /**
     * Cargo/função do funcionário (representado por um RoleDTO)
     * 
     * Validação:
     * @NotNull - O objeto RoleDTO não pode ser nulo
     */
    @NotNull(message = "Role is mandatory")
    private RoleDTO role;
}