package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.models.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("chapterview")
public class ChapterviewController {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("chapterlist")
    public String viewChapterlist(Model model) {
        model.addAttribute("chapters", chapterRepository.findAll());
        model.addAttribute("pageTitle", "List of Chapters");
        return "chapterview/chapterlist";
    }

    @GetMapping("chapterviewer/{chapterId}")
    public String viewChapterviewer(Model model, @PathVariable int chapterId) {

        Optional optChapter = chapterRepository.findById(chapterId);

        if (optChapter.isPresent()) {
            Chapter chapter = (Chapter) optChapter.get();
            model.addAttribute("chapter", chapter);
            model.addAttribute("pageTitle", chapter.getTitle());
            return "chapterview/chapterviewer";
        } else {
            return "redirect:../chapterview/chapterlist";
        }
    }
}
