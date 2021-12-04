package day4;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Bingo {
    private static final String SPACE_DELIMITER = " ";
    private static final String COMMA_DELIMITER = ",";
    private final List<Integer> chosenNumbers;
    private final List<Board> boards;
    private Integer score;

    public Bingo(final List<String> lines) {
        this.chosenNumbers = Arrays.stream(lines.get(0).split(COMMA_DELIMITER)).map(Integer::parseInt).toList();

        boards = new ArrayList<>();
        //skip first
        for (int i = 1; i < lines.size(); i += 6) {
            final List<String> strings = lines.subList(i + 1, i + 6);
            final List<List<Integer>> valueList = strings.stream().map(s -> {
                final String[] split = s.trim().split(SPACE_DELIMITER);
                return Arrays.stream(split).filter(splitValue -> !splitValue.isBlank()).map(Integer::parseInt).toList();
            }).toList();
            final Board b = new Board(i, valueList);
            System.out.println(b);
            boards.add(b);
        }
    }

    public void startGame() {
        for (Integer chosenNumber : chosenNumbers) {
            final List<Board> activeBoards = this.boards.stream().filter(Board::isActive).toList();
            if (activeBoards.size() > 1) {
                for (Board board : activeBoards) {
                    board.markNumber(chosenNumber);
                }
            } else {
                final Board lastBoard = activeBoards.get(0);
                final boolean bingo = lastBoard.markNumber(chosenNumber);
                if (bingo) {
                    endGame(lastBoard, chosenNumber);
                    return;
                }
            }
        }
    }

    private void endGame(Board board, Integer chosenNumber) {
        final int score = board.calcScore();
        this.score = score * chosenNumber;
    }
}
