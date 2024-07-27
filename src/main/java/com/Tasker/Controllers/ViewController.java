package com.Tasker.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling view-related requests.
 *
 * @author Yons Said
 */
@Controller
public class ViewController {

    /**
     * Displays the registration form.
     *
     * @return the name of the registration view.
     */
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    /**
     * Displays the home page.
     *
     * @return the name of the home page view.
     */
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    /**
     * Displays the login form.
     *
     * @return the name of the login view.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Displays the about us page.
     *
     * @return the name of the about us view.
     */
    @GetMapping("/aboutus")
    public String showAboutUsPage() {
        return "aboutus";
    }

    /**
     * Displays the user home page.
     *
     * @return the name of the user home view.
     */
    @GetMapping("/user/home")
    public String showUserHome() {
        return "forward:/userhome2.html";
    }

    /**
     * Displays the admin home page.
     *
     * @return the name of the admin home view.
     */
    @GetMapping("/admin/home")
    public String showAdminHome() {
        return "adminhome";
    }
}
