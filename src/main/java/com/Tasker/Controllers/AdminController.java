package com.Tasker.Controllers;

import com.Tasker.Models.MyUser;
import com.Tasker.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for admin-related operations.
 * <p>
 * This controller handles requests related to admin operations such as managing users.
 * </p>
 *
 * @author Yons Said
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     * Constructs an AdminController with the specified AdminService.
     *
     * @param adminService the service to handle admin operations
     */
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Endpoint for accessing admin-specific operations.
     *
     * @return a response indicating the access status of the admin endpoint
     */
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access forbidden");
        }

        return ResponseEntity.ok("Admin endpoint accessed.");
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<MyUser>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    /**
     * Creates a new user.
     *
     * @param myUser the user to create
     * @return the created user
     */
    @PostMapping("/users")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser myUser) {
        return ResponseEntity.ok(adminService.createUser(myUser));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a response indicating the result of the delete operation
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a user by their ID.
     *
     * @param id the ID of the user to update
     * @param myUser the updated user information
     * @return the updated user
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Long id, @RequestBody MyUser myUser) {
        return ResponseEntity.ok(adminService.updateUser(id, myUser));
    }
}
