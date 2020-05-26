package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("postcraft")
public class PostcraftController {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("post")
    public String renderCreateChapterForm(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute(new Chapter());
        model.addAttribute("tags", tagRepository.findAll());
        return "postcraft/post";
    }

    @PostMapping("post")
    public String processCreateChapterForm(@ModelAttribute @Valid Chapter newChapter, Errors errors, Model model,
                                           @RequestParam int storyId, @RequestParam List<Integer> tags) {

        if (errors.hasErrors()) {
            model.addAttribute("stories", storyRepository.findAll());
            model.addAttribute("tags", tagRepository.findAll());
            return "postcraft/post";
        }

        List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

        Optional<Story> storyResult = storyRepository.findById(storyId);
        if (storyResult.isEmpty()) {
            // later add a default story that catches uncategorized chapters
        } else {
            Story story = storyResult.get();
            newChapter.setStory(story);
        }

        for (Tag tag : tagObjs) {
            newChapter.addTag(tag);
        }

        chapterRepository.save(newChapter);
        return "redirect:../";
    }

}
