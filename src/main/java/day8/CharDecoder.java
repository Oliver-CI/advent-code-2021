package day8;

import lombok.Getter;
import util.IterativeSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class CharDecoder implements IterativeSolver {
    public static final String SPACE = " ";
    private long uniqueDigits = 0;
    private HashMap<String, String> patternMap = new HashMap<>();

    @Override
    public void iterate(String line) {
        final String[] stringParts = line.split("\\|");
        final List<String> patternDigits = Arrays.stream(stringParts[0].trim().split(SPACE)).toList();
        //set defaults
        var multiValPatterns = new ArrayList<String>();
        patternDigits.forEach(pattern -> {
            switch (pattern.length()) {
                case 2 -> patternMap.put(pattern, "1");
                case 3 -> patternMap.put(pattern, "7");
                case 4 -> patternMap.put(pattern, "4");
                case 7 -> patternMap.put(pattern, "8");
                default -> multiValPatterns.add(pattern);
            }
        });
        var fiveAndTwo = new ArrayList<String>();
        multiValPatterns.forEach(pattern -> {
            final Map.Entry<String, String> entry4 = patternMap.entrySet().stream().filter(e -> Objects.equals(e.getValue(), "4")).findFirst().get();
            final Map.Entry<String, String> entry7 = patternMap.entrySet().stream().filter(e -> Objects.equals(e.getValue(), "7")).findFirst().get();
            if (pattern.length() == 5) {
                if (checkIfAllCharInclude(entry7.getKey(), pattern)) {
                    patternMap.put(pattern, "3");
                } else {
                    fiveAndTwo.add(pattern);
                }
            } else if (pattern.length() == 6) {
                if (checkIfAllCharInclude(entry7.getKey(), pattern)) {
                    if (checkIfAllCharInclude(entry4.getKey(), pattern)) {
                        patternMap.put(pattern, "9");
                    } else patternMap.put(pattern, "0");
                } else {
                    patternMap.put(pattern, "6");
                }
            }
        });
        fiveAndTwo.forEach(pattern -> {
            final Map.Entry<String, String> entry6 = patternMap.entrySet().stream().filter(e -> Objects.equals(e.getValue(), "6")).findFirst().get();
            final Map.Entry<String, String> entry8 = patternMap.entrySet().stream().filter(e -> Objects.equals(e.getValue(), "8")).findFirst().get();
            char upperRightChar = 'z';
            for (char c : entry8.getKey().toCharArray()) {
                if (entry6.getKey().indexOf(c) == -1) {
                    upperRightChar = c;
                }
            }
            if (pattern.indexOf(upperRightChar) == -1) {
                patternMap.put(pattern, "5");
            } else patternMap.put(pattern, "2");
        });


        final String digits = Arrays.stream(stringParts[1].trim().split(SPACE)).map(this::decode).collect(Collectors.joining());
        final int entryValue = Integer.parseInt(digits);
        System.out.println(entryValue);
        uniqueDigits += entryValue;
        patternMap = new HashMap<>();
    }

    private String decode(final String codedString) {
        return patternMap.entrySet().stream().filter(e -> e.getKey().length() == codedString.length()).filter(e -> checkIfAllCharInclude(e.getKey(), codedString)).map(Map.Entry::getValue).findFirst().orElse("");
    }

    private boolean checkIfAllCharInclude(final String keyCode, String codedString) {
        for (char c : keyCode.toCharArray()) {
            if (codedString.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

}
