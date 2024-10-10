package org.lumijiez.monoalpha;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Map;

public class HelloController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private TextArea inputArea;

    @FXML
    private void initialize() {
         analyzeText();
    }

    @FXML
    private void analyzeText() {
        String inputText = inputArea.getText();
        Map<Character, Integer> frequencyMap = InputAnalyzer.analyzeFrequency(inputText);
        List<XYChart.Data<String, Integer>> barChartData = InputAnalyzer.getBarChartData(frequencyMap);
        updateBarChart(barChart, barChartData);
    }

    private void updateBarChart(BarChart<String, Integer> barChart, List<XYChart.Data<String, Integer>> data) {
        barChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Letter Frequencies");

        for (XYChart.Data<String, Integer> datum : data) {
            series.getData().add(new XYChart.Data<>(datum.getXValue(), datum.getYValue()));
        }

        barChart.getData().add(series);
    }
}