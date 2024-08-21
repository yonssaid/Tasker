package com.Tasker.Controllers;

import com.Tasker.Dto.UserDto;
import com.Tasker.Exceptions.CustomExceptions;
import com.Tasker.Models.MyUser;
import com.Tasker.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing user
 *
 * <p>
 * Provides endpoints for creating, updating, retrieving, and deleting users.
 * Handles exceptions and returns appropriate HTTP responses.
 * </p>
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
     * Updates user information.
     *
     * @param userDto  the user data to update.
     * @param request  the HttpServletRequest object.
     * @return a ResponseEntity containing the updated user information or an error status.
     */
    @PutMapping("update/info")
    public ResponseEntity<MyUser> updateUser(@RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            MyUser updatedUser = userService.updateUser(userDto, request, response);
            return ResponseEntity.ok(updatedUser);
        } catch (CustomExceptions.UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CustomExceptions.UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Updates the user's password.
     *
     * @param currentPassword the current password of the user.
     * @param newPassword     the new password to set.
     * @param request         the HttpServletRequest object.
     * @return a ResponseEntity indicating the result of the update operation.
     */
    @PutMapping("update/password")
    public ResponseEntity<Void> updatePassword(@RequestParam String currentPassword,
                                               @RequestParam String newPassword,
                                               HttpServletRequest request) {
        try {
            userService.updateUserPassword(currentPassword, newPassword, request);
            return ResponseEntity.ok().build();
        } catch (CustomExceptions.UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CustomExceptions.IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (CustomExceptions.UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return a ResponseEntity containing the user, or 404 Not Found if the user does not exist.
     */
    @GetMapping("{id}")
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
    @GetMapping("info")
    public ResponseEntity<MyUser> getUserInfo(HttpServletRequest request) {
        MyUser userProfile = userService.getUserProfile(request).getBody();
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     */
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
