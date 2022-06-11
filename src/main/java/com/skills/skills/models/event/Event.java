package com.skills.skills.models.event;

import com.skills.skills.models.AbstractEntity;
import com.skills.skills.models.Tag;
import com.skills.skills.models.skill.SkillsCategory;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
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

    @NotBlank(message ="Say something about your event!")
    @Size(max = 500, message = "Description too long.")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    @ManyToOne
    @NotNull(message = "Skill category is required")
    private SkillsCategory skillsCategory;

    public Event(String name, String description, String contactEmail, SkillsCategory skillsCategory) {
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.skillsCategory = skillsCategory;
    }

    public Event() {}

    public String getName() {  return name; }

    public void setName(String name) { this.name = name; }

    public SkillsCategory getSkillsCategory() { return skillsCategory; }

    public void setSkillsCategory(SkillsCategory skillsCategory) { this.skillsCategory = skillsCategory; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getContactEmail() { return contactEmail; }

    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

}
