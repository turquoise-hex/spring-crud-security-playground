package com.example.jpasectest.controller;

import com.example.jpasectest.model.User;
import com.example.jpasectest.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "registration";
        }

        System.out.println(user.getName());

        if(userService.registerNewUserAccount(user)){
            return "redirect:/auth/login";
        } else {
            System.out.println("User already exists");
            return "registration";
        }
        }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        if(isActivated){
            System.out.println("User activated successfully");
        } else {
            System.out.println("User not found by activation code");
        }
        return "redirect:/auth/login";
    }


}
