package com.Tasker.Controllers;

import com.Tasker.Models.Category;
import com.Tasker.Services.CategoryService;
import com.Tasker.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing categories.
 *
 * @author Yons Said
 */
@RestController
@RequestMapping("/api/categories/")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    /**
     * Constructs a CategoryController with the specified services.
     *
     * @param categoryService the service for managing category-related operations.
     * @param userService the service for handling user-related operations.
     */
    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    /**
     * Retrieves all categories.
     *
     * @param request the HttpServletRequest object.
     * @return a list of all categories.
     */
    @GetMapping("getAll")
    public List<Category> getAllCategories(HttpServletRequest request) {
        return categoryService.getAllCategories(request);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category.
     * @return a ResponseEntity containing the category if found, or a 404 Not Found status if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new category.
     *
     * @param name the name of the category to create.
     * @param request the HttpServletRequest object.
     * @return a ResponseEntity containing the created category and a 201 Created status, or a 400 Bad Request status if creation fails.
     */
    @PostMapping("create")
    public ResponseEntity<Category> createCategory(@RequestBody String name, HttpServletRequest request) {
        try {
            Category category = categoryService.createCategory(name, request);
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
