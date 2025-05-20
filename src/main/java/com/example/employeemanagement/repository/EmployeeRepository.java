package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT e FROM Employee e WHERE " +
           "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:email IS NULL OR LOWER(e.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:roleName IS NULL OR LOWER(e.role.name) LIKE LOWER(CONCAT('%', :roleName, '%')))")
    Page<Employee> findByFilters(
        @Param("name") String name,
        @Param("email") String email,
        @Param("roleName") String roleName,
        Pageable pageable);

    // Métodos adicionais para buscas específicas
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Employee> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Employee> findByRoleNameContainingIgnoreCase(String roleName, Pageable pageable);
    
    // Novo método para verificar existência de email
    boolean existsByEmail(String email);
    
    // Método para verificar email excluindo um ID específico (útil para update)
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.email = :email AND e.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
}