package com.personalfinancetracker.dto;

import com.personalfinancetracker.entity.Account;
import com.personalfinancetracker.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestDTO {
    // accountNumber ✅
    @NotBlank(message = "Account number required")
    private String accountNumber;

    //balance
    @NotNull(message = "Balance is required")
    private BigDecimal balance;

    //bankName ✅
    @NotBlank(message = "Bank name is required")
    private String bankName;

    //accountType ✅
    private Account.AccountType accountType;

    //userId
    @NotNull(message = "User id is required")
    private Long userId;
}
