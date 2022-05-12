package ua.tvsolutions.bloggg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.tvsolutions.bloggg.models.AppUser;
import ua.tvsolutions.bloggg.repos.AppUserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private AppUserRepository appUserRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    String showProfilePage(){
        return "profile";
    }

    @GetMapping("/edit")
    String editProfile(Model model, Principal principal){
        if (principal==null) throw new IllegalStateException(("Principal is null"));
        String username = principal.getName();
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser==null) throw new UsernameNotFoundException(String.format("user %s is not found!",username));
        model.addAttribute("appUser",appUser);
        model.addAttribute("username", appUser.getUsername());
        return "profile";
    }
}
