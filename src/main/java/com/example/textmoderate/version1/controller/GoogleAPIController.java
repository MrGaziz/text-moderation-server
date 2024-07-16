package com.example.textmoderate.version1.controller;

import com.example.textmoderate.version1.service.ContentModerationService;
import com.example.textmoderate.version1.service.TextModerationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleAPIController {

    private final TextModerationService textModerationService;
    private final ContentModerationService contentModerationService;

    public GoogleAPIController(TextModerationService textModerationService, ContentModerationService contentModerationService) {
        this.textModerationService = textModerationService;
        this.contentModerationService = contentModerationService;
    }

    @PostMapping("/moderate/googleApi")
    public String moderateMessage(@RequestParam("content") String content) {
        String translatedContent = textModerationService.translateToEnglish(content);
        String analysisResult = textModerationService.analyzeText(translatedContent);
        return contentModerationService.moderateContent(analysisResult);
    }

}
