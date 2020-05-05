package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.models.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("storyview/{storyId}")
    public String viewStoryview(Model model, @PathVariable int storyId) {
        Optional optStory = storyRepository.findById(storyId);
        if (optStory.isPresent()) {
            Story story = (Story) optStory.get();
            model.addAttribute("story", story);
            model.addAttribute("chapters", chapterRepository.findAll());
            return "storycraft/storyview";
        } else {
            return "redirect:../storycraft/storyhub";
        }
    }

}
