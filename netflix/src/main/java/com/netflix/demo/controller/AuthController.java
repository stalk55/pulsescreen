package com.netflix.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.demo.dto.request.UserRequest;
import com.netflix.demo.dto.request.LoginRequest;
import com.netflix.demo.dto.response.EmailValidationResponse;
import com.netflix.demo.dto.response.LoginResponse;
import com.netflix.demo.dto.response.MessageResponse;
import com.netflix.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    private ResponseEntity<MessageResponse> register (@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(authService.signUp(userRequest));
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response = authService.login(loginRequest.getEmail(),loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate-email")
    private ResponseEntity<EmailValidationResponse> validateEmail(@RequestParam String email){
        return ResponseEntity.ok(authService.validateEmail(email));
    }
}
