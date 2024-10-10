package org.lumijiez.monoalpha;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InputAnalyzer {

    public static Map<Character, Integer> analyzeFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
            }
        }
        return frequencyMap;
    }

    public static List<XYChart.Data<String, Integer>> getBarChartData(Map<Character, Integer> frequencyMap) {
        return frequencyMap.entrySet().stream()
                .map(entry -> new XYChart.Data<>(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }

    static void updateBarChart(BarChart<String, Integer> barChart, List<XYChart.Data<String, Integer>> data) {
        barChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (XYChart.Data<String, Integer> datum : data) {
            series.getData().add(new XYChart.Data<>(datum.getXValue(), datum.getYValue()));
        }

        barChart.getXAxis().setAnimated(false);
        barChart.getData().add(series);

        for (XYChart.Data<String, Integer> datum : series.getData()) {
            Tooltip tooltip = new Tooltip("Frequency: " + datum.getYValue());

            tooltip.setShowDelay(new Duration(0));
            tooltip.setHideDelay(new Duration(0));

            Tooltip.install(datum.getNode(), tooltip);
        }
    }
}

