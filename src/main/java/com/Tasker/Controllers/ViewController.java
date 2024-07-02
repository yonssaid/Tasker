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

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(){ return "login";}

    @GetMapping("/user/home")
    public String showUserHome(){return "userhome";}
}
