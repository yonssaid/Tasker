package com.Tasker.Services;

import com.Tasker.Dto.TaskDto;
import com.Tasker.Models.MyUser;
import com.Tasker.Models.Task;
import com.Tasker.Repositories.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing task-related operations.
 * <p>
 * This service handles CRUD operations for tasks and associates them with users.
 * </p>
 *
 * @author Yons Said
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    /**
     * Constructor for TaskService.
     *
     * @param taskRepository the task repository
     * @param userService the user service
     */
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    /**
     * Creates a new task for the authenticated user.
     *
     * @param taskDTO the data transfer object containing task details.
     * @param request the HttpServletRequest object.
     * @return the created task.
     */
    public Task createTask(TaskDto taskDTO, HttpServletRequest request) {
        ResponseEntity<MyUser> response = userService.getUserProfile(request);
        MyUser user = response.getBody();

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDeadline(taskDTO.getDeadline());
        task.setStatus("To-Do");
        task.setPriority(taskDTO.getPriority());
        task.setUser(user);

        return taskRepository.save(task);
    }

    /**
     * Retrieves all tasks associated with the authenticated user.
     *
     * @param request the HttpServletRequest object.
     * @return a list of all tasks for the authenticated user.
     */
    @SuppressWarnings("DuplicatedCode")
    public List<Task> findAll(HttpServletRequest request) {
        ResponseEntity<MyUser> response = userService.getUserProfile(request);
        if (response.getStatusCode() == HttpStatus.OK) {
            MyUser user = response.getBody();
            assert user != null;
            Long userId = user.getUserId();
            return taskRepository.findAllByUser_UserId(userId);
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task.
     * @return an Optional containing the task if found, or empty otherwise.
     */
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to be deleted.
     */
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Updates a task with new details.
     *
     * @param id the ID of the task to be updated.
     * @param taskDto the data transfer object containing updated task details.
     * @return an Optional containing the updated task if found, or empty otherwise.
     */
    public Optional<Task> updateTask(Long id, TaskDto taskDto) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setStatus(taskDto.getStatus());
            task.setPriority(taskDto.getPriority());
            task.setDeadline(taskDto.getDeadline());

            Task updatedTask = taskRepository.save(task);
            return Optional.of(updatedTask);
        }

        return Optional.empty();
    }
}
