package day4;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle4 {
    public static void main(String[] args) {
//        final String fileName = "day4/example.txt";
        final String fileName = "day4/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);

        var bingo = new Bingo(FileUtil.getAllLines(inputStream));
        bingo.startGame();

        System.out.println("Score: " + bingo.getScore());
    }
}
