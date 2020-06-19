package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("createspace/chapterspace")
public class ChapterspaceController {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("chapterhub")
    public String viewChapterhub(Model model) {
        model.addAttribute("chapters", chapterRepository.findAll());
        model.addAttribute("pageTitle", "All Chapters");
        return "createspace/chapterspace/chapterhub";
    }

    @PostMapping("chapterhub")
    public String processChapterhubForm(Model model, @RequestParam String buttonFunction,
                                        @RequestParam int chapterId) {

        if (buttonFunction.equals("delete")) {
            chapterRepository.deleteById(chapterId);
        }

        model.addAttribute("chapters", chapterRepository.findAll());
        model.addAttribute("pageTitle", "All Chapters");
        return "createspace/chapterspace/chapterhub";
    }
}
