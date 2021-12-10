package day9;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
public class HeightMapper {
    private final List<Integer> lowestPoints;
    private final List<Point> lowPoints;
    private final int risk;
    private int maxX;
    private int maxY;

    public HeightMapper() {
        this.lowestPoints = new ArrayList<>();
        this.lowPoints = new ArrayList<>();
        this.risk = 0;
    }

    public void measure(List<String> lines) {
        maxY = lines.size() - 1;
        maxX = lines.get(0).length() - 1;
        final List<int[]> asciiTable = lines.stream().map(String::chars).map(IntStream::toArray).toList();
        for (int i = 0; i < asciiTable.size(); i++) {
            int[] row = asciiTable.get(i);
            final int minVal = Arrays.stream(row).min().getAsInt();
            for (int j = 0; j < row.length; j++) {
                if (row[j] == minVal) {
                    lowPoints.add(new Point(i, j, parseCharToInt(minVal)));
                }
            }
        }
        for (int i = 0; i <= maxX; i++) {
            int finalI = i;
            final List<Integer> col = lines.stream().map(String::chars).map(IntStream::toArray).map(a -> a[finalI]).toList();
            final int minVal = col.stream().mapToInt(Integer::intValue).min().getAsInt();
            for (int j = 0; j <= maxY; j++) {
                if (col.get(j) == minVal) {
                    lowPoints.add(new Point(j, i, parseCharToInt(minVal)));
                }
            }
        }
        final List<Point> distinctList = lowPoints.stream().distinct().toList();
        distinctList.forEach(point -> checkIfLowest(asciiTable, point));

    }

    private int parseCharToInt(int minVal) {
        return Integer.parseInt(Character.toString(minVal));
    }

    private void checkIfLowest(List<int[]> lines, Point point) {
        boolean xCheck = checkNeighboursX(lines.get(point.x), point.y);
        int[] lower = null, upper = null;
        if (point.x > 0) {
            lower = lines.get(point.x - 1);
        }
        if (point.x < maxY) {
            upper = lines.get(point.x + 1);
        }
        boolean yCheck = checkNeighboursY(lower, upper, point);
        if (xCheck && yCheck) {
            lowestPoints.add(point.value);
        }
    }

    private boolean checkNeighboursX(int[] ints, int col) {
        if (col == maxX) {
            return ints[col - 1] > ints[col];
        } else if (col == 0) {
            return ints[col + 1] > ints[col];
        } else {
            return ints[col - 1] > ints[col] && ints[col] < ints[col + 1];
        }
    }

    private boolean checkNeighboursY(int[] lower, int[] upper, Point point) {
        boolean lowerCheck = false, upperCheck = false;
        if (nonNull(lower)) {
            lowerCheck = parseCharToInt(lower[point.y]) > point.value;
        }
        if (nonNull(upper)) {
            upperCheck = parseCharToInt(upper[point.y]) > point.value;
        }
        if (isNull(lower)) {
            return upperCheck;
        } else if (isNull(upper)) {
            return lowerCheck;
        }
        return upperCheck && lowerCheck;
    }

    public int getRisk() {
        System.out.println(lowPoints);
        return lowestPoints.stream()
                .map(i -> i + 1)
                .reduce(0, Integer::sum);
    }

    static class Point {
        int x;
        int y;
        int value;

        public Point(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("%d:[%d,%d]", value, x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
