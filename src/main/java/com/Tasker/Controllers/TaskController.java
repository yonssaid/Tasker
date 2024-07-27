package com.Tasker.Controllers;

import com.Tasker.Dto.TaskDto;
import com.Tasker.Models.Task;
import com.Tasker.Services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing tasks.
 *
 * @author Yons Said
 */
@RestController
@RequestMapping("/api/tasks/")
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructs a TaskController with the specified task service.
     *
     * @param taskService the service for managing tasks.
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * Updates an existing task.
     *
     * @param id      the ID of the task to update.
     * @param taskDto the data transfer object containing updated task details.
     * @return a ResponseEntity containing the updated task, or 404 Not Found if the task does not exist.
     */
    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Optional<Task> updatedTask = taskService.updateTask(id, taskDto);
        return updatedTask.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new task.
     *
     * @param taskDto the data transfer object containing task details.
     * @param request the HttpServletRequest object.
     * @return the created task.
     */
    @PostMapping("create")
    public Task createTask(@RequestBody TaskDto taskDto, HttpServletRequest request) {
        return taskService.createTask(taskDto, request);
    }

    /**
     * Retrieves all tasks.
     *
     * @param request the HttpServletRequest object.
     * @return a list of all tasks.
     */
    @GetMapping("getAll")
    public List<Task> getAllTasks(HttpServletRequest request) {
        return taskService.findAll(request);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task.
     * @return a ResponseEntity containing the task, or 404 Not Found if the task does not exist.
     */
    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete.
     */
    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
