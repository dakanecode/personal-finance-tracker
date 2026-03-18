package com.personalfinancetracker.repository;

import com.personalfinancetracker.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    find account by user id
    List<Account> findByUserId(Long userId);
    boolean existsByAccountNumber(String accountNumber);
}
