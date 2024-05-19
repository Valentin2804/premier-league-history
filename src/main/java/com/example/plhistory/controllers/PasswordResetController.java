package com.example.plhistory.controllers;

import com.example.plhistory.services.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PasswordResetController {

    private final UserDetailsServiceImpl service;

    @GetMapping("/login/passreset")
    public String loginRedirect(Model model){

        return "passwordReset";
    }

    @PostMapping("/login/passreset")
    public String handlePassReset(@RequestParam("email") String email) {

        return service.passwordResetEmail(email);
    }
}
