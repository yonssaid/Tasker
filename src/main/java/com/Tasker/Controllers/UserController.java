package com.Tasker.Controllers;

import com.Tasker.Models.MyUser;
import com.Tasker.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 *
 * @author Yons Said
 */
@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a UserController with the specified user service.
     *
     * @param userService the service for managing user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Creates a new user.
     *
     * @param myUser the user to be created.
     * @return the created user.
     */
    @PostMapping
    public MyUser createUser(@RequestBody MyUser myUser) {
        return userService.save(myUser);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     */
    @GetMapping
    public List<MyUser> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return a ResponseEntity containing the user, or 404 Not Found if the user does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Long id) {
        Optional<MyUser> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @param request the HttpServletRequest object.
     * @return a ResponseEntity containing the user's profile information.
     */
    @GetMapping("/userinfo")
    public ResponseEntity<MyUser> getUserInfo(HttpServletRequest request) {
        MyUser userProfile = userService.getUserProfile(request).getBody();
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
