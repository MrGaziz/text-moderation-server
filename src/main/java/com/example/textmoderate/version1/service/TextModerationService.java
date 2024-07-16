package com.example.textmoderate.version1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TextModerationService {

    @Value("${perspective.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TextModerationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String translateToEnglish(String text) {
        // TODO Implement translation logic
        return text;
    }

    public String analyzeText(String text) {
        String url = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + apiKey;
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("TOXICITY", new HashMap<>());
        attributes.put("SEVERE_TOXICITY", new HashMap<>());
        attributes.put("PROFANITY", new HashMap<>());
        attributes.put("IDENTITY_ATTACK", new HashMap<>());
        attributes.put("INSULT", new HashMap<>());
        attributes.put("THREAT", new HashMap<>());
//        attributes.put("SEXUALLY_EXPLICIT", new HashMap<>());
//        attributes.put("FLIRTATION", new HashMap<>());

        data.put("comment", Map.of("text", text));
        data.put("languages", List.of("en", "ru"));
        data.put("requestedAttributes", attributes);

        ResponseEntity<String> response = restTemplate.postForEntity(url, data, String.class);
        return response.getBody();
    }

}
