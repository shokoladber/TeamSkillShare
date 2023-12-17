package com.skills.controllers;

import com.skills.data.TagRepository;
import com.skills.data.UserRepository;
import com.skills.models.Tag;
import com.skills.models.authentication.AuthenticationService;
import com.skills.models.dto.LoginFormDTO;
import com.skills.models.dto.RegisterFormDTO;
import com.skills.models.skill.Skill;
import com.skills.models.user.User;
import com.skills.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

    @Autowired(required = false)
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    private static final String USER_SESSION_KEY = "user";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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

    @PostMapping("/register")
    public String submitRegisterForm(@ModelAttribute("registerFormDTO") @Valid RegisterFormDTO registerFormDTO,
                                     @ModelAttribute("userProfile") @Valid UserProfile userProfile,
                                     Errors errors,
                                     HttpSession session,
                                     Model model) {

        if (errors.hasErrors()) {
            return handleRegistrationErrors(registerFormDTO, userProfile, errors, session, model);
        }

        if (userRepository.existsByUsername(registerFormDTO.getUsername())) {
            return handleUsernameTakenError(registerFormDTO, userProfile, model);
        }

        if (userRepository.existsByEmail(userProfile.getEmail())) {
            return handleEmailTakenError(registerFormDTO, userProfile, model);
        }

        User newUser = createAndSaveUser(registerFormDTO, userProfile);
        setUserInSession(session, newUser);

        return "redirect:/users/profile";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginFormDTO loginFormDTO, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the login form
            return "login";
        }

        // Handle user authentication
        boolean isAuthenticated = authenticationService.authenticate(loginFormDTO.getUsername(), loginFormDTO.getPassword());

        if (isAuthenticated) {
            // Set some flag in the session or security context to mark the user as authenticated
            // For example, you might use Spring Security for this purpose
            // SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password, authorities));

            // Redirect to the home page after successful authentication
            return "redirect:/home";
        } else {
            // If authentication fails, add an error message and return to the login form
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }


    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(USER_SESSION_KEY);
        return userId != null ? userRepository.findById(userId).orElse(null) : null;
    }

    private void setUserInSession(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user.getId());
    }

    private String handleRegistrationErrors(RegisterFormDTO registerFormDTO, UserProfile userProfile, Errors errors, HttpSession session, Model model) {
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("registerFormDTO", registerFormDTO);
        model.addAttribute("title", "Register");

        // Clear sensitive information
        registerFormDTO.setPassword(null);
        registerFormDTO.setVerifyPassword(null);

        return "register";
    }

    private String handleUsernameTakenError(RegisterFormDTO registerFormDTO, UserProfile userProfile, Model model) {
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("registerFormDTO", registerFormDTO);
        model.addAttribute("error", "Username is already taken. Please choose another.");
        model.addAttribute("title", "Register");

        // Clear sensitive information
        registerFormDTO.setPassword(null);
        registerFormDTO.setVerifyPassword(null);

        return "register";
    }

    private String handleEmailTakenError(RegisterFormDTO registerFormDTO, UserProfile userProfile, Model model) {
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("registerFormDTO", registerFormDTO);
        model.addAttribute("error", "Email is already registered. Please use another email.");
        model.addAttribute("title", "Register");

        // Clear sensitive information
        registerFormDTO.setPassword(null);
        registerFormDTO.setVerifyPassword(null);

        return "register";
    }

    private User createAndSaveUser(RegisterFormDTO registerFormDTO, UserProfile userProfile) {
        User newUser = new User();
        newUser.setUsername(registerFormDTO.getUsername());
        newUser.setPassword(registerFormDTO.getPassword());
        newUser.setUserProfile(userProfile);
        return userRepository.save(newUser);
    }
}
