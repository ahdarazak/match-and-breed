package com.heroku.java.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heroku.java.Model.AccountAdminModel;
import com.heroku.java.Repository.AccountRepository;

@Service
public class AccountServices {

    @Autowired
    private AccountRepository accountRepository;

    public AccountAdminModel registerAdmin(AccountAdminModel account) {
        return accountRepository.save(account);
    }
}
