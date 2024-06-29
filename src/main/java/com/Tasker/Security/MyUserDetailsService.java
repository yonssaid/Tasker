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

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(MyUserDetailsService.class.getName());

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
                    .roles(userObject.getRole().getName()) // Directly get the role name
                    .build();
        } else {
            logger.warning("User not found: " + username);
            throw new UsernameNotFoundException(username);
        }
    }
}
