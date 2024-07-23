package com.Tasker.Controllers;

import com.Tasker.Dto.TaskCategoryDto;
import com.Tasker.Models.TaskCategory;
import com.Tasker.Services.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/taskcategories/")
public class TaskCategoryController{
    @Autowired
    private TaskCategoryService taskCategoryService;

    @GetMapping
    public List<TaskCategory> getAllTaskCategories() {
        return taskCategoryService.getAllTaskCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskCategory> getTaskCategoryById(@PathVariable Long id) {
        Optional<TaskCategory> taskCategory = taskCategoryService.getTaskCategoryById(id);
        return taskCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("create")
    public ResponseEntity<Void> createTaskCategory(@RequestBody TaskCategoryDto taskCategoryDTO) {
        try {
            taskCategoryService.create(taskCategoryDTO.getTaskId(), taskCategoryDTO.getCategoryId());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTaskCategory(@PathVariable Long id) {
        taskCategoryService.deleteTaskCategory(id);
    }
}
