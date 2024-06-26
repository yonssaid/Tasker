package com.Tasker.Repositories;

import com.Tasker.Models.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
}
