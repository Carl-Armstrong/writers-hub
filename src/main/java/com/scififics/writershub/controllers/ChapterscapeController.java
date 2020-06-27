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
@RequestMapping("createspace/chapterscape")
public class ChapterscapeController {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("")
    public String viewIndex(Model model) {
        model.addAttribute("chapters", chapterRepository.findAll());
        model.addAttribute("pageTitle", "All Chapters");
        return "createspace/chapterscape/index";
    }

    @PostMapping("")
    public String processIndexForm(Model model, @RequestParam String buttonFunction,
                                        @RequestParam int chapterId) {

        if (buttonFunction.equals("delete")) {
            chapterRepository.deleteById(chapterId);
        }

        model.addAttribute("chapters", chapterRepository.findAll());
        model.addAttribute("pageTitle", "All Chapters");
        return "createspace/chapterscape/index";
    }

    @GetMapping("chapterbuilder")
    public String renderCreateChapterForm(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("pageTitle", "Create New Chapter");
        model.addAttribute(new Chapter());
        return "createspace/chapterscape/chapterbuilder";
    }

    @PostMapping("chapterbuilder")
    public String processCreateChapterForm(@ModelAttribute @Valid Chapter newChapter, Errors errors,
                                           Model model, @RequestParam int storyId,
                                           @RequestParam(required = false) List<Integer> tags) {

        if (errors.hasErrors()) {
            model.addAttribute("stories", storyRepository.findAll());
            model.addAttribute("tags", tagRepository.findAll());
            model.addAttribute("pageTitle", "Create New Chapter");
            return "createspace/chapterscape/chapterbuilder";
        }

        if (tags != null) {
            List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

            for (Tag tag : tagObjs) {
                newChapter.addTag(tag);
            }
        }

        Optional<Story> storyResult = storyRepository.findById(storyId);
        if (storyResult.isEmpty()) {
            // later add a default story that catches uncategorized chapters
        } else {
            Story story = storyResult.get();
            newChapter.setStory(story);
        }

        chapterRepository.save(newChapter);
        return "redirect:../../chapterview/chapterviewer/" + newChapter.getId();
    }

    @GetMapping("chapterbreaker/{chapterId}")
    public String renderEditChapterForm(Model model, @PathVariable int chapterId) {


        Optional optChapter = chapterRepository.findById(chapterId);

        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());

        if (optChapter.isPresent()) {
            Chapter chapter = (Chapter) optChapter.get();
            model.addAttribute("pageTitle", "Editing " + chapter.getTitle());
            model.addAttribute("chapter", chapter);
            return "createspace/chapterscape/chapterbreaker";
        } else {
            return "redirect:../createspace/chapterscape/index/";
        }
    }

    @PostMapping("chapterbreaker")
    public String processEditChapterForm(@ModelAttribute @Valid Chapter editedChapter, Errors errors, Model model,
                                         @RequestParam int storyId, @RequestParam(required = false) List<Integer> tags,
                                         @RequestParam int chapterId) {

        if (errors.hasErrors()) {
            model.addAttribute("chapters", chapterRepository.findAll());
            return "redirect:../createspace/chapterscape/chapterbreaker/" + chapterId;
        }

        Optional optChapter = chapterRepository.findById(chapterId);

        if (optChapter.isPresent()) {
            Chapter chapter = (Chapter) optChapter.get();

            chapter.setTitle(editedChapter.getTitle());
            chapter.setDescription(editedChapter.getDescription());
            chapter.setContent(editedChapter.getContent());

            if (tags != null) {
                List<Tag> tagObjs = (List<Tag>) tagRepository.findAllById(tags);

                for (Tag tag : tagObjs) {
                    chapter.addTag(tag);
                }
            }

            Optional<Story> storyResult = storyRepository.findById(storyId);
            if (storyResult.isEmpty()) {
                // later add a default story that catches uncategorized chapters
            } else {
                Story story = storyResult.get();
                chapter.setStory(story);
            }

            chapterRepository.save(chapter);
            return "redirect:../createspace/chapterscape/chapterbreaker" + chapterId;

        } else {
            return "redirect:../createspace/chapterscape/index/";
        }
    }
}
