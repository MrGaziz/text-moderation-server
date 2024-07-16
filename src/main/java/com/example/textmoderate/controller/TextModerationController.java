package com.example.textmoderate.controller;

import com.example.textmoderate.service.ContentModerationService;
import com.example.textmoderate.service.TextModerationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextModerationController {

    private final TextModerationService textModerationService;
    private final ContentModerationService contentModerationService;

    public TextModerationController(TextModerationService textModerationService, ContentModerationService contentModerationService) {
        this.textModerationService = textModerationService;
        this.contentModerationService = contentModerationService;
    }

    @PostMapping("/moderate")
    public String moderateMessage(@RequestParam("content") String content) {
        String translatedContent = textModerationService.translateToEnglish(content);
        String analysisResult = textModerationService.analyzeText(translatedContent);
        return contentModerationService.moderateContent(analysisResult);
    }

}
