// Define o pacote onde esta interface está localizada
package com.example.employeemanagement.repository;

// Importa a entidade Role, que será manipulada por este repositório
import com.example.employeemanagement.model.Role;

// Importa a interface JpaRepository, que fornece operações CRUD prontas
import org.springframework.data.jpa.repository.JpaRepository;

// Declara a interface do repositório da entidade Role
// Estende JpaRepository para herdar métodos de acesso a dados
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Como JpaRepository já fornece todos os métodos básicos (CRUD),
    // não é necessário declarar nada aqui por enquanto.

    // Exemplos de métodos herdados:
    // - findAll()
    // - findById(Long id)
    // - save(Role role)
    // - deleteById(Long id)
    // - existsById(Long id)
}
