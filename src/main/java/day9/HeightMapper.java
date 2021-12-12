package day9;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
public class HeightMapper {
    public static final int CHAR_9 = 57;
    private final List<Point> lowestPoints;
    private final List<Integer> basinSizes;
    private final int risk;
    private int maxX;
    private int maxY;

    public HeightMapper() {
        this.lowestPoints = new ArrayList<>();
        this.basinSizes = new ArrayList<>();
        this.risk = 0;
    }

    public void measure(List<String> lines) {
        maxY = lines.size() - 1;
        maxX = lines.get(0).length() - 1;
        final List<int[]> asciiTable = lines.stream().map(String::chars).map(IntStream::toArray).toList();
        for (int i = 0; i < asciiTable.size(); i++) {
            int[] row = asciiTable.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] != CHAR_9) {
                    final Point point = new Point(i, j, parseCharToInt(row[j]));
                    final boolean lowest = checkIfLowest(asciiTable, point);
                    if (lowest) {
                        lowestPoints.add(point);
                        calcBasinSize(asciiTable, point);
                    }
                }
            }
        }
    }

    private void calcBasinSize(List<int[]> asciiTable, Point point) {
        var traversed = new ArrayList<Point>() {{
            add(point);
        }};
        ArrayList<Point> active = new ArrayList<>() {{
            add(point);
        }};
        while (!active.isEmpty()) {
            var neighbours = new ArrayList<Point>();
            for (Point activePoint : active) {
                getNeighbours(asciiTable, activePoint).stream()
                        .filter(n -> n.value < 9)
                        .forEach(neighbours::add);
            }
            traversed.addAll(neighbours);
            active = new ArrayList<>(neighbours);
        }
        basinSizes.add(traversed.size());
    }

    private int parseCharToInt(int minVal) {
        return Integer.parseInt(Character.toString(minVal));
    }

    private List<Point> getNeighbours(List<int[]> table, Point point) {
        List<Point> xNeighbours = getNeighboursX(table.get(point.x), point);
        List<Point> yNeighbours = getNeighboursY(table, point);
        return Stream.of(xNeighbours, yNeighbours).flatMap(Collection::stream).toList();
    }

    private List<Point> getNeighboursX(int[] row, Point original) {
        final int col = original.y;
        if (col == maxX) {
            return List.of(new Point(original.x, col - 1, parseCharToInt(row[col - 1])));
        } else if (col == 0) {
            return List.of(new Point(original.x, col + 1, parseCharToInt(row[col + 1])));
        } else {
            final Point left = new Point(original.x, col - 1, parseCharToInt(row[col - 1]));
            final Point right = new Point(original.x, col + 1, parseCharToInt(row[col + 1]));
            return List.of(left, right);
        }
    }

    private List<Point> getNeighboursY(List<int[]> table, Point original) {
        final int col = original.y;
        var points = new ArrayList<Point>();
        if (original.x > 0) {
            final int indexLower = original.x - 1;
            var lower = table.get(indexLower);
            points.add(new Point(indexLower, col, parseCharToInt(lower[col])));
        }
        if (original.x < maxY) {
            final int indexUpper = original.x + 1;
            var upper = table.get(indexUpper);
            points.add(new Point(indexUpper, col, parseCharToInt(upper[col])));
        }
        return points;
    }

    private boolean checkIfLowest(List<int[]> table, Point point) {
        List<Point> xNeighbours = getNeighboursX(table.get(point.x), point);
        List<Point> yNeighbours = getNeighboursY(table, point);
        return Stream.of(xNeighbours, yNeighbours).flatMap(Collection::stream)
                .allMatch(p -> p.value > point.value);
    }

    public int getRisk() {
        return lowestPoints.stream().map(p -> p.value).map(i -> i + 1).reduce(0, Integer::sum);
    }

    public int getBasinSizes() {
        return basinSizes.stream().sorted(Comparator.reverseOrder()).limit(3).reduce((a, b) -> a * b).orElse(0);
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
