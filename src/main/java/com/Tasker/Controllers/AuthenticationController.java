package com.Tasker.Controllers;

import com.Tasker.Dto.LoginDto;
import com.Tasker.Dto.RegisterDto;
import com.Tasker.Models.MyUser;
import com.Tasker.Models.Role;
import com.Tasker.Repositories.RoleRepository;
import com.Tasker.Repositories.UserRepository;
import com.Tasker.Services.JWTService;
import com.Tasker.Services.MyUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * REST controller for authentication-related endpoints.
 *
 * @author Yons Said
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MyUserDetailsService myUserDetailsService;

    /**
     * Constructs an AuthenticationController with the specified dependencies.
     *
     * @param authenticationManager the manager responsible for authenticating users.
     * @param userRepository the repository to perform CRUD operations on users.
     * @param roleRepository the repository to perform CRUD operations on roles.
     * @param passwordEncoder the encoder to handle password encoding.
     * @param jwtService the service for handling JWT operations.
     */
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository,
                                    RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                    JWTService jwtService, MyUserDetailsService myUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }


    /**
     * Registers a new user.
     *
     * @param registerDto the data transfer object containing user registration details.
     * @return a ResponseEntity containing a success message or an error message if the username is taken.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        MyUser myUser = new MyUser();
        myUser.setUsername(registerDto.getUsername());
        myUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        myUser.setEmail(registerDto.getEmail());
        myUser.setAge(registerDto.getAge());
        myUser.setFirstName(registerDto.getFirstName());
        myUser.setLastName(registerDto.getLastName());

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("User role not set."));

        myUser.setRole(userRole);
        userRepository.save(myUser);

        return new ResponseEntity<>("User has been registered successfully!", HttpStatus.OK);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param loginDto  the data transfer object containing login details.
     * @param response the HttpServletResponse object.
     * @return a ResponseEntity containing a success message, JWT token, and redirect URL, or an error message if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);

            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);

            response.addCookie(cookie);

            String redirectUrl = "/user/home";

            return ResponseEntity.ok().body(Map.of("message", "Login successful!", "redirectUrl", redirectUrl));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    /**
     * Handles user logout by clearing the JWT token cookie.
     *
     * @param response the HttpServletResponse object.
     * @return a ModelAndView object redirecting to the login page.
     */
    @PostMapping("/logout")
    public ModelAndView logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ModelAndView("redirect:/login");
    }

    /**
     * Checks if the user is authenticated by verifying the JWT token.
     *
     * @param request the HttpServletRequest object.
     * @return a ResponseEntity containing a JSON object with the authentication status.
     */
    @GetMapping("/isAuth")
    public ResponseEntity<Map<String, Boolean>> isAuthenticated(HttpServletRequest request) {
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        boolean isAuthenticated = jwtToken != null && jwtService.isTokenValid(jwtToken);

        return ResponseEntity.ok(Map.of("authenticated", isAuthenticated));
    }


    /**
     * Checks if the authenticated user has the "ROLE_ADMIN" authority.
     *
     * @param request the HttpServletRequest object containing the cookies, including the JWT token.
     * @return a ResponseEntity containing a Boolean value indicating whether the user is an admin.
     *  If the JWT token is missing or invalid, returns a ResponseEntity with HTTP status 401 (Unauthorized) and false.
     */
    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }

            String jwtToken = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }

            if (jwtToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }

            String username = jwtService.extractUsername(jwtToken);

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"));

            return ResponseEntity.ok(isAdmin);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }


}
