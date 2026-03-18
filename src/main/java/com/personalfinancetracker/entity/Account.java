package com.personalfinancetracker.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account number is required")
    @Column(nullable = false)
    private String accountNumber;

    @NotNull(message = "Balance is required")
    @Column(nullable = false)
    private BigDecimal balance;

    @NotBlank(message = "Bank name is required")
    @Column(nullable = false)
    private String bankName;


    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    public enum AccountType { SAVINGS, CHECKING }


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;







}
