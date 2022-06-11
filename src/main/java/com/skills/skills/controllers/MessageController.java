package com.skills.skills.controllers;

import com.skills.skills.data.EventRepository;
import com.skills.skills.data.MessagesRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.Tag;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.Message;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MessageController {

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

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

    private static final String userSessionKey = "user";

    @GetMapping("/users/inbox")
    public String displayInbox (HttpSession session, Model model) {
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("messages", user.getMessages());
        return "users/inbox";
    }

    @GetMapping("/events/event_message/event={eventId}")
    public String displayEventMessage (@PathVariable Integer eventId, HttpSession session, Model model) {
        User user = getUserFormSession(session);
        Optional<Event> result = eventRepository.findById(eventId);
        Event currentEvent = result.get();
        Optional<User> eventCreatorUser = userRepository.findById(currentEvent.getCreatorId(currentEvent));
        User creatorUser = eventCreatorUser.get();
        model.addAttribute("creator", creatorUser);
        model.addAttribute(new Message());
        model.addAttribute("user", user);
        model.addAttribute("event", currentEvent);

        return "events/event_message";
    }

    @PostMapping("/events/event_message/event={eventId}")
    public String processEventMessage (@PathVariable Integer eventId, Model model, HttpSession session,  @ModelAttribute @Valid Message newMessage){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<Event> result = eventRepository.findById(eventId);
        Event currentEvent = result.get();
        Optional<User> eventCreatorUser = userRepository.findById(currentEvent.getCreatorId(currentEvent));
        User creatorUser = eventCreatorUser.get();
        messagesRepository.save(newMessage);
        userRepository.save(user);
        userRepository.save(creatorUser);
        model.addAttribute("skills", user.getSkills());
        model.addAttribute("creatorEvents", user.getCreatorEvents());
        model.addAttribute("guestEvents", user.getGuestEvents());
        return "redirect:/users/profile";
    }

}
