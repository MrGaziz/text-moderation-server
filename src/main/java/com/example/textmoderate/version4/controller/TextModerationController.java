package com.example.textmoderate.version4.controller;

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
    public Map<String, Object> moderateMessage(@RequestHeader("Authorization") String token,
                                               @RequestHeader("Accept-Language") String language,
                                               @RequestBody Map<String, String> request) {
        String msisdn = extractMsisdnFromToken(token);
        String textMessage = request.get("text_message");

        String reason = moderationFacadeService.moderateTextMessage(msisdn, textMessage);
        boolean isApproved = "accept".equals(reason);

        return Map.of(
                "data", Map.of(
                        "is_approved", isApproved,
                        "reason", isApproved ? null : reason
                )
        );
    }

    private String extractMsisdnFromToken(String token) {
        // Extract MSISDN from the token (implementation depends on token structure of Yelaman's project)
        return "87077777777"; // Example
    }
}

