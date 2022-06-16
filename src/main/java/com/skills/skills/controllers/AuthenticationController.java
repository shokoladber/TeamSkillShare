package com.skills.skills.controllers;

<<<<<<< HEAD
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.User;
=======
import com.skills.skills.data.*;
import com.skills.skills.models.Tag;
>>>>>>> dev
import com.skills.skills.models.dto.LoginFormDTO;
import com.skills.skills.models.dto.RegisterFormDTO;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.skill.SkillsCategory;
import com.skills.skills.models.user.User;
import com.skills.skills.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired(required = false)
    UserRepository userRepository;

<<<<<<< HEAD
=======
    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public SkillsCategoryRepository skillsCategoryRepository;

>>>>>>> dev
    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

<<<<<<< HEAD
=======
    @GetMapping("/users/profile")
    public String displayPageAfterLogin (HttpSession session, Model model) {
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("skills", user.getSkills());
        model.addAttribute("creatorEvents", user.getCreatorEvents());
        model.addAttribute("guestEvents", user.getGuestEvents());
        model.addAttribute(new Skill());
        model.addAttribute(new Tag());
        model.addAttribute("tags", tagRepository.findAll());
        return "users/profile";
    }

    @PostMapping("/users/profile")
        public String displayPageAfterFilter (@RequestParam int tagId, HttpSession session, Model model) {
        User user = getUserFormSession(session);

        List<Skill> skills;
        skills = user.getSkills();
        List<Skill> filteredSkills = new ArrayList<>();

        skills.forEach(skill -> {
            Tag skillTag = skill.tagName;
            if (skill.getTagId(skillTag) == tagId) {
                filteredSkills.add(skill);
            }
        });
        model.addAttribute("user", user);
        model.addAttribute("skills", filteredSkills);
        model.addAttribute(new Skill());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("creatorEvents", user.getCreatorEvents());
        model.addAttribute("guestEvents", user.getGuestEvents());
        return "users/profile";
    }


>>>>>>> dev
    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }

        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);
<<<<<<< HEAD

        return "redirect:";
=======
        return "redirect:/users/profile";

>>>>>>> dev
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

<<<<<<< HEAD
        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

=======
>>>>>>> dev
        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";
        }

        setUserInSession(request.getSession(), theUser);

<<<<<<< HEAD
        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
=======
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

>>>>>>> dev
        request.getSession().invalidate();
        return "redirect:/login";
    }

}
