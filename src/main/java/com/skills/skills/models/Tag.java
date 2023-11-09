package com.skills.skills.models;

import com.skills.skills.models.skill.Skill;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends AbstractEntity{

    @OneToMany(mappedBy = "tagName")
    private final List<Skill> skills = new ArrayList<>();


    private String tagName;

    public Tag(String tagName) { this.tagName = tagName; }

    public Tag() {}

    public String getTagName() { return tagName; }

    public void setTagName(String tagName) { this.tagName = tagName; }

}
