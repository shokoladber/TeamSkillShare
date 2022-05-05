package com.skills.skills.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Max(value = 30, message = "Name must be 30 characters or less")
    private String name;

    @NotBlank(message = "Description is required")
    private SkillCategory category;

    @NotBlank(message = "Description is required")
    @Max(value = 500, message = "Description must be 500 characters or less")
    private SkillDescription description;

    @NotNull
    @Max(value = 15, message = "Tag must be 15 characters or less")
    private Tag tag;

    public Skill(String name, SkillCategory category, SkillDescription description, Tag tag) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.tag = tag;
    }

    public Skill() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    public SkillDescription getDescription() {
        return description;
    }

    public void setDescription(SkillDescription description) {
        this.description = description;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
