package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.models.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("search2")
public class Search2Controller {

    @Autowired
    private ChapterRepository chapterRepository;

    static HashMap<String, String> typeChoices = new HashMap<>();

    public Search2Controller () {
        typeChoices.put("title", "Title");
        typeChoices.put("description", "Description");
        typeChoices.put("content", "Content");
        typeChoices.put("tags", "Tags");
    }

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("types", typeChoices);
        return "search2";
    }

    @PostMapping("")
    public String displaySearchResults(Model model, @RequestParam String  searchType, @RequestParam String searchTerm) {
        Iterable<Chapter> chapters;

        chapters = chapterRepository.findAll();

        model.addAttribute("types", typeChoices);
        model.addAttribute("chapters" ,chapters);

        return "search2";
    }
}
