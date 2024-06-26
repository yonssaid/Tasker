package com.Tasker.Controllers;

import com.Tasker.Dto.LoginDto;
import com.Tasker.Dto.RegisterDto;
import com.Tasker.Models.Role;
import com.Tasker.Models.User;
import com.Tasker.Repositories.RoleRepository;
import com.Tasker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {


    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository,
                                    RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("registerpage")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getPassword(),
                        loginDto.getUsername()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User login successful", HttpStatus.OK);

    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setAge(registerDto.getAge());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());

        Role userRole;
        if (registerDto.getRole() != null && !registerDto.getRole().isEmpty()) {
            userRole = roleRepository.findByName(registerDto.getRole())
                    .orElseThrow(() -> new RuntimeException(registerDto.getRole() + " role not set."));
        } else {
            userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("User role not set."));
        }

        user.setRole(userRole);
        userRepository.save(user);

        return new ResponseEntity<>("User has been registered successfully!", HttpStatus.OK);
    }
}
