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

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    private final UserService userService;

    public CategoryService(UserService userService) {
        this.userService = userService;
    }

    public List<Category> getAllCategories(HttpServletRequest request) {

        ResponseEntity<MyUser> response = userService.getUserProfile(request);
        if (response.getStatusCode() == HttpStatus.OK) {
            MyUser user = response.getBody();
            Long userId = user.getUserId();


            return categoryRepository.findAllByUser_UserId(userId);
        }
        return Collections.emptyList();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(String name, HttpServletRequest request) {
        ResponseEntity<MyUser> response = userService.getUserProfile(request);
        MyUser user = response.getBody();

        Category category = new Category();
        category.setName(name);
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
