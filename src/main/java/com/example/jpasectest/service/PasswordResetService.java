package com.example.jpasectest.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.jpasectest.model.PasswordResetToken;
import com.example.jpasectest.model.User;
import com.example.jpasectest.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSenderService mailSenderService;

    public PasswordResetService() {
    }

    public void createPasswordResetTokenForEmail(String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setToken(UUID.randomUUID().toString());
            passwordResetToken.setEmail(user.getEmail());
            passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            passwordResetTokenRepository.save(passwordResetToken);
            // Send password reset email

            mailSenderService.send(email, "password reset", passwordResetToken.getToken());
        }
    }

    public void resetPassword(String token, String password) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken != null && passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            String email = passwordResetToken.getEmail();
            User user = userService.findByEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            passwordResetTokenRepository.delete(passwordResetToken);
        }
    }
}