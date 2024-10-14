package org.lumijiez.monoalpha;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.lumijiez.monoalpha.util.CharacterSwitcher;
import org.lumijiez.monoalpha.util.InputAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private TextArea inputArea;

    @FXML
    private TextArea ruleArea;

    @FXML
    private TextArea outputArea;

    @FXML
    private TextField patternInput;

    @FXML
    private Button patternButton;

    @FXML
    private TextArea patternOutput;

    private Map<String, List<String>> dictionaryMap;

    @FXML
    private void initialize() {
        analyzeText();
        loadDictionary();
        barChart.setStyle("-fx-font-size: 18px;");
        inputArea.textProperty().addListener((observable, oldValue, newValue) -> {
            analyzeText();
            applyChanges();
        });
        ruleArea.textProperty().addListener((observable, oldValue, newValue) -> applyChanges());
        patternButton.setOnAction(e -> matchPattern());
    }

    @FXML
    private void analyzeText() {
        String inputText = inputArea.getText();
        Map<Character, Integer> frequencyMap = InputAnalyzer.analyzeFrequency(inputText);
        List<XYChart.Data<String, Integer>> barChartData = InputAnalyzer.getBarChartData(frequencyMap);
        InputAnalyzer.updateBarChart(barChart, barChartData);
    }

    private void applyChanges() {
        String rules = ruleArea.getText();
        String changedText = CharacterSwitcher.applyRules(rules, inputArea.getText());
        outputArea.setText(changedText);
    }

    private void loadDictionary() {
        dictionaryMap = new HashMap<>();

        try (InputStream is = getClass().getResourceAsStream("/org/lumijiez/monoalpha/dictionary.txt")) {
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
            e.printStackTrace();
            patternOutput.setText("Error loading dictionary");
        }
    }

    private void matchPattern() {
        String inputWord = patternInput.getText().trim();
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

    private String generatePattern(String word) {
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
}