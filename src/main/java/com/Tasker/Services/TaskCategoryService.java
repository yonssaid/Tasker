package com.Tasker.Services;

import com.Tasker.Models.Category;
import com.Tasker.Models.Task;
import com.Tasker.Models.TaskCategory;
import com.Tasker.Repositories.TaskCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing TaskCategory-related operations.
 * <p>
 * This service handles CRUD operations for task categories and their associations with tasks and categories.
 * </p>
 *
 * @author Yons Said
 */
@Service
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;
    private final CategoryService categoryService;
    private final TaskService taskService;

    /**
     * Constructor for TaskCategoryService.
     *
     * @param taskCategoryRepository the repository to perform CRUD operations on task categories.
     * @param categoryService the category service to retrieve category information.
     * @param taskService the task service to retrieve task information.
     */
    @Autowired
    public TaskCategoryService(TaskCategoryRepository taskCategoryRepository, CategoryService categoryService, TaskService taskService) {
        this.taskCategoryRepository = taskCategoryRepository;
        this.categoryService = categoryService;
        this.taskService = taskService;
    }

    /**
     * Retrieves all task categories.
     *
     * @return a list of all task categories.
     */
    public List<TaskCategory> getAllTaskCategories() {
        return taskCategoryRepository.findAll();
    }

    /**
     * Retrieves a task category by its ID.
     *
     * @param id the ID of the task category.
     * @return an Optional containing the task category if found, or empty otherwise.
     */
    public Optional<TaskCategory> getTaskCategoryById(Long id) {
        return taskCategoryRepository.findById(id);
    }

    /**
     * Creates a new task category association between a task and a category.
     *
     * @param taskId the ID of the task.
     * @param categoryId the ID of the category.
     */
    public void create(Long taskId, Long categoryId) {
        TaskCategory taskCategory = new TaskCategory();
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        Optional<Task> task = taskService.findById(taskId);

        taskCategory.setTask(task.orElse(null));
        taskCategory.setCategory(category.orElse(null));

        taskCategoryRepository.save(taskCategory);
    }

    /**
     * Retrieves a task category by the task ID.
     *
     * @param taskId the ID of the task.
     * @return an Optional containing the task category if found, or empty otherwise.
     */
    public Optional<TaskCategory> getCategoryByTaskId(Long taskId) {
        return taskCategoryRepository.findByTask_Id(taskId).stream().findFirst();
    }

    /**
     * Deletes a task category by the task ID.
     *
     * @param taskId the ID of the task.
     */
    @Transactional
    public void deleteTaskCategory(Long taskId) {
        taskCategoryRepository.deleteByTask_Id(taskId);
    }

    /**
     * Updates the category of a task category by the task ID.
     *
     * @param taskId the ID of the task.
     * @param newCategoryId the ID of the new category.
     */
    public void updateTaskCategory(Long taskId, Long newCategoryId) {
        List<TaskCategory> taskCategories = taskCategoryRepository.findByTask_Id(taskId);
        Category newCategory = categoryService.getCategoryById(newCategoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (taskCategories.isEmpty()) {
            throw new RuntimeException("No TaskCategory entries found for taskId: " + taskId);
        }

        for (TaskCategory taskCategory : taskCategories) {
            taskCategory.setCategory(newCategory);
            taskCategoryRepository.save(taskCategory);
        }
    }
}
