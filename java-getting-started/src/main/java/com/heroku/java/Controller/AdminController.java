package com.heroku.java.Controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.heroku.java.Model.AdminModel;

@SpringBootApplication
@Controller
public class AdminController {

    @Autowired
    public AdminController(DataSource dataSource) {
    }

    @GetMapping("/admin")
    public String login() {
        return "admin/admin";
    }

    @GetMapping("/signupadmin")
    public String adminSignup(@ModelAttribute AdminModel account) {
        // accountService.registerAdmin(account);
        return "admin/signupadmin"; // redirect to login after signup
    }

    // @PostMapping("/signupadmin")
    // public String handleAdminSignup(@ModelAttribute AccountAdminModel account) {
    //     accountService.registerAdmin(account);
    //     return "redirect:/admin"; // redirect to login after signup
    // }
}
