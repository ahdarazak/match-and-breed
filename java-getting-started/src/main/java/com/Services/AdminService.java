package com.Services;

import com.Model.Admin;
import com.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Method to find Admin by username
    public Optional<Admin> findAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    // Method to save Admin
    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }
}
