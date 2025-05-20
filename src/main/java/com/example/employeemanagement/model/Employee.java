// Define o pacote onde esta classe está localizada
package com.example.employeemanagement.model;

// Importa as anotações JPA (para mapeamento da entidade no banco de dados)
import jakarta.persistence.*;

// Importa as anotações do Lombok para gerar automaticamente métodos e construtores
import lombok.*;

// Importa o tipo LocalDate (usado para armazenar a data de admissão)
import java.time.LocalDate;

// Indica que esta classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados
@Entity

// Especifica o nome da tabela no banco de dados como "employees"
@Table(name = "employees")

// Lombok: Gera automaticamente métodos como getters, setters, toString, equals e hashCode
@Data

// Lombok: Gera automaticamente um construtor sem argumentos
@NoArgsConstructor

// Lombok: Gera automaticamente um construtor com todos os campos
@AllArgsConstructor
public class Employee {

    // Indica que este campo é a chave primária da tabela
    @Id

    // Define que o valor da chave primária será gerado automaticamente (auto-incremento)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapeia a coluna "name" e define que ela não pode ser nula
    @Column(nullable = false)
    private String name;

    // Mapeia a coluna "email", que deve ser única e não pode ser nula
    @Column(nullable = false, unique = true)
    private String email;

    // Mapeia a coluna "admission_date" com nome personalizado e define como obrigatória
    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    // Define um relacionamento muitos-para-um com a entidade Role (muitos funcionários podem ter a mesma função)
    @ManyToOne

    // Especifica o nome da chave estrangeira no banco de dados como "role_id"
    // Também define que essa relação é obrigatória (não pode ser nula)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
