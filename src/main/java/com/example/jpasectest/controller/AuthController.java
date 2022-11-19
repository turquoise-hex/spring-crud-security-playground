package com.example.jpasectest.controller;


import com.example.jpasectest.model.User;
import com.example.jpasectest.repository.UserRepository;
import com.example.jpasectest.security.SecurityUser;
import com.example.jpasectest.security.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/current")
    public String getCurrentUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication.getName();

    }
}
