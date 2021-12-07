package day7;

import util.FileUtil;

import java.io.InputStream;
import java.util.List;

public class Puzzle7 {
    public static void main(String[] args) {
//        final String fileName = "day7/example.txt";
        final String fileName = "day7/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);
        final List<String> allLines = FileUtil.getAllLines(inputStream);
        final String firstLine = allLines.get(0);


        var fuelService = new FuelService();
        System.out.println("Fuel: " + fuelService.calcLowFuel(firstLine));


    }
}
