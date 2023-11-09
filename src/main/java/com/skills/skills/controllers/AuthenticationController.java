package com.skills.skills.controllers;

import com.skills.skills.data.*;
import com.skills.skills.models.Tag;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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

    // Other methods...
}
