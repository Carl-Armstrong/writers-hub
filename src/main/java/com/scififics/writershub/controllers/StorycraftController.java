package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.ChapterData;
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

    @RequestMapping(value= "storyview")
    public String viewStoryview(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Chapter> chapters;
        if (column.toLowerCase().equals("all")){
            chapters = chapterRepository.findAll();
        } else {
            chapters = ChapterData.findByColumnAndValue(column, value, chapterRepository.findAll());
        }
        model.addAttribute("chapters", chapters);

        return "storycraft/storyview";
    }

}
