package day5;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle5 {
    public static void main(String[] args) {
//        final String fileName = "day5/example.txt";
        final String fileName = "day5/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);

        var lineManager = new LineManager();
        FileUtil.iterateOverLine(inputStream, lineManager);

        System.out.println("Connections: " + lineManager.getConnections());
    }
}
