package com.scififics.writershub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("createspace")
public class CreatespaceController {

    @GetMapping("")
    public String displayCreatespace(Model model) {
        model.addAttribute("pageTitle", "Create Space");
        return "createspace/index";
    }
}
