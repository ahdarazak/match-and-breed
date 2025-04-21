package com.heroku.java.Controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.heroku.java.Model.AdminModel;
import com.heroku.java.Services.AdminServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final AdminServices adminService;

    @Autowired
public AdminController(AdminServices adminService) {
    this.adminService = adminService;
}

    @ModelAttribute("admin")
    public AdminModel adminModel() {
        return new AdminModel();
    }

    @GetMapping("/admin")
    public String login(Model model) {
        // Make sure admin model is available for the form
        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new AdminModel());
        }
        return "admin/admin";
    }

    @PostMapping("/admin")
    public String processLogin(@ModelAttribute("admin") AdminModel admin,
                               HttpSession session, Model model) {
        try {
            AdminModel authenticatedAdmin = adminService.authenticateAdmin(admin.getUsername(), admin.getPassword());
            if (authenticatedAdmin != null) {
                session.setAttribute("admin", authenticatedAdmin);
                session.setAttribute("adminID", authenticatedAdmin.getAdminID());
                session.setAttribute("adminUsername", authenticatedAdmin.getUsername());
                return "redirect:/admin/dashboard";
            } else {
                model.addAttribute("errorMessage", "Invalid username or password");
                return "admin/admin";
            }
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Database error: " + e.getMessage());
            return "admin/admin";
        }
    }
    

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Check if admin is logged in
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin";
        }
        
        return "admin/dashboard";
    }

    @GetMapping("/signupadmin")
    public String adminSignup(Model model) {
        model.addAttribute("admin", new AdminModel());
        return "signupadmin";
    }

    @PostMapping("/signupadmin")
    public String handleAdminSignup(@ModelAttribute AdminModel admin, Model model) {
        try {
            // Register the admin
            adminService.signupAdmin(admin);
            
            // Add success message
            model.addAttribute("successMessage", "Account created successfully! Please login.");
            return "redirect:/admin";
        } catch (SQLException e) {
            // Handle registration error
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
            model.addAttribute("admin", admin);
            return "admin/signupadmin";
        }
    }
    
    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        // Invalidate session
        session.invalidate();
        return "redirect:/admin";
    }
}