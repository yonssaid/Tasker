package com.Tasker.Services;

import com.Tasker.Models.MyUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.Tasker.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String JWT_COOKIE_NAME = "jwtToken";

    @Autowired
    private JWTService jwtService;

    public MyUser save(MyUser myUser) {
        return userRepository.save(myUser);
    }

    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }

    public MyUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


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



    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
