package com.example.SpringBootTest2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class SimpleController {
    @Value("${spring.application.name}")
    String appName;

    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
}
