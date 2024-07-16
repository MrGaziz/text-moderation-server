package com.example.textmoderate.version2.controller;

import com.example.textmoderate.version2.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAPIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/moderate/openAI")
    public String moderateContent(@RequestBody String content) {
        return openAIService.moderateContent(content);
    }
}
