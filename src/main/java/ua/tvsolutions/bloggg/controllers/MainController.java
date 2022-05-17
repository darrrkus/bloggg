package ua.tvsolutions.bloggg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.tvsolutions.bloggg.models.AppUser;
import ua.tvsolutions.bloggg.models.Post;
import ua.tvsolutions.bloggg.repos.PostRepository;
import ua.tvsolutions.bloggg.services.AppUserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AppUserService appUserService;
    @GetMapping()
    String indexPage(Model model,
                     @RequestParam(name="filter", defaultValue = "") String filter)
    {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()) {
            posts = postRepository.findByTextContains(filter);
        } else {
            posts = postRepository.findAll();
        }
        model.addAttribute("posts", posts);
        return "/home";
    }

    @PostMapping("/")
    String addPost(@RequestParam  String text,
                   @RequestParam String tag,
                   Principal principal){
        AppUser author = appUserService.findByUsername(principal.getName());
        Post newPost = new Post(text, tag, author);
        postRepository.save(newPost);
        return "redirect:/";
    }

    @GetMapping("/home")
    String showHomepage() {

        return "redirect:/";
    }



}
