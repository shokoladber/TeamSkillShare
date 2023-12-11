package com.skills.skills.controllers;

import com.skills.skills.data.UserRepository;
import com.skills.skills.models.user.User;
import com.skills.skills.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile/{userId}")
    public String viewUserProfile(@PathVariable int userId, Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return "redirect:/";
        }

        User user = userOptional.get();

        if (currentUser == null || currentUser.getId() != user.getId()) {
            // If the current user is not the owner of the profile, redirect to home or another page
            return "redirect:/";
        }

        model.addAttribute("user", currentUser);
        model.addAttribute("skills", user.getSkills());
        return "users/profile";
    }

    // Display a list of users
    @GetMapping
    public String displayUsers(Model model, HttpSession session) {
        User user = authenticationController.getUserFromSession(session);
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "/index";
    }

    // View user's personal information
    @GetMapping("personal_info/{userId}")
    public String viewUser(@PathVariable int userId, HttpSession session, Model model) {
        User currentUser = authenticationController.getUserFromSession(session);
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return "redirect:/users/";
        }

        User user = userOptional.get();

        if (currentUser.getId() != user.getId()) {
            return "redirect:/users";
        } else {
            model.addAttribute("user", currentUser);
            model.addAttribute("skills", user.getSkills());
            return "users/personal_info";
        }
    }

    // Edit user's personal information
    @GetMapping("edit_personal_info/{userId}")
    public String editUser(@PathVariable Integer userId, Model model, HttpSession session) {
        User userLoggedIn = authenticationController.getUserFromSession(session);
        Optional<User> getUserOptional = userRepository.findById(userId);

        if (getUserOptional.isEmpty()) {
            return "redirect:/users/";
        }

        User currentUser = getUserOptional.get();

        if (userLoggedIn.getId() != userId) {
            return "redirect:/users/";
        }

        model.addAttribute("user", currentUser.getUserProfile());
        return "users/edit_personal_info";
    }

    @PostMapping("edit_personal_info/{userId}")
    public String processEdit(@ModelAttribute @Valid UserProfile userProfile,
                              Errors errors, @PathVariable Integer userId, HttpSession session, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("user", userProfile);
            return "users/personal_info";
        }

        User userLoggedIn = authenticationController.getUserFromSession(session);
        Optional<User> getUserOptional = userRepository.findById(userId);

        if (getUserOptional.isEmpty()) {
            return "redirect:/users/";
        }

        User currentUser = getUserOptional.get();

        if (userLoggedIn.getId() != userId) {
            return "redirect:/users/";
        }

        UserProfile currentUserProfile = currentUser.getUserProfile();
        currentUserProfile.setFirstName(userProfile.getFirstName());
        currentUserProfile.setLastName(userProfile.getLastName());
        currentUserProfile.setEmail(userProfile.getEmail());
        currentUserProfile.setPhoneNumber(userProfile.getPhoneNumber());
        currentUserProfile.setZipCode(userProfile.getZipCode());

        userRepository.save(currentUser);
        return "redirect:/users/personal_info/" + userId;
    }

    // View user search results
    @GetMapping("user_details/{userId}")
    public String viewUserSearchResult(@PathVariable int userId, HttpSession session, Model model) {
        User currentUser = authenticationController.getUserFromSession(session);
        Optional<User> searchResultOptional = userRepository.findById(userId);

        if (searchResultOptional.isEmpty()) {
            return "redirect:/users/";
        }

        User searchResultUser = searchResultOptional.get();

        if (currentUser.getId() == searchResultUser.getId()) {
            return "redirect:/users/profile";
        } else {
            model.addAttribute("user", currentUser);
            model.addAttribute("searchUser", searchResultUser);
            model.addAttribute("skills", searchResultUser.getSkills());
            model.addAttribute("events", searchResultUser.getCreatorEvents());
            return "users/user_details";
        }
    }

    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the current session
        return "redirect:/"; // Redirect to the home page
    }
}
