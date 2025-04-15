package com.Repository;

import com.Model.Admin;
import java.util.Optional;

public interface AdminRepository {
    Optional<Admin> findByUsername(String username);
    void save(Admin admin); // define your own save logic somewhere
}
