package com.skills.skills.controllers;

import com.skills.skills.data.EventRepository;
import com.skills.skills.data.SkillsCategoryRepository;
import com.skills.skills.data.TagRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    public EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public SkillsCategoryRepository skillsCategoryRepository;

    private static final String userSessionKey = "user";

    public User getUserFormSession(HttpSession session) {
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

    //responds to request at events/create?userId=##
    @GetMapping("create/{userId}")
    public String createNewEvent (@PathVariable Integer userId, Model model){
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        model.addAttribute("title", "Create New Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", skillsCategoryRepository.findAll());
        return  "events/create";
    }

    @PostMapping("create/{userId}")
    public String processNewEvent(@PathVariable Integer userId, HttpSession session, Model model, @ModelAttribute @Valid Event newEvent, Errors errors) {

        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        // save new event
        eventRepository.save(newEvent);
        //add event to user
        currentUser.addEventToProfile(newEvent);
        //re-save user to update
        userRepository.save(currentUser);
        model.addAttribute("events", currentUser.getEvents());
        return "redirect:/users/profile";
    }

    @GetMapping("delete/{userId}")
    public String displayDeleteSkillForm(Model model){
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }
    @PostMapping("delete/{userId}")
    public String processDeleteEventForm(@RequestParam (required = false) int [] eventIds, HttpSession session, Model model, @PathVariable Integer userId){
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        if(eventIds != null){
            for(int id : eventIds){
                eventRepository.deleteById(id);
            }
        }
        userRepository.save(currentUser);
        model.addAttribute("events", currentUser.getEvents());
        return "redirect:/users/profile";
    }

}