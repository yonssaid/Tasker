package com.Tasker.Services;

import com.Tasker.Models.Role;
import com.Tasker.Models.MyUser;
import com.Tasker.Repositories.RoleRepository;
import com.Tasker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing admin-related operations.
 *
 * @author Yons Said
 */
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructs an AdminService with the specified user repository and role repository.
     *
     * @param userRepository the repository to perform CRUD operations on users.
     * @param roleRepository the repository to perform CRUD operations on roles.
     */
    @Autowired
    public AdminService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     */
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a new user.
     *
     * @param myUser the user to be created.
     * @return the created user.
     */
    public MyUser createUser(MyUser myUser) {
        if (myUser.getRole() == null) {
            Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role USER not found"));
            myUser.setRole(userRole);
        }
        return userRepository.save(myUser);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to be deleted.
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to be updated.
     * @param updatedMyUser the updated user details.
     * @return the updated user.
     */
    public MyUser updateUser(Long id, MyUser updatedMyUser) {
        MyUser myUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        myUser.setUsername(updatedMyUser.getUsername());
        myUser.setPassword(updatedMyUser.getPassword());
        myUser.setRole(updatedMyUser.getRole());
        return userRepository.save(myUser);
    }
}
