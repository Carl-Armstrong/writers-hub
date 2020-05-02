package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.models.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("storycraft")
public class StorycraftController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @GetMapping("storyhub")
    public String renderStoryhubForm(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        return "storycraft/storyhub";
    }

    @PostMapping("storyhub")
    public String processStoryhubForm(@ModelAttribute Story newStory, Model model) {
        storyRepository.save(newStory);
        return "redirect:../storycraft/storyhub";
    }

    @GetMapping("storyview")
    public String viewStoryview(Model model) {
        return "storycraft/storyview";
    }

}
