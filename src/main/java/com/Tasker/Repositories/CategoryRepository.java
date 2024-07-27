package com.Tasker.Repositories;

import com.Tasker.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Category} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for {@link Category} entities.
 * </p>
 *
 * <p>
 * It also provides a method to find all categories by user ID.
 * </p>
 *
 * @author Yons Said
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Find all categories by user ID.
     *
     * @param userId the ID of the user
     * @return a list of categories belonging to the user with the specified ID
     */
    List<Category> findAllByUser_UserId(Long userId);
}
