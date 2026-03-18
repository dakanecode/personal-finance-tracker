package com.personalfinancetracker.repository;

import com.personalfinancetracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Finding transactions by account
     List<Transaction> findByAccountId(Long accountId);
     List<Transaction> findByCategoryId(Long categoryId);
     List<Transaction> findByAccountIdAndTransactionDateBetween(
             Long accountId,
             LocalDateTime start,
             LocalDateTime end);

    @Query("SELECT t FROM Transaction t WHERE t.account.user.id = :userId AND t.transactionDate BETWEEN :start AND :end")
    List<Transaction> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}

