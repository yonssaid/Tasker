package com.Tasker.Services;

import com.Tasker.Models.Category;
import com.Tasker.Models.MyUser;
import com.Tasker.Repositories.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing category-related operations.
 * <p>
 * This service handles CRUD operations for categories and associates them with users.
 * </p>
 *
 * @author Yons Said
 */
@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;

    private final UserService userService;

    /**
     * Constructor for CategoryService.
     *
     * @param categoryRepository the repository to perform CRUD operations on categories.
     * @param userService the user service to retrieve user information.
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    /**
     * Retrieves all categories associated with the authenticated user.
     *
     * @param request the HttpServletRequest object.
     * @return a list of all categories for the authenticated user.
     */
    @SuppressWarnings("DuplicatedCode")
    public List<Category> getAllCategories(HttpServletRequest request) {
        ResponseEntity<MyUser> response = userService.getUserProfile(request);
        if (response.getStatusCode() == HttpStatus.OK) {
            MyUser user = response.getBody();
            assert user != null;
            Long userId = user.getUserId();
            return categoryRepository.findAllByUser_UserId(userId);
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category.
     * @return an Optional containing the category if found, or empty otherwise.
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Creates a new category for the authenticated user.
     *
     * @param name the name of the category.
     * @param request the HttpServletRequest object.
     * @return the created category.
     */
    public Category createCategory(String name, HttpServletRequest request) {
        ResponseEntity<MyUser> response = userService.getUserProfile(request);
        MyUser user = response.getBody();

        Category category = new Category();
        category.setName(name);
        category.setUser(user);
        return categoryRepository.save(category);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to be deleted.
     */
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
