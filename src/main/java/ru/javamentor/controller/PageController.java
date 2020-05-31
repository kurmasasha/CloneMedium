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
import ru.javamentor.model.Topic;
import ru.javamentor.service.TopicService;



//@org.springframework.stereotype.Controller
@Controller
public class PageController {

    public final TopicService topicService;

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
        model.addAttribute("dy_home", user);
        return "dy_home";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }
  
    @GetMapping("/topic/id")
    public String topicPage(@PathVariable Long id, Model model) {
        Topic topic = topicService.getTopicById(id);
        model.addAttribute("topic", topic);
        return "topic";
    }

}

