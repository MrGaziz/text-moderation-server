package com.example.textmoderate.version4.controller;

import com.example.textmoderate.version4.model.ModerationResponse;
import com.example.textmoderate.version4.model.ModerationResult;
import com.example.textmoderate.version4.service.ModerationFacadeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/sms")
public class TextModerationController {

    private final ModerationFacadeService moderationFacadeService;

    public TextModerationController(ModerationFacadeService moderationFacadeService) {
        this.moderationFacadeService = moderationFacadeService;
    }

    @PostMapping("/moderate")
    public ModerationResponse moderateMessage(@RequestHeader("Authorization") String token,
                                              @RequestHeader("Accept-Language") String language,
                                              @RequestBody Map<String, String> request) {
        String msisdn = extractMsisdnFromToken(token);
        String textMessage = request.get("text_message");

        ModerationResult result = moderationFacadeService.moderateTextMessage(msisdn, textMessage);
        return new ModerationResponse(result);
    }


    private String extractMsisdnFromToken(String token) {
        // Extract MSISDN from the token (implementation depends on token structure of Yelaman's project)
        return "87077777777"; // Example
    }
}

