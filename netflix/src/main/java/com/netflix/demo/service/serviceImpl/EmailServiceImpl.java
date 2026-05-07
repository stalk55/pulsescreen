package com.netflix.demo.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.netflix.demo.exception.EmailNotVerifiedException;
import com.netflix.demo.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{


    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:4200}")
    private String frontEndUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    public void sendVerificationEmail(String toEmail, String token) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail) ;
            message.setTo(toEmail);
            message.setSubject("PulseScreen - Verify your mail");
            String verificationLink = frontEndUrl + "/verify-email?token"+token;

            String emailBody =
                "Welcome to pulseScreen\n\n"
                            + "Thank you for registering. please verify your email adress by clicking on the link below : \n\n"
                            +verificationLink
                            +"\n\n"
                            +"This link will expire in 24 hours. \n\n"
                            +"If you didnt crreated this account simply ignore it"
                            +"Best Regards,\n"
                            +"PulseScreen Team";
            message.setText(emailBody);
            mailSender.send(message);
            logger.info("verification mail sent to {}",toEmail);

        }catch(Exception ex){
            logger.error("failed to send verification mail to {} : {}",toEmail,ex.getMessage(),ex);
            throw new EmailNotVerifiedException("Failled to send verification email");
        }
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String token) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("PulseScreen - Passwor reset");

            String resetLink = frontEndUrl + "/reset-password?token="+token;

            String emailBody = 
                            "Hi,\n\n"
                                    +"We received a arequest to reset your password. Click the Link below to reset it : \n\n"
                                    +resetLink
                                    +"\n\n"
                                    +"This link will expire in 1 hour"
                                    +"If you didn't request a password reset, please ignore this email"
                                    +"Best regards"
                                    +"PulseScreen Team";
            message.setText(emailBody);
            mailSender.send(message);

            logger.info("password reset email sent to {]",toEmail);

        }catch(Exception ex){
            logger.error("failed to send reset password email to {} : {}",toEmail,ex.getMessage(),ex);
            throw new RuntimeException("failed to send password reset password email");
        }
    }

    

}
