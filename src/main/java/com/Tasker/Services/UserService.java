package com.Tasker.Services;

import com.Tasker.Dto.UserDto;
import com.Tasker.Exceptions.CustomExceptions;
import com.Tasker.Models.MyUser;
import com.Tasker.Repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Service class for managing user-related operations.
 * <p>
 * This service handles CRUD operations for users and user authentication.
 * </p>
 *
 * @author Yons Said
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    private static final String JWT_COOKIE_NAME = "jwtToken";

    /**
     * Constructor for UserService.
     *
     * @param userRepository the repository to perform CRUD operations on users.
     * @param jwtService the JWT service to handle JWT operations.
     * @param passwordEncoder the password encoder to handle password encryption.
     */
    @Autowired
    public UserService(UserRepository userRepository, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a user entity to the database.
     *
     * @param myUser the user entity to be saved.
     * @return the saved user entity.
     */
    public MyUser save(MyUser myUser) {
        return userRepository.save(myUser);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return an Optional containing the user if found, or empty otherwise.
     */
    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve.
     * @return the user if found.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public MyUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    /**
     * Retrieves the user profile from the JWT token present in the request cookies.
     *
     * @param request the HttpServletRequest object containing the request information.
     * @return a ResponseEntity containing the user if found and the token is valid, or UNAUTHORIZED status otherwise.
     */
    public ResponseEntity<MyUser> getUserProfile(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jwt = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
        if (jwt != null && jwtService.isTokenValid(jwt)) {
            String username = jwtService.extractUsername(jwt);
            MyUser user = findByUsername(username);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Updates the user's information based on the provided UserDto.
     *
     * @param userDto the UserDto containing the updated user information.
     * @param request the HttpServletRequest object containing the request information.
     * @param response the HttpServletResponse object containing the response information.
     * @return the updated user entity.
     * @throws CustomExceptions.UserNotFoundException if the user is not found or unauthorized.
     * @throws CustomExceptions.UsernameAlreadyExistsException if the provided username already exists.
     * @throws CustomExceptions.InvalidEmailException if the provided email is in an invalid format.
     */
    public MyUser updateUser(UserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        MyUser currentUser = getCurrentUserFromRequest(request)
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found or unauthorized"));

        if (userDto.getUsername() != null && !userDto.getUsername().equals(currentUser.getUsername())) {
            if (userRepository.existsByUsername(userDto.getUsername())) {
                throw new CustomExceptions.UsernameAlreadyExistsException("Username already exists");
            }
            currentUser.setUsername(userDto.getUsername());
        }

        if (userDto.getEmail() != null) {
            if (!isValidEmail(userDto.getEmail())) {
                throw new CustomExceptions.InvalidEmailException("Invalid email format");
            }
            currentUser.setEmail(userDto.getEmail());
        }

        if (userDto.getAge() != null) {
            currentUser.setAge(userDto.getAge());
        }
        if (userDto.getFirstName() != null) {
            currentUser.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            currentUser.setLastName(userDto.getLastName());
        }


        userRepository.save(currentUser);

        Cookie oldCookie = new Cookie("jwtToken", null);
        oldCookie.setHttpOnly(true);
        oldCookie.setPath("/");
        oldCookie.setMaxAge(0);
        response.addCookie(oldCookie);

        String newToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                currentUser.getUsername(), "", new ArrayList<>()));

        Cookie newCookie = new Cookie("jwtToken", newToken);
        newCookie.setHttpOnly(true);
        newCookie.setSecure(true);
        newCookie.setPath("/");
        newCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(newCookie);

        return currentUser;
    }

    /**
     * Updates the user's password.
     *
     * @param currentPassword the current password of the user.
     * @param newPassword the new password to set for the user.
     * @param request the HttpServletRequest object containing the request information.
     * @throws CustomExceptions.UserNotFoundException if the user is not found or unauthorized.
     * @throws CustomExceptions.IncorrectPasswordException if the current password is incorrect.
     */
    public void updateUserPassword(String currentPassword, String newPassword, HttpServletRequest request) {
        MyUser currentUser = getCurrentUserFromRequest(request)
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found or unauthorized"));

        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            throw new CustomExceptions.IncorrectPasswordException("Current password is incorrect");
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Validates the email format using a regex pattern.
     *
     * @param email the email to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    /**
     * Retrieves the current user from the JWT token present in the request cookies.
     *
     * @param request the HttpServletRequest object containing the request information.
     * @return an Optional containing the current user if found and the token is valid, or empty otherwise.
     */
    private Optional<MyUser> getCurrentUserFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    String jwt = cookie.getValue();
                    if (jwtService.isTokenValid(jwt)) {
                        String username = jwtService.extractUsername(jwt);
                        return Optional.of(findByUsername(username));
                    }
                }
            }
        }
        return Optional.empty();
    }
}
