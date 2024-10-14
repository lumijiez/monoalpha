package org.lumijiez.monoalpha.util;

import javafx.scene.control.TextArea;
import org.lumijiez.monoalpha.MainController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternGenerator {

    public static String generatePattern(String word) {
        StringBuilder pattern = new StringBuilder();
        char nextLower = 'a';
        char nextUpper = 'A';
        Map<Character, Character> charMap = new HashMap<>();

        for (char c : word.toCharArray()) {
            if (Character.isLowerCase(c)) {
                if (!charMap.containsKey(c)) {
                    charMap.put(c, nextLower++);
                }
                pattern.append(charMap.get(c));
            } else if (Character.isUpperCase(c)) {
                if (!charMap.containsKey(c)) {
                    charMap.put(c, nextUpper++);
                }
                pattern.append(charMap.get(c));
            } else {
                pattern.append(c);
            }
        }
        return pattern.toString();
    }

    public static void loadDictionary(Map<String, List<String>> dictionaryMap, TextArea textArea) {

        try (InputStream is = MainController.class.getResourceAsStream("/org/lumijiez/monoalpha/dictionary.txt")) {
            assert is != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 2) {
                        String pattern = parts[0];
                        String word = parts[1];
                        dictionaryMap.computeIfAbsent(pattern, k -> new ArrayList<>()).add(word);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            textArea.setText("Error loading dictionary");
        }
    }

    public static void matchPattern(String inputWord, TextArea patternOutput, Map<String, List<String>> dictionaryMap) {

        if (inputWord.isEmpty()) {
            patternOutput.setText("Please enter a word.");
            return;
        }

        String inputPattern = generatePattern(inputWord);

        List<String> matchingWords = dictionaryMap.getOrDefault(inputPattern, new ArrayList<>());

        if (matchingWords.isEmpty()) {
            patternOutput.setText("No matching words found.");
        } else {
            patternOutput.setText(String.join("\n", matchingWords));
        }
    }

}
