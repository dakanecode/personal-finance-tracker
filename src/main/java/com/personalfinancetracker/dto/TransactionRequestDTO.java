package com.personalfinancetracker.dto;

import com.personalfinancetracker.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDTO {
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    private Transaction.TransactionType transactionType;

    //transactionDate
    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;

    private String description;

    @NotNull(message = "account id is required")
    private Long accountId;

    @NotNull(message = "category id is required")
    private Long categoryId;

}
