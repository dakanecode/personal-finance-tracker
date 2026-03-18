package com.personalfinancetracker.controller;

import com.personalfinancetracker.dto.TransactionRequestDTO;
import com.personalfinancetracker.dto.TransactionResponseDTO;
import com.personalfinancetracker.service.TransactionService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO dto){
        return new ResponseEntity<>(transactionService.createTransaction(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id){
        return new ResponseEntity<>(transactionService.getTransactionById(id),HttpStatus.OK);
    }
    @GetMapping("/account/{accountId}")
    public  ResponseEntity <List<TransactionResponseDTO>> getTransactionsByAccountId(@PathVariable Long accountId){
        return new ResponseEntity<>(transactionService.getTransactionsByAccountId(accountId),HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByCategoryId(@PathVariable Long categoryId){
        return new ResponseEntity<>(transactionService.getTransactionsByCategoryId(categoryId),HttpStatus.OK);
    }
    @GetMapping("/account/{accountId}/date-range")  // getTransactionsByAccountAndDateRange
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByAccountAndDateRange(
            @PathVariable Long accountId,@RequestParam LocalDateTime start,@RequestParam LocalDateTime end){
        return new ResponseEntity<>(transactionService.getTransactionsByAccountAndDateRange(accountId,start,end),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionRequestDTO dto){
        return new ResponseEntity<>(transactionService.updateTransaction(id,dto),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
