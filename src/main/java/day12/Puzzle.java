package day12;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle {
    public static void main(String[] args) {
        final String fileName = "day12/example.txt";
//        final String fileName = "day12/example2.txt";
//        final String fileName = "day12/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);
        final CaveTracker caveTracker = new CaveTracker();
        FileUtil.iterateOverLine(inputStream, caveTracker);
        caveTracker.buildTree();
        System.out.println("Result: " + caveTracker.getTotalPaths());
    }
}
