package day9;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle9 {
    public static void main(String[] args) {
        final String fileName = "day9/example.txt";
//        final String fileName = "day9/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);

        var heightMapper = new HeightMapper();
        heightMapper.measure(FileUtil.getAllLines(inputStream));

        System.out.println("Risk: " + heightMapper.getRisk());

    }
}
