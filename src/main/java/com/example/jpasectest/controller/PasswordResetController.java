package com.example.jpasectest.controller;

import com.example.jpasectest.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/request")
    public String showRequestPasswordResetForm() {
        // Add any necessary model attributes
        return "requestPasswordReset";
    }

    @PostMapping("/request")
    public String handlePasswordResetRequest(@RequestParam("email") String email, Model model) {
        passwordResetService.createPasswordResetTokenForEmail(email);
        // Add any necessary model attributes
        return "passwordResetRequested";
    }

    @GetMapping("/change")
    public String showChangePasswordForm(@RequestParam("token") String token, Model model) {
        // Add the token to the model to access it in the form
        model.addAttribute("token", token);
        // Add any other necessary model attributes
        return "changePassword";
    }

    @PostMapping("/change")
    public String handlePasswordChange(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model) {
        passwordResetService.resetPassword(token, password);
        // Add any necessary model attributes
        return "passwordResetSuccessful";
    }
}