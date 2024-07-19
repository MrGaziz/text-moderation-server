package com.example.textmoderate.version4.service;


import com.example.textmoderate.version4.model.ProhibitedWord;
import com.example.textmoderate.version4.repository.ProhibitedWordRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

@Service
public class DatabaseModerationService {

    private final ProhibitedWordRepository prohibitedWordRepository;
    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public DatabaseModerationService(ProhibitedWordRepository prohibitedWordRepository) {
        this.prohibitedWordRepository = prohibitedWordRepository;
    }

    public String checkTextAgainstDatabase(String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            for (ProhibitedWord badWord : prohibitedWordRepository.findAll()) {
                int threshold = (int) (badWord.getWord().length() * 0.3); // Порог в 20% от длины слова
                if (levenshteinDistance.apply(word.toLowerCase(), badWord.getWord().toLowerCase()) <= threshold) {
                    return "reject";
                }
            }
        }
        return "accept";
    }
}
