package ua.tvsolutions.bloggg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping()
    String indexPage(){
        return "/home";
    }

    @GetMapping("/home")
    String showHomepage() {

        return "home";
    }

}
