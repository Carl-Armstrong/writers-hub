package com.scififics.writershub.controllers;

import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.models.Tag;
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
@RequestMapping("createspace/tagscape")
public class TagscapeController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("")
    public String displayAddTagForm(Model model) {
        model.addAttribute(new Tag());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("pageTitle", "Tag Hub");
        return "createspace/tagscape/index";
    }

    @PostMapping("")
    public String processAddTagForm(@ModelAttribute @Valid Tag newTag,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "createspace/tagscape/index";
        }

        tagRepository.save(newTag);
        return "redirect:../createspace/tagscape";
    }
}
