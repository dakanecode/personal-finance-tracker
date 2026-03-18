package com.personalfinancetracker.dto;

import com.personalfinancetracker.entity.Transaction;
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
public class TransactionResponseDTO {
    private Long id;
    private BigDecimal amount;
    private Transaction.TransactionType transactionType;
    private LocalDateTime transactionDate;
    private String description;
    private LocalDateTime createdAt;

    private String accountNumber;
    private String categoryName;
}
