package org.lumijiez.monoalpha.util;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterSwitcher {

    public static List<Text> applyRules(String rulesInput, String text) {
        Map<Character, Character> ruleMap = new HashMap<>();

        String[] rules = rulesInput.split(";");

        for (String rule : rules) {
            String[] parts = rule.split(">");
            if (parts.length != 2 || parts[0].trim().length() != 1 || parts[1].trim().length() != 1) {
                List<Text> errorText = new ArrayList<>();
                errorText.add(createErrorText());
                return errorText;
            }
            char from = parts[0].trim().charAt(0);
            char to = parts[1].trim().charAt(0);
            ruleMap.put(from, to);
        }

        text = text.replaceAll("\\r?\\n", "");

        List<Text> textNodes = new ArrayList<>();

        for (char c : text.toCharArray()) {
            char newChar = ruleMap.getOrDefault(c, c);
            Text textNode = new Text(newChar + "");

            if (c != newChar) {
                textNode.setFill(Color.RED);
            }

            textNodes.add(textNode);
        }

        return textNodes;
    }

    private static Text createErrorText() {
        Text errorText = new Text("BAD RULE");
        errorText.setFill(Color.RED);
        return errorText;
    }
}
