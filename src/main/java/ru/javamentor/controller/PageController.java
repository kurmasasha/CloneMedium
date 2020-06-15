package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javamentor.model.User;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserServiceImpl;

@Controller
public class PageController {

    public final TopicService topicService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public PageController(TopicService topicService) {
        this.topicService = topicService;
    }


    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) userService.loadUserByUsername(currentUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("userId", user.getId());
        //return "home";
        return "homePage";
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String allTopicsPage(Model model) {
//        //return "all_topics_page";
//        return "rootPage";
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String noLoggedPage(Model model) {
        //return "no_logged_home_dbg";
        return "rootPage";
        //return "all_topics_page";
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
}

