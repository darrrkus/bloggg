package ua.tvsolutions.bloggg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.tvsolutions.bloggg.models.AppUser;
import ua.tvsolutions.bloggg.repos.AppUserRepository;
import ua.tvsolutions.bloggg.services.AppUserService;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private AppUserService appUserService;

    @Autowired
    public ProfileController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    String showProfilePage(){
        return "profile";
    }

    @GetMapping("/edit")
    String editProfile(Model model, Principal principal){
        String username = principal.getName();
        AppUser appUser = appUserService.findByUsername(username);
        model.addAttribute("appUser",appUser);
        return "profile";
    }

}
