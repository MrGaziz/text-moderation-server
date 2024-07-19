package com.example.textmoderate.version2.controller;

import com.example.textmoderate.version2.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAPIController {

    private final OpenAIService openAIService;

    public OpenAPIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/moderate/openAI")
    public String moderateContent(@RequestBody String content) {
        return openAIService.moderateContent(content);
    }
}
