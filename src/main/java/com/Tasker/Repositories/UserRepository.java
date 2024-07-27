package com.Tasker.Repositories;

import com.Tasker.Models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link MyUser} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for {@link MyUser} entities.
 * </p>
 *
 * <p>
 * It also provides methods to find a user by username and check if a username exists.
 * </p>
 *
 * @author Yons Said
 */
public interface UserRepository extends JpaRepository<MyUser, Long> {
    /**
     * Find a user by their username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the found user, or empty if no user is found
     */
    Optional<MyUser> findByUsername(String username);

    /**
     * Check if a user exists by their username.
     *
     * @param username the username to check
     * @return {@code true} if a user with the specified username exists, {@code false} otherwise
     */
    Boolean existsByUsername(String username);
}
