package com.Tasker.Repositories;

import com.Tasker.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUser_UserId(Long userId);
}
