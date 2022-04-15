package com.skills.skills.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {

    //HomePage
    @RequestMapping("")
    public String index(Model model){
        return "index";
    }

}
