package com.skills.models.skill;

import com.skills.models.AbstractEntity;
import com.skills.models.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message = "What is your skill called?")
    @Size(max = 30, message = "Name must be 30 characters or less")
    public String name;

    @ManyToOne
    @NotNull(message = "Skill category is required")
    private SkillsCategory catName;

    @ManyToOne
    @NotNull
    public Tag tagName;

    public Skill(String name, SkillsCategory catName, Tag tagName) {
        this.name = name;
        this.catName = catName;
        this.tagName = tagName;
    }

    public Skill() {}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getTagId(Tag tag) { return tag.getId(); }

    public SkillsCategory getCatName() { return catName; }

    public String getCatNameString(Skill skill) { return skill.catName.getCatName(); }

    public void setCatName(SkillsCategory catName) { this.catName = catName; }

    public Tag getTagName() { return tagName; }

    public void setTagName (Tag tagName) { this.tagName = tagName; }

}


