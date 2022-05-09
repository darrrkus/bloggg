package ua.tvsolutions.bloggg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping()
    String showHomepage(Principal principal, Model model) {
        String username;
        if (principal == null) {
            username = "guest";
        } else{
            System.out.println(principal);
            username = principal.getName();
        }
        model.addAttribute("username", username);
        return "home";
    }

}
