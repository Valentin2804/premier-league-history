package com.example.plhistory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PasswordResetController {

    @GetMapping("/login/passreset")
    public String loginRedirect(Model model){
        model.addAttribute("message", "check your email for password reset");
        System.out.println("aa");
        return "login";
    }
}
