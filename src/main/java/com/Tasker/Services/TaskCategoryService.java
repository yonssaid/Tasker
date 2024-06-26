package com.Tasker.Services;

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

    public List<TaskCategory> getAllTaskCategories() {
        return taskCategoryRepository.findAll();
    }

    public Optional<TaskCategory> getTaskCategoryById(Long id) {
        return taskCategoryRepository.findById(id);
    }

    public TaskCategory saveTaskCategory(TaskCategory taskCategory) {
        return taskCategoryRepository.save(taskCategory);
    }

    public void deleteTaskCategory(Long id) {
        taskCategoryRepository.deleteById(id);
    }
}