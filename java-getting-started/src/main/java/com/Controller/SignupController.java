package com.Controller;

import com.Model.Admin;
import com.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    private final AdminService adminService;

    @Autowired
    public SignupController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Show the sign-up form for Admin
    @GetMapping("/admin-signup")
    public String showAdminSignupForm(Model model) {
        model.addAttribute("admin", new Admin());  // Add a new Admin object to the model
        return "admin-signup";  // Return the view name for the signup page
    }

    // Handle the sign-up form submission and register the Admin
    @PostMapping("/admin-signup")
    public String registerAdmin(Admin admin, Model model) {
        // Check if the username already exists
        if (adminService.findAdminByUsername(admin.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");  // Add error message to model
            return "admin-signup";  // Return to signup page with error
        }

        // Save the new Admin to the database
        adminService.saveAdmin(admin);
        return "redirect:/admin-login";  // Redirect to login page after successful signup
    }
}
