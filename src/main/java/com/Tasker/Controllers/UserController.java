package com.Tasker.Controllers;

import com.Tasker.Models.MyUser;
import com.Tasker.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private UserService userService;



    @PostMapping
    public MyUser createUser(@RequestBody MyUser myUser) {
        return userService.save(myUser);
    }

    @GetMapping
    public List<MyUser> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Long id) {
        Optional<MyUser> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/userinfo")
    public ResponseEntity<MyUser> getUserInfo(HttpServletRequest request) {
       MyUser userProfile = userService.getUserProfile(request).getBody();
        return ResponseEntity.ok(userProfile);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
