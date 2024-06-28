package com.Tasker.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/registerpage")
    public String showRegistrationForm()
    {
        return "register";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "index";
    }
}
