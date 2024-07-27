package com.Tasker.Repositories;

import com.Tasker.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Role} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for {@link Role} entities.
 * </p>
 *
 * <p>
 * It also provides a method to find a role by its name.
 * </p>
 *
 * @author Yons Said
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Find a role by its name.
     *
     * @param name the name of the role
     * @return an optional containing the role with the specified name, or an empty optional if no role was found
     */
    Optional<Role> findByName(String name);
}
