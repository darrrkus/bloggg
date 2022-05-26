package ua.tvsolutions.bloggg.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.tvsolutions.bloggg.models.AppUser;
import ua.tvsolutions.bloggg.models.Post;
import ua.tvsolutions.bloggg.repos.PostRepository;
import ua.tvsolutions.bloggg.services.AppUserService;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;
    private final PostRepository postRepository;
    private final AppUserService appUserService;

    public MainController(PostRepository postRepository, AppUserService appUserService) {
        this.postRepository = postRepository;
        this.appUserService = appUserService;
    }

    @GetMapping("/home")
    String indexPage(Model model,
                     @RequestParam(name = "filter", defaultValue = "") String filter) {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()) {
            posts = postRepository.findByTextContains(filter);
        } else {
            posts = postRepository.findAll();
        }
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        return "/home";
    }

    @PostMapping("/home")
    String addPost(@RequestParam String text,
                   @RequestParam String tag,
                   @RequestParam("file") MultipartFile file,
                   Principal principal) throws IOException
    {
        AppUser author = appUserService.findByUsername(principal.getName());
        Post newPost = new Post(text, tag, author);
        if (file != null &&
                file.getOriginalFilename() != null &&
                !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "\\" + resultFilename));
            newPost.setFilename(resultFilename);
        }
        postRepository.save(newPost);
        return "redirect:/home";
    }

    @GetMapping("/")
    String showHomepage() {

        return "redirect:/home";
    }


}
