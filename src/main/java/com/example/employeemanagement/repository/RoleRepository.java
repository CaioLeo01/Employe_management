package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Role entities with custom query methods.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Checks if a role with the given name exists.
     * @param name the role name to check
     * @return true if a role with this name exists, false otherwise
     */
    boolean existsByName(String name);
    
    /**
     * Checks if another role (excluding the specified ID) has the given name.
     * @param name the role name to check
     * @param id the ID of the role to exclude from the check
     * @return true if another role with this name exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Role r WHERE r.name = :name AND r.id <> :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);
}