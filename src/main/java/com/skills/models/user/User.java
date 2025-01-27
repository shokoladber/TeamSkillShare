package com.skills.models.user;

import com.skills.models.AbstractEntity;
import com.skills.models.event.Event;
import com.skills.models.skill.Skill;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    private String email;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private UserProfile userProfile;

    @OneToMany(mappedBy = "id")
    private final List<Message> messages = new ArrayList<>();

    @ManyToMany
    private final List<Skill> skills = new ArrayList<>();

    @ManyToMany
    private final List<Event> creatorEvents = new ArrayList<>();

    @ManyToMany
    private final List<Event> guestEvents = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.email = userProfile.getEmail();
    }

    public User() {

    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setEmail(String email) {userProfile.setEmail(email);}

    public String getEmail() { return userProfile.getEmail(); }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Event> getCreatorEvents() {
        return creatorEvents;
    }

    public List<Event> getGuestEvents() {
        return guestEvents;
    }

    // Utility methods
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public void addSkillsToProfile(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkillsFromProfile(Skill skill) {
        this.skills.remove(skill);
    }

    public void addCreatorEventToProfile(Event event) {
        this.creatorEvents.add(event);
    }

    public void addGuestEventToProfile(Event event) {
        this.guestEvents.add(event);
    }

    public void removeGuestEventFromProfile(Event event) {
        this.guestEvents.remove(event);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessageToInbox(Message message) {
        this.messages.add(message);
    }

    public void setUsername(String username) {
    }

    public void setPassword(String password) {
    }
}
