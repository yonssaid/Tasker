package com.Tasker.Repositories;

import com.Tasker.Models.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link TaskCategory} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for {@link TaskCategory} entities.
 * </p>
 *
 * <p>
 * It also provides methods to find task categories by task ID and delete task categories by task ID.
 * </p>
 *
 * @author Yons Said
 */
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
    /**
     * Find task categories by task ID.
     *
     * @param taskId the ID of the task
     * @return a list of task categories associated with the specified task ID
     */
    List<TaskCategory> findByTask_Id(Long taskId);

    /**
     * Delete task categories by task ID.
     *
     * @param taskId the ID of the task
     */
    void deleteByTask_Id(Long taskId);
}
