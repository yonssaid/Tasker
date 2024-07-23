package com.Tasker.Services;

import com.Tasker.Models.Category;
import com.Tasker.Models.Task;
import com.Tasker.Models.TaskCategory;
import com.Tasker.Repositories.TaskCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskCategoryService {
    @Autowired
    private TaskCategoryRepository taskCategoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TaskService taskService;

    public List<TaskCategory> getAllTaskCategories() {
        return taskCategoryRepository.findAll();
    }

    public Optional<TaskCategory> getTaskCategoryById(Long id) {
        return taskCategoryRepository.findById(id);
    }

    public void create(Long taskId, Long categoryId) {
        TaskCategory taskCategory = new TaskCategory();
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        Optional<Task> task = taskService.findById(taskId);

        taskCategory.setTask(task.orElse(null));
        taskCategory.setCategory(category.orElse(null));

        taskCategoryRepository.save(taskCategory);
    }

    public void deleteTaskCategory(Long id) {
        taskCategoryRepository.deleteById(id);
    }
}