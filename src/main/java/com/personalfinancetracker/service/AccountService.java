package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.AccountRequestDTO;
import com.personalfinancetracker.dto.AccountResponseDTO;
import com.personalfinancetracker.entity.Account;
import com.personalfinancetracker.entity.User;
import com.personalfinancetracker.exception.DuplicateResourceException;
import com.personalfinancetracker.exception.ResourceNotFoundException;
import com.personalfinancetracker.repository.AccountRepository;
import com.personalfinancetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    // create account:
    public AccountResponseDTO createAccount(AccountRequestDTO dto){
        if(accountRepository.existsByAccountNumber(dto.getAccountNumber())){
            throw new DuplicateResourceException("Accounts already exists");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Account account = Account.builder()
                .accountNumber(dto.getAccountNumber())
                .balance(dto.getBalance())
                .bankName(dto.getBankName())
                .accountType(dto.getAccountType())
                .user(user)
                .build();

        Account saved = accountRepository.save(account);
        return mapToResponseDTO(saved);
    }

    // find account by id
    public AccountResponseDTO getAccountsById(Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToResponseDTO(account);
    }
    // find all accounts
    public List<AccountResponseDTO> getAccountsByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found"));
        return accountRepository.findByUserId(userId)  // gets only THIS user's accounts
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    // update account:
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO dto){
        // get account by id
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

         account.setAccountNumber(dto.getAccountNumber());
         account.setBalance(dto.getBalance());
         account.setBankName(dto.getBankName());
         account.setAccountType(dto.getAccountType());

         Account saved = accountRepository.save(account);
         return mapToResponseDTO(saved);

    }

    // delete account by id
    public void deleteAccountById(Long id){
        accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        accountRepository.deleteById(id);
    }

    // helper fun: mapToResponseDTO
    private AccountResponseDTO mapToResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .bankName(account.getBankName())
                .accountType(account.getAccountType())
                .userFullName(account.getUser().getFirstName() + " " + account.getUser().getLastName())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
