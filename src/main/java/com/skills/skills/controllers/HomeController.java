package com.skills.skills.controllers;

import com.skills.skills.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {

    //HomePage
    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("test","This is a Hello World Test");
        model.addAttribute("people", Arrays.asList(
                new User("John","Smith","test@aol.com"),
                new User("Jane","Doe","test@google.com"),
                new User("Richard","Roe", "test@yahoo.com")
        ));
        return "index";
    }

}
