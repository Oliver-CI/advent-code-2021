package day10;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle {
    public static void main(String[] args) {
//        final String fileName = "day10/example.txt";
        final String fileName = "day10/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);
        var compiler = new CustomCompiler();
        FileUtil.iterateOverLine(inputStream, compiler);

        System.out.println("SyntaxErrorScore: " + compiler.getSyntaxErrorScore());
        System.out.println("CompletionScore: " + compiler.getCompletionScore());
    }
}
