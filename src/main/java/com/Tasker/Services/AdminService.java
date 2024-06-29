package com.Tasker.Services;

import com.Tasker.Models.Role;
import com.Tasker.Models.MyUser;
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

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public MyUser createUser(MyUser myUser) {
        // Set default role to USER if not provided
        if (myUser.getRole() == null ) {
            Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role USER not found"));
            myUser.setRole(userRole);
        }
        return userRepository.save(myUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public MyUser updateUser(Long id, MyUser updatedMyUser) {
        MyUser myUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        myUser.setUsername(updatedMyUser.getUsername());
        myUser.setPassword(updatedMyUser.getPassword()); // Ideally, encode the password
        myUser.setRole(updatedMyUser.getRole());
        return userRepository.save(myUser);
    }
}

