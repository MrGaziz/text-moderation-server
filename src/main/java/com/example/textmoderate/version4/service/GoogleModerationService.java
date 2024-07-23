package com.example.textmoderate.version4.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class GoogleModerationService {
    @Value("${perspective.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private static final double THRESHOLD = 0.70;
    private static final Logger log = LoggerFactory.getLogger(GoogleModerationService.class);

    public GoogleModerationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String analyzeTextWithGoogle(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode attributeScores = rootNode.path("attributeScores");

            // Итерация по каждому атрибуту
            Iterator<Map.Entry<String, JsonNode>> fields = attributeScores.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String attributeName = field.getKey();
                JsonNode summaryScore = field.getValue().path("summaryScore");
                double value = summaryScore.path("value").asDouble();

                if (value > THRESHOLD) {
                    return String.format("reject %s", attributeName.toLowerCase());
                }
            }

            return "accept";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error: Unable to process the moderation result.";
        }
    }

    public String analyzeText(String text) {
        String url = MessageFormat.format("https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key={0}", apiKey);
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("TOXICITY", new HashMap<>());
        attributes.put("SEVERE_TOXICITY", new HashMap<>());
        attributes.put("PROFANITY", new HashMap<>());
        attributes.put("IDENTITY_ATTACK", new HashMap<>());
        attributes.put("INSULT", new HashMap<>());
        attributes.put("THREAT", new HashMap<>());

        data.put("comment", Map.of("text", text));
        data.put("languages", List.of("en", "ru"));
        data.put("requestedAttributes", attributes);

        ResponseEntity<String> response = restTemplate.postForEntity(url, data, String.class);
        return response.getBody();
    }
}
