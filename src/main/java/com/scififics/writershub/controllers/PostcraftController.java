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

    @GetMapping("postview/{chapterId}")
    public String viewPostview(Model model, @PathVariable int chapterId) {

        Optional optChapter = chapterRepository.findById(chapterId);

        if (optChapter.isPresent()) {
            Chapter chapter = (Chapter) optChapter.get();
            model.addAttribute("chapter", chapter);
            return "postcraft/postview";
        } else {
            return "redirect:../";
        }
    }

    @GetMapping("posthub")
    public String viewPosthub(Model model) {
        model.addAttribute("chapters", chapterRepository.findAll());
        return "postcraft/posthub";
    }

    @PostMapping("posthub")
    public String processPosthubForm(Model model, @RequestParam(required = false) int[] chapterId) {

        /*
        This is a special kind of cheating.
        I made chapterId an array so I could check if it was null because it is primitive and
        I need a way to check if it exists. It works so I'm leaving it alone.
        */
        if (chapterId != null) {
            for (int id : chapterId) {
                chapterRepository.deleteById(id);
            }
        }

        model.addAttribute("chapters", chapterRepository.findAll());
        return "postcraft/posthub";
    }
}
