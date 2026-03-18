package com.personalfinancetracker.controller;

import com.personalfinancetracker.dto.MonthlyReportDTO;
import com.personalfinancetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final TransactionService transactionService;

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyReportDTO> generateMonthlyReport(@RequestParam Long userId,@RequestParam int month, @RequestParam int year){
        return new ResponseEntity<>(transactionService.generateMonthlyReport(userId, month, year), HttpStatus.OK);
    }
}
