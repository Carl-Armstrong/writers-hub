package com.scififics.writershub.controllers;

import com.scififics.writershub.data.ChapterData;
import com.scififics.writershub.data.ChapterRepository;
import com.scififics.writershub.models.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ChapterRepository chapterRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("chapters", chapterRepository.findAll());
        return "index";
    }


}
