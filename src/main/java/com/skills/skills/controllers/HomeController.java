package com.skills.skills.controllers;

import com.skills.skills.data.EventRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    private static final String userSessionKey = "user";

    private User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        return userRepository.findById(userId).orElse(null);
    }

    @GetMapping("")
    public String displayHomepage(Model model, HttpSession session) {
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("title", "What is SkillShare");
        return "index";
    }

    @GetMapping("home")
    public String displayHome(Model model, HttpSession session) {
        User user = getUserFromSession(session);
        List<Event> classes = new ArrayList<>();
        eventRepository.findAll().forEach(classes::add);
        Collections.shuffle(classes);

        model.addAttribute("user", user);
        model.addAttribute("events", classes);
        return "/home";
    }

    @GetMapping("about")
    public String displayAboutUs(Model model, HttpSession session) {
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("title", "About Us");
        return "/about";
    }
}
