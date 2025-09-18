package com.example.Backend_GI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PrivacyController {

    @GetMapping("/privacy-policy.html")
    public String greeting(Model model) {
        model.addAttribute("message", "Welcome to our website!");
        return "index.html"; // Thymeleaf template name (greeting.html)
    }
}








