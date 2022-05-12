package ua.tvsolutions.bloggg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.tvsolutions.bloggg.models.AppUser;
import ua.tvsolutions.bloggg.services.AppUserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/save")
    public String registerNewUser(@ModelAttribute("user") AppUser appUser){
        if (appUser!=null) {
             appUserService.registerNewUser(appUser);
        }
        return "redirect:/home";
    }

//    @PostMapping("/delete")
//    String deleteUser(Principal principal){
//
//    }
}
