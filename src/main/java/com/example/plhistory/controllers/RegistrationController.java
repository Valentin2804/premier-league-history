package com.example.plhistory.controllers;

import com.example.plhistory.controllers.requests.RegistrationRequest;
import com.example.plhistory.entities.User.User;
import com.example.plhistory.services.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserDetailsServiceImpl service;

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") RegistrationRequest request){

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") RegistrationRequest request, Model model){

        if(!request.getPassword().equals(request.getConfirmPassword())){
            model.addAttribute("message", "Different passwords provided");
            return "registration";
        }

        User user1 = new User();
        user1.setEmail(request.getEmail());
        user1.setFirstName(request.getFirstName());
        user1.setLastName(request.getLastName());
        user1.setPassword(request.getPassword());

        return service.registerUser(user1, model);
    }

    @GetMapping("registration/confirm")
    public String confirm(@RequestParam("token") String token){
        return service.confirmToken(token);
    }
}
