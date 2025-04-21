package com.heroku.java.Controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@Controller
public class CustomerController {

    @Autowired
    public CustomerController(DataSource dataSource) {
    }

    @GetMapping("/customer")
    public String customerlogin() {
        return "customer";
    }

    @GetMapping("/signupcustomer")
    public String customerSignup() {
        // accountService.registerAdmin(account);
        return "customer/signupcustomer"; // redirect to login after signup
    }

    // @PostMapping("/signupadmin")
    // public String handleAdminSignup(@ModelAttribute AccountAdminModel account) {
    //     accountService.registerAdmin(account);
    //     return "redirect:/admin"; // redirect to login after signup
    // }
}
