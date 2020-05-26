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
@RequestMapping("tagcraft")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("add-tag")
    public String displayAddTagForm(Model model) {
        model.addAttribute(new Tag());
        model.addAttribute("tags", tagRepository.findAll());
        return "tagcraft/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid Tag newTag,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "tagcraft/add-tag";
        }

        tagRepository.save(newTag);
        return "redirect:../tagcraft/add-tag";
    }

}
