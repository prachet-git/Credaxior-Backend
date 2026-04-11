package com.lms.controller;

import com.lms.model.Loan;
import com.lms.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private LoanRepository loanRepo;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<Loan> loans = loanRepo.findAll();

        long approved = loans.stream().filter(l -> l.getStatus().equals("APPROVED")).count();
        long pending = loans.stream().filter(l -> l.getStatus().equals("PENDING")).count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalLoans", loans.size());
        stats.put("approvedLoans", approved);
        stats.put("pendingLoans", pending);

        return stats;
    }
}