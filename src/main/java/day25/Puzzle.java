package day25;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle {
    public static void main(String[] args) {
        final String fileName = "day25/example.txt";
//        final String fileName = "day25/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);


        System.out.println("Result: ");
    }
}
