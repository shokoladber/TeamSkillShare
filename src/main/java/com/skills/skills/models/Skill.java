package com.skills.skills.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Max(value = 30, message = "Name must be 30 characters or less")
    private String name;

    private Category catName;

    public Skill(String name, Category catName) {
        this.name = name;
        this.catName = catName;
//        this.description = description;
//        this.tag = tag;
    }

    public Skill() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category catName() {
        return catName;
    }

    public void setCategory(Category catName) {
        this.catName = catName;
    }
//    public SkillDescription getDescription() {
//        return description;
//    }
//
//    public void setDescription(SkillDescription description) {
//        this.description = description;
//    }

//    public Tag getTag() {
//        return tag;
//    }
//
//    public void setTag(Tag tag) {
//        this.tag = tag;
//    }
}
