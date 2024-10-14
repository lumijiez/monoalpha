package org.lumijiez.monoalpha.util;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.lumijiez.monoalpha.util.GlobalVars.RAINBOW;

public class InputAnalyzer {

    private static final Map<String, Double> ENGLISH_FREQUENCIES = createEnglishFrequencies();
    private static final Random RANDOM = new Random();

    private static Map<String, Double> createEnglishFrequencies() {
        Map<String, Double> frequencies = new HashMap<>();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d",
                "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                "y", "z"};
        double[] frequenciesValues = {8.17, 1.49, 2.78, 4.25, 12.70, 2.23, 2.01, 6.09, 6.97, 0.15,
                0.77, 4.03, 2.41, 6.75, 7.51, 1.93, 0.09, 5.99, 6.33, 9.06,
                2.76, 0.98, 2.36, 0.15, 1.97, 0.07, 8.17, 1.49, 2.78, 4.25,
                12.70, 2.23, 2.01, 6.09, 6.97, 0.15, 0.77, 4.03, 2.41, 6.75,
                7.51, 1.93, 0.09, 5.99, 6.33, 9.06, 2.76, 0.98, 2.36, 0.15,
                1.97, 0.07};

        for (int i = 0; i < letters.length; i++) {
            frequencies.put(letters[i], frequenciesValues[i]);
        }
        return frequencies;
    }

    public static Map<Character, Integer> analyzeFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        text.chars()
                .filter(Character::isLetter)
                .forEach(ch -> frequencyMap.merge((char) ch, 1, Integer::sum));
        return frequencyMap;
    }

    public static List<XYChart.Data<String, Double>> getBarChartData(Map<Character, Integer> frequencyMap) {
        int totalCount = frequencyMap.values().stream().mapToInt(Integer::intValue).sum();
        return frequencyMap.entrySet().stream()
                .map(entry -> new XYChart.Data<>(entry.getKey().toString(), (entry.getValue() / (double) totalCount) * 100))
                .collect(Collectors.toList());
    }

    public static List<XYChart.Data<String, Double>> getStaticEnglishFrequencies() {
        return ENGLISH_FREQUENCIES.entrySet().stream()
                .map(entry -> new XYChart.Data<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static void updateBarChart(BarChart<String, Number> barChart, Map<Character, Integer> frequencyMap) {
        barChart.getData().clear();

        List<XYChart.Data<String, Double>> actualData = getBarChartData(frequencyMap);
        List<XYChart.Data<String, Double>> englishData = getStaticEnglishFrequencies();

        barChart.getData().add(createSeries("Actual Frequency", actualData, true)); // Random colors
        barChart.getData().add(createSeries("English Frequency", englishData, false)); // Constant color

        setTooltips(barChart.getData().get(0));
        setTooltips(barChart.getData().get(1));
    }

    private static XYChart.Series<String, Number> createSeries(String name, List<XYChart.Data<String, Double>> data, boolean randomizeColor) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);
        for (XYChart.Data<String, Double> datum : data) {
            XYChart.Data<String, Number> barData = new XYChart.Data<>(datum.getXValue(), datum.getYValue());
            barData.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (RAINBOW && randomizeColor && newNode != null) {
                    String color = randomColor();
                    newNode.setStyle("-fx-bar-fill: " + color + ";");
                }
            });
            series.getData().add(barData);
        }
        setTooltips(series);
        return series;
    }

    private static String randomColor() {
        int r = RANDOM.nextInt(256);
        int g = RANDOM.nextInt(256);
        int b = RANDOM.nextInt(256);
        return String.format("rgb(%d,%d,%d)", r, g, b);
    }

    private static void setTooltips(XYChart.Series<String, Number> series) {
        for (XYChart.Data<String, Number> datum : series.getData()) {
            String tooltipText = String.format("%s: %.2f%%", series.getName(), (Double) datum.getYValue());
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setHideDelay(Duration.ZERO);
            Tooltip.install(datum.getNode(), tooltip);
        }
    }
}
