package com.Tasker.Services;

import com.Tasker.Dto.UpdateUserDto;
import com.Tasker.Dto.RegisterAdminDto;
import com.Tasker.Models.Role;
import com.Tasker.Models.MyUser;
import com.Tasker.Models.Task;
import com.Tasker.Repositories.RoleRepository;
import com.Tasker.Repositories.TaskRepository;
import com.Tasker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an AdminService with the specified user repository and role repository.
     *
     * @param userRepository the repository to perform CRUD operations on users.
     * @param roleRepository the repository to perform CRUD operations on roles.
     */
    @Autowired
    public AdminService(UserRepository userRepository, RoleRepository roleRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
     * Retrieves all tasks.
     *
     * @return a list of all tasks.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
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
     * Updates an existing user with the details provided in the UpdateUserDto.
     *
     * @param id the ID of the user to be updated.
     * @param updateUserDto the DTO containing the updated user details.
     * @return the updated user.
     */
    public MyUser updateUser(Long id, UpdateUserDto updateUserDto) {

        MyUser myUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        myUser.setUsername(updateUserDto.getUsername());
        myUser.setEmail(updateUserDto.getEmail());
        myUser.setAge(updateUserDto.getAge());
        myUser.setFirstName(updateUserDto.getFirstName());
        myUser.setLastName(updateUserDto.getLastName());


        if (updateUserDto.getRoleName() != null) {
            Role newRole = roleRepository.findByName(updateUserDto.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found."));
            myUser.setRole(newRole);
        }


        return userRepository.save(myUser);
    }


    /**
     * Creates a new  user.
     *
     * @param registerAdminDto the Data Transfer Object containing details of the user to be created.
     * @return success message.
     */

    public String createUser(RegisterAdminDto registerAdminDto) {
        if (userRepository.existsByUsername(registerAdminDto.getUsername())) {
            return "Username is taken!";
        }

        MyUser myUser = new MyUser();
        myUser.setUsername(registerAdminDto.getUsername());
        myUser.setPassword(passwordEncoder.encode("password123"));
        myUser.setEmail(registerAdminDto.getEmail());
        myUser.setAge(registerAdminDto.getAge());
        myUser.setFirstName(registerAdminDto.getFirstName());
        myUser.setLastName(registerAdminDto.getLastName());

        Role role = roleRepository.findByName(registerAdminDto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found."));

        myUser.setRole(role);
        userRepository.save(myUser);

        return "User has been created successfully!";
    }


}
