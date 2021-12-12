package day11;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle {
    public static void main(String[] args) {
        final String fileName = "day10/example.txt";
//        final String fileName = "day10/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);


        System.out.println("Result: ");
    }
}
