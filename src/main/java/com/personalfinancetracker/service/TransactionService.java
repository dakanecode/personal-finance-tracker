package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.MonthlyReportDTO;
import com.personalfinancetracker.dto.TransactionRequestDTO;
import com.personalfinancetracker.dto.TransactionResponseDTO;
import com.personalfinancetracker.entity.Account;
import com.personalfinancetracker.entity.Category;
import com.personalfinancetracker.entity.Transaction;
import com.personalfinancetracker.exception.ResourceNotFoundException;
import com.personalfinancetracker.repository.AccountRepository;
import com.personalfinancetracker.repository.CategoryRepository;
import com.personalfinancetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
//     // Fetch all transactions for a user's accounts between the start and end of the given month
//        //Filter INCOME transactions → sum them → totalIncome
//    //Filter EXPENSE transactions → sum them → totalExpenses
//    //Calculate netBalance = totalIncome - totalExpenses
//    //Count transactions
//    //Build and return MonthlyReportDTO
        public MonthlyReportDTO generateMonthlyReport(Long userId,int month, int year){

            // calculate start and end of month
            LocalDateTime start = LocalDateTime.of(year, month,1,0,0,0);
            LocalDateTime end = start.plusMonths(1).minusSeconds(1);



            // // Step 2 - fetch all transactions for this user in that month
            List<Transaction> transactions = transactionRepository.findByUserIdAndDateRange(userId, start, end);
                    // Step 3 - sum income using Streams
            BigDecimal totalIncome = transactions.stream()
                    .filter(t -> t.getTransactionType() == Transaction.TransactionType.DEPOSIT)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalExpenses = transactions.stream()
                    .filter(t -> t.getTransactionType() == Transaction.TransactionType.WITHDRAWAL)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return MonthlyReportDTO.builder()
                    .month(Month.of(month).name() + " " + year)
                    .totalIncome(totalIncome)
                    .totalExpenses(totalExpenses)
                    .netBalance(totalIncome.subtract(totalExpenses))
                    .transactionCount((long) transactions.size())
                    .transactions(transactions.stream()
                            .map(this::mapToResponseDTO)
                            .collect(Collectors.toList()))
                    .build();
        }
    //    createTransaction...one transaction
    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto){

        // check if transaction exists using idd
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("account with not found"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with not found"));
        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .transactionType(dto.getTransactionType())
                .transactionDate(dto.getTransactionDate())
                .description(dto.getDescription())
                .account(account)
                .category(category)
                .build();
        Transaction saved = transactionRepository.save(transaction);
        return mapToResponseDTO(saved);
    }

//    getTransactionById
    public TransactionResponseDTO getTransactionById(Long id){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("transaction not found"));
        return mapToResponseDTO(transaction);
    }
    // getTransactionsByAccountId
    public List<TransactionResponseDTO> getTransactionsByAccountId(Long accountId){
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
//    //getTransactionsByCategoryId
    public List<TransactionResponseDTO> getTransactionsByCategoryId(Long categoryId){
        return transactionRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // getTransactionsByAccountAndDateRange
    public List<TransactionResponseDTO> getTransactionsByAccountAndDateRange(
            Long accountId,
            LocalDateTime start,
            LocalDateTime end){
        return transactionRepository.findByAccountIdAndTransactionDateBetween(accountId,start,end)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    // //updateTransaction
    public TransactionResponseDTO updateTransaction(Long id,TransactionRequestDTO dto){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("transaction not found"));
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setDescription(dto.getDescription());

        Transaction saved = transactionRepository.save(transaction);
        return mapToResponseDTO(saved);
    }
    public void deleteTransaction(Long id){
        transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        transactionRepository.deleteById(id);
    }

    private TransactionResponseDTO mapToResponseDTO(Transaction transaction){
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .transactionDate(transaction.getTransactionDate())
                .description(transaction.getDescription())
                .accountNumber(transaction.getAccount().getAccountNumber())
                .categoryName(transaction.getCategory().getCategoryName())
                .createdAt(transaction.getCreatedAt())
                .build();
    }



//getTransactionsByAccountId
//getTransactionsByCategoryId
//getTransactionsByAccountAndDateRange
//updateTransaction
//deleteTransaction

}
