package day6;

import util.FileUtil;

import java.io.InputStream;
import java.util.List;

public class Puzzle6 {
    public static void main(String[] args) {
//        final String fileName = "day6/example.txt";
        final String fileName = "day6/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);
        final List<String> allLines = FileUtil.getAllLines(inputStream);
        final String firstLine = allLines.get(0);


        final int days = 80;
        var fishPopulation = new FishPopulation(days);
        fishPopulation.iterate(firstLine);
        System.out.println("Days: " + days + ", Population: " + fishPopulation.getTotalPopulation());


    }
}
