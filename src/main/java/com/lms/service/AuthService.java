package com.lms.service;

import com.lms.model.User;
import com.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

  
    public User register(User user) {

        
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        
        String role = user.getRole();

        if (role == null || role.isBlank()) {
            role = "BORROWER"; 
        }

        role = role.toUpperCase();

       
        if (role.equals("ADMIN")) {
            throw new RuntimeException("Cannot register as ADMIN");
        }

      
        if (!role.equals("BORROWER") &&
            !role.equals("LENDER") &&
            !role.equals("ANALYST")) {
            throw new RuntimeException("Invalid role selected");
        }

        user.setRole(role);

       
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repo.save(user);
    }

   
    public User login(String username, String password) {

        Optional<User> optionalUser = repo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

       
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }
} 
    