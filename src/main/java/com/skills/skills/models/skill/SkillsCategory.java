package com.skills.skills.models.skill;

import com.skills.skills.models.AbstractEntity;
import com.skills.skills.models.skill.Skill;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SkillsCategory extends AbstractEntity {

    private String catName;

    @OneToMany(mappedBy = "catName")
    private final List<Skill> skills = new ArrayList<>();

    public SkillsCategory(String catName) { this.catName = catName; }

    public SkillsCategory() {}

    public List<Skill> getSkills(){ return skills; }

    public String getCatName() { return catName; }

    public void setCatName(String catName) { this.catName = catName; }

}
