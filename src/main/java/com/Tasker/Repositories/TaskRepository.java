package com.Tasker.Repositories;

import com.Tasker.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Task} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for {@link Task} entities.
 * </p>
 *
 * <p>
 * It also provides methods to find tasks by user ID and find a task by its ID.
 * </p>
 *
 * @author Yons Said
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * Find all tasks by user ID.
     *
     * @param userId the ID of the user
     * @return a list of tasks associated with the specified user ID
     */
    List<Task> findAllByUser_UserId(Long userId);

    /**
     * Find a task by its ID.
     *
     * @param id the ID of the task
     * @return an {@link Optional} containing the found task, or empty if no task is found
     */
    Optional<Task> findById(Long id);
}
