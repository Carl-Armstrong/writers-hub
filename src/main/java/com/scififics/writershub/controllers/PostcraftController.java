package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("postcraft")
public class PostcraftController {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private StoryRepository storyRepository;

    @GetMapping("post")
    public String renderCreateChapterForm(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute(new Chapter());
        return "postcraft/post";
    }

    @PostMapping("post")
    public String processCreateChapterForm(@ModelAttribute @Valid Chapter newChapter,  Errors errors, Model model,
                                           @RequestParam int storyId) {

        if (errors.hasErrors()) {
            model.addAttribute("stories", storyRepository.findAll());
            return "postcraft/post";
        }

        Optional<Story> storyResult = storyRepository.findById(storyId);
        if (storyResult.isEmpty()) {
            // later add a default story that catches uncategorized chapters
        } else {
            Story story = storyResult.get();
            newChapter.setStory(story);
        }

        chapterRepository.save(newChapter);
        return "redirect:../";
    }
}
