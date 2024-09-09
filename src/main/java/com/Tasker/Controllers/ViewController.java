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
     * Displays the features page.
     *
     * @return the name of the features view.
     */
    @GetMapping("/features")
    public String showFeaturesPage() {
        return "features";
    }

    /**
     * Displays the contact page.
     *
     * @return the name of the contactt us view.
     */
    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    /**
     * Displays the user home page.
     *
     * @return the name of the user home view.
     */
    @GetMapping("/user/home")
    public String showUserHome() {
        return "forward:/userhome.html";
    }

    /**
     * Displays the calendar view page.
     *
     * @return the name of the calendar view page.
     */
    @GetMapping("/user/home/calendar")
    public String showCalendarView() {
        return "forward:/userhome.html";
    }

    /**
     * Displays the table view page.
     *
     * @return the name of the table view page.
     */
    @GetMapping("/user/home/table")
    public String showTableView() {
        return "forward:/userhome.html";
    }

    /**
     * Displays the admin tools page.
     *
     * @return the name of the table view page.
     */
    @GetMapping("/admin/tools")
    public String showAdminTools() {
        return "forward:/userhome.html";
    }

    /**
     * Displays the admin users page.
     *
     * @return the name of the users view page.
     */
    @GetMapping("/admin/users")
    public String showAdminUsers() {
        return "forward:/userhome.html";
    }

    /**
     * Displays the admin tasks page.
     *
     * @return the name of the tasks view page.
     */
    @GetMapping("/admin/tasks")
    public String showAdminTasks() {
        return "forward:/userhome.html";
    }

    /**
     * Displays the admin logistics dashboard page.
     *
     * @return the name of the logistics view page.
     */
    @GetMapping("/admin/logistics")
    public String showAdminLogisticsDashboard() {
        return "forward:/userhome.html";
    }

}
