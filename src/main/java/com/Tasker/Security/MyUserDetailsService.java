package com.Tasker.Security;

import com.Tasker.Models.MyUser;
import com.Tasker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Implementation of UserDetailsService to load user-specific data.
 * <p>
 * This service retrieves user details from the database and constructs a UserDetails object.
 * </p>
 *
 * @author Yons Said
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(MyUserDetailsService.class.getName());

    /**
     * Constructor for MyUserDetailsService.
     *
     * @param userRepository the repository to perform CRUD operations on users.
     */
    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user to load
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: " + username);
        Optional<MyUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObject = user.get();
            logger.info("User found: " + userObject.getUsername());
            return User.builder()
                    .username(userObject.getUsername())
                    .password(userObject.getPassword())
                    .roles(userObject.getRole().getName())
                    .build();
        } else {
            logger.warning("User not found: " + username);
            throw new UsernameNotFoundException(username);
        }
    }
}
