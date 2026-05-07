package com.netflix.demo.service;

import com.netflix.demo.dto.request.UserRequest;
import com.netflix.demo.dto.response.EmailValidationResponse;
import com.netflix.demo.dto.response.LoginResponse;
import com.netflix.demo.dto.response.MessageResponse;

import jakarta.validation.Valid;

public interface AuthService {
    MessageResponse signUp(@Valid UserRequest userRequest);
    LoginResponse login(String email,String password);
    EmailValidationResponse validateEmail(String email);
}
