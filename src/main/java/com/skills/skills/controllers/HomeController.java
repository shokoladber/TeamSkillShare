package com.skills.skills.controllers;

import com.skills.skills.data.EventRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.dto.LoginFormDTO;
import com.skills.skills.models.dto.RegisterFormDTO;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.user.User;
import com.skills.skills.models.user.UserProfile;
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

        if (user != null) {
            // User is logged in
            model.addAttribute("user", user);
            model.addAttribute("title", "Welcome, " + user.getUsername() + "!");
        } else {
            // User is not logged in
            model.addAttribute("title", "What is SkillShare");
        }

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

    @GetMapping("/register")
    public String displayRegisterPage(Model model, HttpSession session) {
        User user = getUserFromSession(session);

        UserProfile userProfile = new UserProfile();
        RegisterFormDTO registerFormDTO = new RegisterFormDTO();

        // Add attributes to the model
        model.addAttribute("user", user);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("registerFormDTO", registerFormDTO);
        model.addAttribute("title", "Register");

        return "/register";
    }

    // Login user
    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        User user = getUserFromSession(session);

        // If the user is already logged in, redirect to home
        if (user != null) {
            return "redirect:/";
        }

        LoginFormDTO loginFormDTO = new LoginFormDTO();

        model.addAttribute("user", user);
        model.addAttribute("loginFormDTO", loginFormDTO);
        model.addAttribute("title", "Log In");

        return "redirect:/";
    }

    // Logout user
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the current session
        return "redirect:/"; // Redirect to the home page
    }

}
