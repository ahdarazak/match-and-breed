package com.heroku.java.Repository;

import com.heroku.java.Model.AccountAdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountAdminModel, Long> {
    AccountAdminModel findByEmail(String email);
}