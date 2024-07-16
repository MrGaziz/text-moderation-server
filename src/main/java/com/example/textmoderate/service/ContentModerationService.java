package com.example.textmoderate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ContentModerationService {

    private static final double THRESHOLD = 0.6;

    public String moderateContent(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode attributeScores = rootNode.path("attributeScores");

            // Iterate through each attribute
            for (JsonNode attribute : attributeScores) {
                String attributeName = attribute.fieldNames().next();
                JsonNode summaryScore = attribute.path("summaryScore");
                double value = summaryScore.path("value").asDouble();

                if (value > THRESHOLD) {
                    return String.format("Rejected: Contains inappropriate content due to %s.", attributeName);
                }
            }

            return "Approved";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to process the moderation result.";
        }
    }
}
