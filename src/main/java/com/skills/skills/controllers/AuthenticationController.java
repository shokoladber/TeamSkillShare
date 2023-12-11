package com.skills.skills.controllers;

import com.skills.skills.data.*;
import com.skills.skills.models.Tag;
import com.skills.skills.models.dto.RegisterFormDTO;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.User;
import com.skills.skills.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

    @Autowired(required = false)
    UserRepository userRepository;

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public SkillsCategoryRepository skillsCategoryRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId != null ? userRepository.findById(userId).orElse(null) : null;
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                           @ModelAttribute @Valid UserProfile userProfile,
                           Errors errors,
                           HttpSession session,
                           Model model) {

        if (errors.hasErrors()) {
            // Validation errors, return to the registration form with error messages
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("registerFormDTO", registerFormDTO);
            model.addAttribute("title", "Register");
            return "register";
        }

        // Check if the username is already taken
        if (userRepository.existsByUsername(registerFormDTO.getUsername())) {
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("registerFormDTO", registerFormDTO);
            model.addAttribute("error", "Username is already taken. Please choose another.");
            model.addAttribute("title", "Register");
            return "register";
        }

        // Check if the email is already taken
        if (userRepository.existsByEmail(userProfile.getEmail())) {
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("registerFormDTO", registerFormDTO);
            model.addAttribute("error", "Email is already registered. Please use another email.");
            model.addAttribute("title", "Register");
            return "register";
        }

        // Creating a new user
        User newUser = new User();
        newUser.setUsername(registerFormDTO.getUsername());
        newUser.setPassword(registerFormDTO.getPassword());
        newUser.setUserProfile(userProfile);

        // Save the new user to the database
        User savedUser = userRepository.save(newUser);

        // Set the user in the session
        setUserInSession(session, savedUser);

        // Redirect to the user's profile page after successful registration
        return "redirect:/users/profile";
    }


    @GetMapping("/users/profile")
    public String displayPageAfterLogin(HttpSession session, Model model) {
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("skills", user != null ? user.getSkills() : null);
        model.addAttribute("creatorEvents", user != null ? user.getCreatorEvents() : null);
        model.addAttribute("guestEvents", user != null ? user.getGuestEvents() : null);
        model.addAttribute(new Skill());
        model.addAttribute(new Tag());
        model.addAttribute("tags", tagRepository.findAll());
        return "users/profile";
    }

    @PostMapping("/users/profile")
    public String displayPageAfterFilter(@RequestParam int tagId, HttpSession session, Model model) {
        User user = getUserFromSession(session);

        List<Skill> filteredSkills = new ArrayList<>();

        if (user != null) {
            user.getSkills().forEach(skill -> {
                Tag skillTag = skill.tagName;
                if (skillTag != null && skill.getTagId(skillTag) == tagId) {
                    filteredSkills.add(skill);
                }
            });
        }

        model.addAttribute("user", user);
        model.addAttribute("skills", filteredSkills);
        model.addAttribute(new Skill());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("creatorEvents", user != null ? user.getCreatorEvents() : null);
        model.addAttribute("guestEvents", user != null ? user.getGuestEvents() : null);
        return "users/profile";
    }



}
