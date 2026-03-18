package com.personalfinancetracker.dto;

import com.personalfinancetracker.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDTO {

//    id
    private Long id;
    private String accountNumber;
    private String userFullName;
    private String bankName;
    private Account.AccountType accountType;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
