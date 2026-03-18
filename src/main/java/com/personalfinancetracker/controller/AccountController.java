package com.personalfinancetracker.controller;

import com.personalfinancetracker.dto.AccountRequestDTO;
import com.personalfinancetracker.dto.AccountResponseDTO;
import com.personalfinancetracker.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO dto){
        return new ResponseEntity<>(accountService.createAccount(dto), HttpStatus.CREATED);
    }
    @GetMapping("/{id}") // getAccountsById
    public ResponseEntity<AccountResponseDTO> getAccountsById(@PathVariable Long id){
        return new ResponseEntity<>(accountService.getAccountsById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")  // getAccountsByUserId
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountRequestDTO dto) {
        return new ResponseEntity<>(accountService.updateAccount(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable Long id) {
        accountService.deleteAccountById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
