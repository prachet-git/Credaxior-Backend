package com.lms.service;

import com.lms.model.Loan;
import com.lms.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepo;

    public List<Loan> getAllLoans() {
        return loanRepo.findAll();
    }

    public Loan createLoan(Loan loan) {
        loan.setStatus("PENDING");
        return loanRepo.save(loan);
    }

    public Loan approveLoan(Long id) {
        Loan loan = loanRepo.findById(id).orElseThrow();
        loan.setStatus("APPROVED");
        return loanRepo.save(loan);
    }

    public Loan rejectLoan(Long id) {
        Loan loan = loanRepo.findById(id).orElseThrow();
        loan.setStatus("REJECTED");
        return loanRepo.save(loan);
    }
}