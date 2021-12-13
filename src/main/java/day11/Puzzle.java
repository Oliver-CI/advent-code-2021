package day11;

import util.FileUtil;

import java.io.InputStream;
import java.util.List;

public class Puzzle {
    public static void main(String[] args) {
//        final String fileName = "day11/example.txt";
        final String fileName = "day11/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);
        final List<String> allLines = FileUtil.getAllLines(inputStream);

        final Flasher flasher = new Flasher(allLines);

        System.out.println("Result: " + flasher.startFlashing(500));
    }
}
