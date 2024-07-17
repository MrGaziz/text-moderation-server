package com.example.textmoderate.version3.service;

import com.example.textmoderate.version3.model.BadWord;
import com.example.textmoderate.version3.repository.BadWordRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class BadWordsService {

    @Autowired
    private BadWordRepository badWordRepository;

    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public String moderateText(String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            for (BadWord badWord : badWordRepository.findAll()) {
                int threshold = (int) (badWord.getWord().length() * 0.2); // Порог в 20% от длины слова
                if (levenshteinDistance.apply(word.toLowerCase(), badWord.getWord().toLowerCase()) <= threshold) {
                    return "Reject";
                }
            }
        }
        return "Approve";
    }

    public String importBadWords(InputStream fileStream) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(fileStream);
            List<String> words = mapper.convertValue(
                    jsonNode.get("words"),
                    new TypeReference<>() {
                    }
            );

            words.forEach(word -> {
                if (!badWordRepository.existsByWord(word)) {
                    badWordRepository.save(new BadWord(null, word));
                }
            });
            return "Words processed: " + words.size();
        } catch (Exception e) {
            return "Error processing file: " + e.getMessage();
        }
    }
}
