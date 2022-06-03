package com.skills.skills.models.event;

import com.skills.skills.models.AbstractEntity;
import com.skills.skills.models.Tag;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name must be 250 characters or less")
    private String name;

    @ManyToOne
    @NotNull(message = "Event category is required")
    private EventCategory eventCategory;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private EventDetails eventDetails;

//    @ManyToOne
//    public Tag tagName;

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

    public Event(String name, EventCategory eventCategory, EventDetails eventDetails) {
        this.name = name;
        this.eventCategory = eventCategory;
//        this.tagName = tagName;
        this.eventDetails = eventDetails;
    }

    public Event() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

//    public List<Tag> getTags() {
//        return tags;
//    }

//    public Tag getTagName() { return tagName; }

//    public void addTag(Tag tag) {
//        this.tags.add(tag);
//    }

    @Override
    public String toString() {
        return name;
    }

}
