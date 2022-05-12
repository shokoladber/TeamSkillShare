package com.skills.skills.controllers;

import com.skills.skills.data.SkillsCategoryRepository;
import com.skills.skills.data.SkillsRepository;
import com.skills.skills.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("skills")
public class SkillsController {

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    public SkillsCategoryRepository skillsCategoryRepository;


    @GetMapping("create")
    public String createNewSkill (Model model){
        model.addAttribute("title", "Create New Skill");
        model.addAttribute(new Skill());
        model.addAttribute("categories", skillsCategoryRepository.findAll());
        return  "skills/create";
    }

    @PostMapping("create")
    public String processNewSkill(Model model, @ModelAttribute @Valid Skill newSkill, Errors errors) {

        model.addAttribute("title", "Create New Skill");
        model.addAttribute("categories", skillsCategoryRepository.findAll());

        if (errors.hasErrors()) {
            return "skills/create";
        }

        skillsRepository.save(newSkill);
        return "skills/create";
    }
}

