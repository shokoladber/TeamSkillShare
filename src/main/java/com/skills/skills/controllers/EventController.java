package com.skills.skills.controllers;

import com.skills.skills.data.EventRepository;
import com.skills.skills.data.EventsCategoryRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.skill.Skill;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("")
public class EventController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventsCategoryRepository eventsCategoryRepository;

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

    @GetMapping("events/create/{userId}")
    public String createNewEvent (@PathVariable Integer userId, Model model){
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        model.addAttribute("title", "Create New Skill");
        model.addAttribute(new Event());
//        model.addAttribute("tags",Tag.getEvents());
        model.addAttribute("categories", eventsCategoryRepository.findAll());
        return  "events/create";
    }

    @PostMapping("events/create/{userId}")
    public String processNewSkill(@PathVariable Integer userId, HttpSession session, Model model, @ModelAttribute @Valid Skill newSkill, Errors errors) {

        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        // save new event
        eventRepository.save(newEvent);
        //add event to user
        currentUser.addEventsToProfile(newSkill);
        //resave user to update
        userRepository.save(currentUser);
        model.addAttribute("events", currentUser.getEvents());
        return "redirect:/";
    }

    @GetMapping("events/delete/{userId}")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("events/delete/{userId}")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:";
    }

    @GetMapping("detail")
    public String displayEventDetails(@RequestParam Integer eventId, Model model) {

        Optional<Event> result = eventRepository.findById(eventId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + eventId);
        } else {
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
        }

        return "events/detail";
    }

}