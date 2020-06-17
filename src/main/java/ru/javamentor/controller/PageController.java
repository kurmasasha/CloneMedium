package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserServiceImpl;

import java.util.List;

@Controller
public class PageController {

    public final TopicService topicService;

    @Qualifier("userDetailServiceImpl")
    @Autowired
    private UserDetailsService userService;

    @Autowired
    public PageController(TopicService topicService) {
        this.topicService = topicService;
    }


    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String loginPage(@ModelAttribute("message") String message, @ModelAttribute("warning") String warning, Model model) {
        boolean flagMes = false;
        boolean flagWar = false;
        if (message != null && !message.equals("")) {
            flagMes = true;
        }
        if (warning != null && !warning.equals("")) {
            flagWar = true;
        }
        model.addAttribute("flagMes", flagMes);
        model.addAttribute("flagWar", flagWar);
        return "login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) userService.loadUserByUsername(currentUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("userId", user.getId());
        return "homePage";
        //return "homePageGM";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String noLoggedPage(Model model) {

        return "rootPage";
        //return "rootPageGM";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }
  
    @GetMapping("/topic/{id}")
    public String topicPage(@PathVariable Long id, Model model) {
        model.addAttribute("topicId", id);
        return "topic";
    }

    @GetMapping("/admin/allUsers")
    public String adminAllUsersPage() {
        return "admin-all_users";
    }

    @GetMapping("/admin/moderate")
    public String adminModeratePage() {
        return "admin-moderate";
    }
}

