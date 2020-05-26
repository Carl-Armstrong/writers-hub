package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.data.StoryRepository;
import com.scififics.writershub.data.TagRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.Story;
import com.scififics.writershub.models.Tag;
import com.scififics.writershub.models.dto.ChapterTagDTO;
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

    @Autowired
    private TagRepository tagRepository;

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

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer chapterId, Model model){
        Optional<Chapter> result = chapterRepository.findById(chapterId);
        Chapter chapter = result.get();
        model.addAttribute("tags", tagRepository.findAll());
        ChapterTagDTO chapterTag = new ChapterTagDTO();
        chapterTag.setChapter(chapter);
        model.addAttribute("chapterTag", chapterTag);

        return "postcraft/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid ChapterTagDTO chapterTag,
                                    Errors errors, Model model) {

        if (!errors.hasErrors()) {
            Chapter chapter = chapterTag.getChapter();
            Tag tag = chapterTag.getTag();
            if (chapter.getTags().contains(tag)){
                chapter.addTag(tag);
                chapterRepository.save(chapter);
            }

        }

        return "redirect:/add-tag";

    }
}
