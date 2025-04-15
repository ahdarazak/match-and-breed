package com.Controller;  // Corrected package name

import com.Model.Admin;  // Correct import for Admin model
import com.Services.AdminService;  // Correct import for AdminService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin-signup")
    public String showAdminSignupForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-signup"; // View name for the signup page
    }

    @PostMapping("/admin-signup")
    public String registerAdmin(Admin admin, Model model) {
        // Check if the username already exists
        if (adminService.findAdminByUsername(admin.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "admin-signup"; // Return to signup form with error
        }

        // Save the new admin
        adminService.saveAdmin(admin);
        return "redirect:/admin-login"; // Redirect to login page
    }
}
