package com.example.jpasectest.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "reset_tokens")
public class PasswordResetToken {
    @Id
    @GeneratedValue
    private Long id;

    private String token;
    private String email;
    private LocalDateTime expiryDate;

    // Getters and setters


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}