package org.lumijiez.monoalpha.util;

import java.util.HashMap;
import java.util.Map;

public class CharacterSwitcher {
    public static String applyRules(String rulesInput, String text) {
        Map<Character, Character> ruleMap = new HashMap<>();

        String[] rules = rulesInput.split(";");
        for (String rule : rules) {
            String[] parts = rule.split(">");
            if (parts.length != 2 || parts[0].trim().length() != 1 || parts[1].trim().length() != 1) {
                return "BAD RULE";
            }
            char from = parts[0].trim().charAt(0);
            char to = parts[1].trim().charAt(0);
            ruleMap.put(from, to);
        }

        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            char newChar = ruleMap.getOrDefault(c, c);
            result.append(newChar);
        }

        return result.toString();
    }
}
