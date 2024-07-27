package com.Tasker.Services;

import com.Tasker.Models.MyUser;
import com.Tasker.Repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    private static final String JWT_COOKIE_NAME = "jwtToken";

    /**
     * Constructor for UserService.
     *
     * @param userRepository the repository to perform CRUD operations on users.
     * @param jwtService the JWT service to handle JWT operations.
     */
    @Autowired
    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    /**
     * Saves a user entity.
     *
     * @param myUser the user to be saved.
     * @return the saved user.
     */
    public MyUser save(MyUser myUser) {
        return userRepository.save(myUser);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     */
    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user.
     * @return an Optional containing the user if found, or empty otherwise.
     */
    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user.
     * @return the user if found.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public MyUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    /**
     * Retrieves the user profile from the JWT token in the request cookies.
     *
     * @param request the HttpServletRequest object.
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
            if (jwtService.isTokenValid(jwt)) {
                String username = jwtService.extractUsername(jwt);
                MyUser user = findByUsername(username);
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
