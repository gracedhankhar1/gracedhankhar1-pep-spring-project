package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        if((account.getUsername() != "") && (account.getPassword().length() > 4)){
            return accountRepository.save(account);
        }
        return null;
    }

    public Account checkIfUsernameExists(Account account){
        return accountRepository.findByUsername(account.getUsername());
    }

    public Account loginAccount(Account account){
        return accountRepository.getAccountByLogin(account.getUsername(), account.getPassword());
    }

}
