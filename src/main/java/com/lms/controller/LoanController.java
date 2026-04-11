package com.lms.controller;

import com.lms.model.Loan;
import com.lms.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    @PutMapping("/{id}/approve")
    public Loan approveLoan(@PathVariable Long id) {
        return loanService.approveLoan(id);
    }

    @PutMapping("/{id}/reject")
    public Loan rejectLoan(@PathVariable Long id) {
        return loanService.rejectLoan(id);
    }
}