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
                                           @RequestParam int storyId, @RequestParam(required = false) List<Integer> tags) {

        if (errors.hasErrors()) {
            model.addAttribute("stories", storyRepository.findAll());
            model.addAttribute("tags", tagRepository.findAll());
            return "postcraft/post";
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
    public String processPosthubForm(Model model, @RequestParam String buttonFunction, @RequestParam int chapterId) {

        if (buttonFunction.equals("delete")) {
            chapterRepository.deleteById(chapterId);
        } else if (buttonFunction.equals("edit")) {

        }

        model.addAttribute("chapters", chapterRepository.findAll());
        return "postcraft/posthub";
    }

    @GetMapping("edit/{chapterId}")
    public String renderEditChapterForm(Model model, @PathVariable int chapterId) {


        Optional optChapter = chapterRepository.findById(chapterId);

        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());

        if (optChapter.isPresent()) {
            Chapter chapter = (Chapter) optChapter.get();
            model.addAttribute("chapter", chapter);
            return "postcraft/edit";
        } else {
            return "redirect:../";
        }

    }

    @PostMapping("edit")
    public String processEditChapterForm(@ModelAttribute @Valid Chapter editedChapter, Errors errors, Model model,
                                         @RequestParam int storyId, @RequestParam(required = false) List<Integer> tags,
                                         @RequestParam int chapterId) {

        if (errors.hasErrors()) {
            model.addAttribute("chapters", chapterRepository.findAll());
            return "postcraft/posthub";
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
            return "redirect:../postcraft/edit/" + chapterId;

        } else {
            return "redirect:../";
        }




    }
}
