package org.lumijiez.monoalpha;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.lumijiez.monoalpha.util.CharacterSwitcher;
import org.lumijiez.monoalpha.util.InputAnalyzer;
import org.lumijiez.monoalpha.util.PatternGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lumijiez.monoalpha.util.GlobalVars.RAINBOW;
import static org.lumijiez.monoalpha.util.PatternGenerator.matchPattern;

public class MainController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private TextArea inputArea;

    @FXML
    private TextArea ruleArea;

    @FXML
    private TextFlow outputArea;

    @FXML
    private TextField patternInput;

    @FXML
    private Button patternButton;

    @FXML
    private TextArea patternOutput;

    @FXML
    private CheckBox rainbowCheckbox;

    private final Map<String, List<String>> dictionaryMap = new HashMap<>();

    @FXML
    private void initialize() {
        analyzeText();
        PatternGenerator.loadDictionary(dictionaryMap, patternOutput);
        //barChart.setStyle("-fx-font-size: 18px;");
        inputArea.textProperty().addListener((observable, oldValue, newValue) -> {
            analyzeText();
            applyChanges(ruleArea.getText());
        });
        ruleArea.textProperty().addListener((observable, oldValue, newValue) -> applyChanges(newValue));
        patternButton.setOnAction(e -> matchPattern(patternInput.getText().trim(), patternOutput, dictionaryMap));
        rainbowCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            RAINBOW = newValue;
            analyzeText();
        });
    }

    @FXML
    private void analyzeText() {
        String input = inputArea.getText();
        Map<Character, Integer> frequencyMap = InputAnalyzer.analyzeFrequency(input);
        InputAnalyzer.updateBarChart(barChart, frequencyMap);
    }

    private void applyChanges(String input) {
        List<Text> textNodes = CharacterSwitcher.applyRules(input, inputArea.getText());
        outputArea.getChildren().clear();
        outputArea.getChildren().addAll(textNodes);
    }
}