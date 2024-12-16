package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query("select u from Account u where u.username = ?1")
    Account findByUsername(String username);

    @Query("select u from Account u where u.username = ?1 and u.password = ?2")
    Account getAccountByLogin(String username, String password);
}
