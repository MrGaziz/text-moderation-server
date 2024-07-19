package com.example.textmoderate.version3.controller;

import com.example.textmoderate.version3.service.BadWordsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TextModerationV3Controller {

    private final BadWordsService badWordsService;

    public TextModerationV3Controller(BadWordsService badWordsService) {
        this.badWordsService = badWordsService;
    }

    @PostMapping("/moderate-text")
    public String moderateText(@RequestParam("content") String text) {
        return badWordsService.moderateText(text);
    }

    @PostMapping("/upload-bad-words")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "Please upload a valid file.";
        }

        return badWordsService.importBadWords(file.getInputStream());
    }
}

