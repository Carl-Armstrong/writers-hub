package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.models.Chapter;
import com.scififics.writershub.models.ChapterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private ChapterRepository chapterRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public SearchController () {
        columnChoices.put("all", "All");
        columnChoices.put("chapter", "Chapter");
        columnChoices.put("story", "Story");
        columnChoices.put("world", "World");
    }

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping("")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        Iterable<Chapter> chapters;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            chapters = chapterRepository.findAll();
        } else {
            chapters = ChapterData.findByColumnAndValue(searchType, searchTerm, chapterRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("chapters", chapters);

        return "search";
    }
}
