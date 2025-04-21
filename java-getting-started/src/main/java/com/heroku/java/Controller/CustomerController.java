package com.heroku.java.Controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.heroku.java.Model.CustomerModel;
import com.heroku.java.Services.CustomerServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {

    private final CustomerServices customerService;

    @Autowired
    public CustomerController(CustomerServices customerService) {
        this.customerService = customerService;
    }

    @ModelAttribute("customer")
    public CustomerModel customerModel() {
        return new CustomerModel();
    }

    @GetMapping("/customer")
    public String customerlogin(Model model) {
        // Make sure admin model is available for the form
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new CustomerModel());
        }
        return "customer/customer";
    }

    @PostMapping("/admin")
    public String processLogin(@ModelAttribute("customer") CustomerModel customer,
                               HttpSession session, Model model) {
        try {
            CustomerModel authenticatedCustomer = customerService.authenticateUser(customer.getCustomerUsername(), customer.getCustomerPassword());
            if (authenticatedCustomer != null) {
                session.setAttribute("customer", authenticatedCustomer);
                session.setAttribute("customerID", authenticatedCustomer.getCustomerID());
                session.setAttribute("customerUsername", authenticatedCustomer.getCustomerUsername());
                return "redirect:/customer/dashboard";
            } else {
                model.addAttribute("errorMessage", "Invalid username or password");
                return "customer/customer";
            }
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Database error: " + e.getMessage());
            return "customer/customer";
        }
    }

    @GetMapping("/customer/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Check if customer is logged in
        if (session.getAttribute("customer") == null) {
            return "redirect:/customer";
        }
        
        return "admin/dashboard";
    }
    
    @GetMapping("/signupcustomer")
    public String customerSignup() {
        // accountService.registerCustomer(account);
        return "customer/signupcustomer"; // redirect to login after signup
    }

    @PostMapping("/signupcustomer")
    public String handleCustomerSignup(@ModelAttribute CustomerModel customer, Model model) {
        try {
            // Register the customer
            customerService.signupCustomer(customer);
            
            // Add success message
            model.addAttribute("successMessage", "Account created successfully! Please login.");
            return "redirect:/customer";
        } catch (SQLException e) {
            // Handle registration error
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
            model.addAttribute("customer", customer);
            return "admin/signupadmin";
        }
    }
    
    @GetMapping("/customer/logout")
    public String logout(HttpSession session) {
        // Invalidate session
        session.invalidate();
        return "redirect:/customer";
    }
}