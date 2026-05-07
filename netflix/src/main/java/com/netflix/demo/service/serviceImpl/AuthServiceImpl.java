package com.netflix.demo.service.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.netflix.demo.dao.UserRepository;
import com.netflix.demo.dto.request.LoginRequest;
import com.netflix.demo.dto.request.UserRequest;
import com.netflix.demo.dto.response.EmailValidationResponse;
import com.netflix.demo.dto.response.LoginResponse;
import com.netflix.demo.dto.response.MessageResponse;
import com.netflix.demo.entity.User;
import com.netflix.demo.enums.Role;
import com.netflix.demo.exception.AccountDeactivatedException;
import com.netflix.demo.exception.BadCredentialsException;
import com.netflix.demo.exception.EmailAlreadyExistsException;
import com.netflix.demo.exception.EmailNotVerifiedException;
import com.netflix.demo.security.JwtUtil;
import com.netflix.demo.service.AuthService;
import com.netflix.demo.service.EmailService;
import com.netflix.demo.util.ServiceUtils;


@Service
public class AuthServiceImpl implements  AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private ServiceUtils serviceUtils;
    @Override
    public MessageResponse signUp(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailAlreadyExistsException("email already exists");
        }
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFullName(userRequest.getFullName());
        user.setRole(Role.USER);
        user.setActive(true);
        user.setEmailVerified(false);
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(Instant.now().plusSeconds(86400));
        userRepository.save(user);
        emailService.sendVerificationEmail(userRequest.getEmail(),verificationToken);

        return new MessageResponse("REgistration sucessfull please check your email to verify your account");
    }

    @Override
    public LoginResponse login(String email,String password){
        User user = userRepository.findByEmail(email)
                                    .filter(u -> passwordEncoder.matches(password,u.getPassword()))
                                    .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
        if (!user.isActive()) {
            throw new AccountDeactivatedException("your account has been deactivated,please contact support for help ");
        }

        if (!user.isEmailVerified()) {
            throw new EmailNotVerifiedException("please verifiy your mail and try again");
        }

        final String token = jwtUtil.generateToken(user.getEmail(),user.getRole().name());

        return new LoginResponse(token,user.getEmail(),user.getFullName(),user.getRole().name());
    }

    @Override
    public EmailValidationResponse validateEmail(String email){
        boolean exists = userRepository.existsByEmail(email);
        return new EmailValidationResponse(exists,!exists);
    }

}
