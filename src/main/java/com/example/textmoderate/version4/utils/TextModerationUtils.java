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
        CATEGORY_MAP.put("severe_toxicity", 4);
        CATEGORY_MAP.put("threat", 5);
        CATEGORY_MAP.put("propaganda", 6);
        CATEGORY_MAP.put("agitation", 7);
        CATEGORY_MAP.put("fraud", 8);
        CATEGORY_MAP.put("sexual_explicit", 9);
        CATEGORY_MAP.put("insult", 10);
        CATEGORY_MAP.put("identity_attack", 11);
    }

    public static int mapRejectionReasonToStatus(String reason, boolean useExactMatch) {
        if ("accept".equalsIgnoreCase(reason)) {
            return 1; // 1: accept
        }

        // Убираем слово "reject" и все не-буквенные символы
        String sanitizedReason = reason.replaceAll("reject", "").replaceAll("[^a-zA-Z ]", "").toLowerCase().trim();

        // Разбиваем на слова
        String[] words = sanitizedReason.split("\\s+");

        for (String word : words) {
            if (useExactMatch) {
                // Проверка точного соответствия
                if (CATEGORY_MAP.containsKey(word)) {
                    return CATEGORY_MAP.get(word);
                }
            } else {
                // Проверка ближайшего соответствия
                String closestMatch = findClosestCategoryMatch(word);
                if (!"unknown".equals(closestMatch)) {
                    return CATEGORY_MAP.getOrDefault(closestMatch, 12); // 12: unknown reason
                }
            }
        }
        return 12; // unknown reason
    }

    private static String findClosestCategoryMatch(String category) {
        Set<String> categories = CATEGORY_MAP.keySet();

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




