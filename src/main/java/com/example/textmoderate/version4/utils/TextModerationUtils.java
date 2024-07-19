package com.example.textmoderate.version4.utils;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TextModerationUtils {

    private static final LevenshteinDistance levenshtein = new LevenshteinDistance();
    private static final int LEVENSHTEIN_THRESHOLD = 5; // Максимальное допустимое расстояние Левенштейна

    private static final Map<String, Integer> CATEGORY_MAP = new HashMap<>();

    static {
        CATEGORY_MAP.put("profanity", 2);
        CATEGORY_MAP.put("toxicity", 3);
        CATEGORY_MAP.put("threat", 4);
        CATEGORY_MAP.put("propaganda", 5);
        CATEGORY_MAP.put("agitation", 6);
        CATEGORY_MAP.put("fraud", 7);
        CATEGORY_MAP.put("sexual_explicit", 8);
    }

    public static int mapRejectionReasonToStatus(String reason) {
        if ("approve".equalsIgnoreCase(reason)) {
            return 1; // 1: accepted
        }

        // Убираем слово "reject" и все не-буквенные символы
        String sanitizedReason = reason.replaceAll("reject", "").replaceAll("[^a-zA-Z ]", "").toLowerCase().trim();

        // Разбиваем на слова
        String[] words = sanitizedReason.split("\\s+");

        for (String word : words) {
            String closestMatch = findClosestCategoryMatch(word);
            if (!"unknown".equals(closestMatch)) {
                return CATEGORY_MAP.getOrDefault(closestMatch, 9); // 9: unknown reason
            }
        }
        return 9; // unknown reason
    }

    private static String findClosestCategoryMatch(String category) {
        Set<String> categories = CATEGORY_MAP.keySet();

        if (categories.contains(category)) {
            return category;
        }

        String closestMatch = null;
        int minDistance = Integer.MAX_VALUE;

        for (String key : categories) {
            int distance = levenshtein.apply(category, key);
            if (distance < minDistance && distance <= LEVENSHTEIN_THRESHOLD) {
                minDistance = distance;
                closestMatch = key;
            }
        }

        return closestMatch != null ? closestMatch : "unknown";
    }
}



