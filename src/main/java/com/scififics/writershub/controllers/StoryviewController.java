package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.data.WorldRepository;
import com.scififics.writershub.models.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("storyview")
public class StoryviewController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("storylist")
    public String viewStorylist(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("worlds", worldRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("pageTitle", "Story List");
        return "storyview/storylist";
    }

    @RequestMapping(value = "storyviewer/{storyId}")
    public String viewStoryviewer(Model model, @PathVariable int storyId) {

        Optional optStory = storyRepository.findById(storyId);

        if (optStory.isPresent()) {
            Story story = (Story) optStory.get();
            model.addAttribute("story", story);
            model.addAttribute("pageTitle", story.getTitle());
            return "storyview/storyviewer";
        } else {
            return "redirect:../storyview/storylist";
        }
    }

}
