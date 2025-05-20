// Define o pacote onde a classe Role está localizada
package com.example.employeemanagement.model;

// Importa as anotações JPA usadas para mapear a entidade ao banco de dados
import jakarta.persistence.*;

// Importa anotações do Lombok para geração automática de código boilerplate
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Marca esta classe como uma entidade JPA, ou seja, ela será mapeada para uma tabela no banco de dados
@Entity

// Especifica o nome da tabela no banco de dados como "roles"
@Table(name = "roles")

// Lombok: Gera automaticamente getters, setters, toString, equals, hashCode, etc.
@Data

// Lombok: Gera automaticamente um construtor sem argumentos
@NoArgsConstructor

// Lombok: Gera automaticamente um construtor com todos os campos
@AllArgsConstructor
public class Role {

    // Define este campo como a chave primária da tabela
    @Id

    // Define que o valor do ID será gerado automaticamente pelo banco (auto-incremento)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define que o campo 'name' não pode ser nulo e deve ser único na tabela
    @Column(nullable = false, unique = true)
    private String name;

    // Campo opcional que armazena a descrição da função/cargo
    private String description;
}
