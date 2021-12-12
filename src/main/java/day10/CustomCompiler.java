package day10;

import util.IterativeSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomCompiler implements IterativeSolver {
    public static final String EMPTY = "";
    public static final String BACKSLASH = "\\";
    private final Map<CustomPattern, Integer> corruptedEntries = new HashMap<>();
    private final List<Long> completionList = new ArrayList<>();


    @Override
    public void iterate(String line) {
        boolean replacementPossible;
        do {
            for (CustomPattern pattern : CustomPattern.values()) {
                line = line.replaceAll(pattern.getFull(), EMPTY);
            }
            final String checkLine = line;
            replacementPossible = Arrays.stream(CustomPattern.values()).anyMatch(p -> checkLine.contains(p.start + p.end));
        } while (replacementPossible);
        final String checkLine = line;

        final boolean isEnded = CustomPattern.endValues().stream().anyMatch(checkLine::contains);
        if (isEnded) {
            String onlyErrors = checkLine;
            for (String startValue : CustomPattern.startValues()) {
                onlyErrors = onlyErrors.replaceAll(BACKSLASH + startValue, EMPTY);
            }
            final String error = onlyErrors.substring(0, 1);
            final CustomPattern patternByChar = CustomPattern.getPatternByChar(error);
            addEntry(corruptedEntries, patternByChar, 1);
        } else {
            var completion = new ArrayList<CustomPattern>();
            for (String s : checkLine.split(EMPTY)) {
                completion.add(CustomPattern.getPatternByChar(s));
            }
            Collections.reverse(completion);
            long total = 0;
            for (CustomPattern customPattern : completion) {
                total *= 5;
                total += customPattern.completionScore;
            }
            completionList.add(total);
        }
    }

    public void addEntry(Map<CustomPattern, Integer> map, final CustomPattern pattern, final int value) {
        if (map.containsKey(pattern)) {
            map.put(pattern, map.get(pattern) + value);
        } else {
            map.put(pattern, value);
        }
    }


    public long getSyntaxErrorScore() {
        return corruptedEntries.entrySet().stream()
                .map(entry -> entry.getValue() * entry.getKey().errorScore)
                .reduce(0, Integer::sum);
    }

    public long getCompletionScore() {
        completionList.sort(Comparator.reverseOrder());
        System.out.println(completionList);
        final int middleIndex = (completionList.size() / 2);
        return completionList.get(middleIndex);
    }

    enum CustomPattern {
        CURLY("{", "}", 1197, 3),
        ROUND("(", ")", 3, 1),
        DIAMOND("<", ">", 25137, 4),
        BLOCK("[", "]", 57, 2);

        private final String start;
        private final String end;
        private final int errorScore;
        private final int completionScore;

        CustomPattern(String start, String end, int errorScore, int completionScore) {
            this.start = start;
            this.end = end;
            this.errorScore = errorScore;
            this.completionScore = completionScore;
        }

        public String getFull() {
            return BACKSLASH + start + BACKSLASH + end;
        }

        public static CustomPattern getPatternByChar(final String character) {
            return Arrays.stream(CustomPattern.values()).filter(p -> p.start.equals(character) || p.end.equals(character)).findFirst().orElseThrow(() -> new IllegalArgumentException("CustomPattern not found"));
        }

        public static List<String> endValues() {
            return Arrays.stream(CustomPattern.values()).map(p -> p.end).toList();
        }

        public static List<String> startValues() {
            return Arrays.stream(CustomPattern.values()).map(p -> p.start).toList();
        }
    }
}
