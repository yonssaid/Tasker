package com.Tasker.Services;

import com.Tasker.Models.Role;
import com.Tasker.Models.User;
import com.Tasker.Repositories.RoleRepository;
import com.Tasker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        // Set default role to USER if not provided
        if (user.getRole() == null ) {
            Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role USER not found"));
            user.setRole(userRole);
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword()); // Ideally, encode the password
        user.setRole(updatedUser.getRole());
        return userRepository.save(user);
    }
}

