package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Employee entities with custom query methods.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    /**
     * Finds employees with optional filters and pagination.
     * @param name filter by employee name (partial match, case-insensitive)
     * @param email filter by employee email (partial match, case-insensitive)
     * @param roleName filter by role name (partial match, case-insensitive)
     * @param pageable pagination information
     * @return page of filtered employees
     */
    @Query("SELECT e FROM Employee e WHERE " +
           "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:email IS NULL OR LOWER(e.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:roleName IS NULL OR LOWER(e.role.name) LIKE LOWER(CONCAT('%', :roleName, '%')))")
    Page<Employee> findByFilters(
        @Param("name") String name,
        @Param("email") String email,
        @Param("roleName") String roleName,
        Pageable pageable);

    /**
     * Checks if an employee with the given email exists.
     * @param email the email to check
     * @return true if an employee with this email exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Checks if another employee (excluding the specified ID) has the given email.
     * @param email the email to check
     * @param id the ID of the employee to exclude from the check
     * @return true if another employee with this email exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.email = :email AND e.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
}