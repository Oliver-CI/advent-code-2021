package day5;

import util.IterativeSolver;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LineManager implements IterativeSolver {
    HashMap<Point, Integer> interconnections = new HashMap<>();

    @Override
    public void iterate(String line) {
        List<Point> original = Arrays.stream(line.split("->")).map(p -> {
            var coordinates = Arrays.stream(p.trim().split(",")).map(Integer::parseInt).toList();
            Integer x = coordinates.get(0);
            Integer y = coordinates.get(1);
            return new Point(x, y);
        }).toList();
        final Point point1 = original.get(0);
        final Point point2 = original.get(1);
        var points = new ArrayList<>(original);
        if (point1.x == point2.x) {
            points.addAll(addRangePoints(point1.x, point1.y, point2.y, true));
        } else if (point1.y == point2.y) {
            points.addAll(addRangePoints(point1.y, point1.x, point2.x, false));
        } else {
            points.addAll(determineDiagonalPoints(point1, point2));
        }
        points.forEach(p -> {
            if (interconnections.containsKey(p)) {
                interconnections.put(p, interconnections.get(p) + 1);
            } else {
                interconnections.put(p, 1);
            }
        });
    }

    private List<Point> determineDiagonalPoints(Point point1, Point point2) {
        final int diffX = point1.x - point2.x;
        final int diffY = point1.y - point2.y;
        var list = new ArrayList<Point>();
        if (Math.abs(diffX) == Math.abs(diffY)) {
            for (int i = 1; i < Math.abs(diffX); i++) {
                var x = diffX < 0 ? point1.x + i : point1.x - i;
                var y = diffY < 0 ? point1.y + i : point1.y - i;
                list.add(new Point(x, y));
            }
        }
        return list;
    }

    private List<Point> addRangePoints(Integer staticValue, Integer boundary1, Integer boundary2, boolean firstCoordinate) {
        if (boundary1 > boundary2) {
            var temp = boundary1;
            boundary1 = boundary2;
            boundary2 = temp;
        }
        var list = new ArrayList<Point>();
        for (int i = boundary1 + 1; i < boundary2; i++) {
            if (firstCoordinate) {
                list.add(new Point(staticValue, i));
            } else {
                list.add(new Point(i, staticValue));
            }
        }
        return list;
    }

    public long getConnections() {
        return interconnections.entrySet().stream().filter(e -> e.getValue() > 1).count();
//                .reduce(0, Integer::sum);
    }
}
