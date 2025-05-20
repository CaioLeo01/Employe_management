// Define o pacote onde a interface está localizada
package com.example.employeemanagement.repository;

// Importa a entidade Employee usada nas operações do repositório
import com.example.employeemanagement.model.Employee;

// Importa classes para paginação de resultados
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Importa interfaces para criação de repositórios JPA e suporte a filtros dinâmicos
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// Importa suporte a consultas personalizadas com JPQL
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Marca esta interface como um componente Spring gerenciado (bean do tipo Repository)
import org.springframework.stereotype.Repository;

// Declaração da interface do repositório
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    // Interface estende JpaRepository para fornecer métodos CRUD padrão
    // Também estende JpaSpecificationExecutor para suportar consultas dinâmicas com Specification (filtros avançados)

    // Consulta customizada com filtros opcionais para name, email e role.name (cargo)
    @Query("SELECT e FROM Employee e WHERE " +
           "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:email IS NULL OR LOWER(e.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:roleName IS NULL OR LOWER(e.role.name) LIKE LOWER(CONCAT('%', :roleName, '%')))")
    Page<Employee> findByFilters(
        @Param("name") String name,           // Parâmetro opcional para filtrar pelo nome
        @Param("email") String email,         // Parâmetro opcional para filtrar pelo email
        @Param("roleName") String roleName,   // Parâmetro opcional para filtrar pelo nome da função/cargo
        Pageable pageable                     // Suporte à paginação de resultados
    );

    // Busca funcionários cujo nome contenha determinado texto (case-insensitive), com paginação
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Busca funcionários cujo email contenha determinado texto (case-insensitive), com paginação
    Page<Employee> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    // Busca funcionários cujo nome da função/cargo contenha determinado texto (case-insensitive), com paginação
    Page<Employee> findByRoleNameContainingIgnoreCase(String roleName, Pageable pageable);

    // Verifica se existe algum funcionário com o e-mail informado (retorna true ou false)
    boolean existsByEmail(String email);

    // Verifica se já existe um e-mail em uso por outro funcionário (útil para evitar duplicidade em atualizações)
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
           "FROM Employee e WHERE e.email = :email AND e.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
}
