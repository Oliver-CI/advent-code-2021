package day11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Flasher {
    private final List<List<Octo>> octi;

    public Flasher(final List<String> lines) {
        octi = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            final String[] strings = s.split("");
            var octos = new ArrayList<Octo>();
            for (int j = 0; j < strings.length; j++) {
                octos.add(new Octo(i, j, Integer.parseInt(strings[j])));
            }
            this.octi.add(octos);
        }
    }

    public long startFlashing(final int steps) {
        for (int s = 0; s < steps; s++) {
            final List<Octo> simple = octi.stream().flatMap(Collection::stream).toList();
            simple.forEach(Octo::raiseLevel);
            boolean isFlashing = simple.stream().anyMatch(octo -> octo.value > 9);
            while (isFlashing) {
                simple.stream()
                        .filter(octo -> octo.value > 9 && !octo.flashed)
                        .map(octo -> getNeighbours(octi, octo))
                        .flatMap(Collection::stream)
                        .forEach(Octo::raiseLevel);

                isFlashing = simple.stream()
                        .filter(octo -> !octo.flashed)
                        .anyMatch(octo -> octo.value > 9);
            }
            simple.stream()
                    .filter(octo -> octo.value > 9)
                    .forEach(Octo::flash);
        }


        final int flashes = Octo.flashes;
        Octo.flashes = 0;
        return flashes;
    }

    private List<Octo> getNeighbours(List<List<Octo>> table, Octo point) {
        List<Octo> xNeighbours = getNeighboursX(table.get(point.x), point);
        List<Octo> yNeighbours = getNeighboursY(table, point);
        var diagonal = new ArrayList<Octo>();
        for (Octo yNeighbour : yNeighbours) {
            diagonal.addAll(getNeighboursX(table.get(yNeighbour.x), yNeighbour));
        }
        return Stream.of(xNeighbours, yNeighbours, diagonal).flatMap(Collection::stream)
                .filter(octo -> !octo.flashed)
                .toList();
    }

    private List<Octo> getNeighboursX(List<Octo> row, Octo original) {
        final int col = original.y;
        if (col == 9) {
            return List.of(row.get(col - 1));
        } else if (col == 0) {
            return List.of(row.get(col + 1));
        } else {
            final Octo left = row.get(col - 1);
            final Octo right = row.get(col + 1);
            return List.of(left, right);
        }
    }

    private List<Octo> getNeighboursY(List<List<Octo>> table, Octo original) {
        final int col = original.y;
        var neighbours = new ArrayList<Octo>();
        if (original.x > 0) {
            neighbours.add(table.get(original.x - 1).get(col));
        }
        if (original.x < 9) {
            neighbours.add(table.get(original.x + 1).get(col));
        }
        return neighbours;
    }

    static class Octo {
        int x;
        int y;
        int value;
        boolean flashed;
        static int flashes = 0;

        public Octo(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.flashed = false;
        }

        @Override
        public String toString() {
            return String.format("%d:[%d,%d]", value, x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Flasher.Octo point = (Flasher.Octo) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public void raiseLevel() {
            if (!flashed) {
                value++;
            }
            if (value > 9) {
                flashed = true;
            }
        }


        public void flash() {
            flashes++;
            flashed = false;
            value = 0;
        }
    }
}
