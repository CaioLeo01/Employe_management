// Define o pacote onde esta classe está localizada
package com.example.employeemanagement.dto;

// Importa a anotação @NotBlank usada para validação de campos
import jakarta.validation.constraints.NotBlank;

// Importa anotações do Lombok para gerar automaticamente métodos comuns
import lombok.*;

// Gera automaticamente métodos como getters, setters, toString, equals e hashCode
@Data

// Gera automaticamente um construtor sem argumentos (padrão)
@NoArgsConstructor

// Gera automaticamente um construtor com todos os argumentos (todos os campos da classe)
@AllArgsConstructor
public class RoleDTO {

    // Campo que representa o ID da função/papel. Pode ser usado como identificador único.
    private Long id;

    // Campo obrigatório que representa o nome da função/papel.
    // A anotação @NotBlank garante que este campo não pode ser nulo nem vazio.
    @NotBlank(message = "Name is mandatory")
    private String name;

    // Campo opcional que descreve a função/papel.
    private String description;
}
