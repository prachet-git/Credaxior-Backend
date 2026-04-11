package com.lms.controller;

import com.lms.dto.*;
import com.lms.model.User;
import com.lms.security.JwtUtil;
import com.lms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = service.register(user);

            
            String token = jwtUtil.generateToken(savedUser.getUsername());

            return ResponseEntity.ok(
                new LoginResponse(token, savedUser.getRole())
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        User user = service.login(req.getUsername(), req.getPassword());

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid credentials"); // ✅ FIX
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(new LoginResponse(token, user.getRole()));
    }
}