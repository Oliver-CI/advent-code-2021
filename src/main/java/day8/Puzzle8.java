package day8;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle8 {
    public static void main(String[] args) {
//        final String fileName = "day8/example.txt";
        final String fileName = "day8/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);

        var charDecoder = new CharDecoder();
        FileUtil.iterateOverLine(inputStream, charDecoder);

        System.out.println("UniqueDigits: " + charDecoder.getUniqueDigits());

    }
}
