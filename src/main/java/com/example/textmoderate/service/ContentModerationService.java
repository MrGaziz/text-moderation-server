package com.example.textmoderate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class ContentModerationService {

    private static final double THRESHOLD = 0.6;
    private static final Logger log = LoggerFactory.getLogger(ContentModerationService.class);

    public String moderateContent(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode attributeScores = rootNode.path("attributeScores");

            // Итерация по каждому атрибуту
            Iterator<Map.Entry<String, JsonNode>> fields = attributeScores.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String attributeName = field.getKey(); // Название атрибута
                JsonNode summaryScore = field.getValue().path("summaryScore");
                double value = summaryScore.path("value").asDouble();

                if (value > THRESHOLD) {
                    return String.format("Rejected: Contains inappropriate content due to %s.", attributeName);
                }
            }

            return "Approved";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error: Unable to process the moderation result.";
        }
    }
}
