package day4;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Getter
@ToString
public class Board {
    private static final int DIMENSION = 5;
    private final Cell[][] cells;
    private final int id;
    private boolean active;


    public Board(final int id, final List<List<Integer>> values) {
        this.id = id;
        this.active = true;
        this.cells = new Cell[DIMENSION][DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                final Cell cell = new Cell(values.get(i).get(j));
                cells[i][j] = cell;
            }
        }
    }

    public boolean markNumber(final int number) {
        final Optional<Cell> optionalCell = Arrays.stream(cells).flatMap(Arrays::stream).filter(c -> c.getValue() == number).findFirst();
        optionalCell.ifPresent(cell -> cell.setChosen(true));

        // check for bingo
        //rows
        for (Cell[] cell : cells) {
            var allChosen = Arrays.stream(cell).allMatch(Cell::isChosen);
            if (allChosen) {
                this.active = false;
                return true;
            }
        }
        //cols
        for (int i = 0; i < DIMENSION; i++) {
            int columnIndex = i;
            var allChosen = IntStream.range(0, DIMENSION).mapToObj(j -> cells[j][columnIndex]).allMatch(Cell::isChosen);
            if (allChosen) {
                this.active = false;
                return true;
            }
        }
        return false;
    }

    public int calcScore() {
        return Arrays.stream(cells).flatMap(Arrays::stream).filter(c -> !c.isChosen()).map(Cell::getValue).reduce(0, Integer::sum);
    }

    @Getter
    @Setter
    static class Cell {
        private final int value;
        private boolean chosen;

        public Cell(int value) {
            this.value = value;
            this.chosen = false;
        }

        @Override
        public String toString() {
            return "Cell{" + value + '}';
        }
    }
}


