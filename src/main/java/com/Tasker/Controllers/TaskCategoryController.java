package com.Tasker.Controllers;

import com.Tasker.Dto.TaskCategoryDto;
import com.Tasker.Models.Category;
import com.Tasker.Models.TaskCategory;
import com.Tasker.Services.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing task categories.
 *
 * @author Yons Said
 */
@RestController
@RequestMapping("/api/taskcategories/")
public class TaskCategoryController {

    private final TaskCategoryService taskCategoryService;

    /**
     * Constructs a TaskCategoryController with the specified task category service.
     *
     * @param taskCategoryService the service for managing task-category associations.
     */
    @Autowired
    public TaskCategoryController(TaskCategoryService taskCategoryService) {
        this.taskCategoryService = taskCategoryService;
    }


    /**
     * Retrieves all task categories.
     *
     * @return a list of all task categories.
     */
    @GetMapping
    public List<TaskCategory> getAllTaskCategories() {
        return taskCategoryService.getAllTaskCategories();
    }

    /**
     * Creates a new task category.
     *
     * @param taskCategoryDTO the data transfer object containing task and category IDs.
     * @return a ResponseEntity with status 201 Created or 500 Internal Server Error if creation fails.
     */
    @PostMapping("create")
    public ResponseEntity<Void> createTaskCategory(@RequestBody TaskCategoryDto taskCategoryDTO) {
        try {
            taskCategoryService.create(taskCategoryDTO.getTaskId(), taskCategoryDTO.getCategoryId());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates an existing task category.
     *
     * @param taskCategoryDTO the data transfer object containing task and category IDs.
     * @return a ResponseEntity with status 200 OK or 500 Internal Server Error if update fails.
     */
    @PutMapping("update")
    public ResponseEntity<Void> updateTaskCategory(@RequestBody TaskCategoryDto taskCategoryDTO) {
        try {
            taskCategoryService.updateTaskCategory(taskCategoryDTO.getTaskId(), taskCategoryDTO.getCategoryId());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves the category associated with a given task ID.
     *
     * @param taskId the ID of the task.
     * @return a ResponseEntity containing the category or 204 No Content if no category is found.
     */
    @GetMapping("{taskId}")
    public ResponseEntity<Category> getCategoryByTaskId(@PathVariable("taskId") Long taskId) {
        Optional<TaskCategory> taskCategory = taskCategoryService.getCategoryByTaskId(taskId);

        if (taskCategory.isPresent()) {
            Category category = taskCategory.get().getCategory();
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Deletes a task category by task ID.
     *
     * @param taskId the ID of the task.
     * @return a ResponseEntity with status 204 No Content or 500 Internal Server Error if deletion fails.
     */
    @DeleteMapping("{taskId}")
    public ResponseEntity<Void> deleteTaskCategory(@PathVariable Long taskId) {
        try {
            taskCategoryService.deleteTaskCategory(taskId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
