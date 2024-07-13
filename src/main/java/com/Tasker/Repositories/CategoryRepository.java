package com.Tasker.Repositories;

import com.Tasker.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c JOIN c.tasks t WHERE t.user.username = :username")
    List<Category> findCategoriesByUser(@Param("username") String username);
}
