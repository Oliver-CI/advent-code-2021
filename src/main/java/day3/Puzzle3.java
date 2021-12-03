package day3;

import util.FileUtil;

import java.io.InputStream;
import java.util.List;

public class Puzzle3 {
    public static void main(String[] args) {
        final String fileName = "day3/example.txt";
//        final String fileName = "day3/input.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);

        var powerCalculator = new PowerCalculator(FileUtil.getAllLines(inputStream));
        final List<String> lines = FileUtil.iterateOverColumn(inputStream, powerCalculator);

        powerCalculator.convertPower(lines);
        System.out.println("Gamma: " + powerCalculator.getGammaRate());
        System.out.println("Epsilon: " + powerCalculator.getEpsilonRate());
        System.out.println("power: " + powerCalculator.getPower());
        System.out.println("OxygenGenerator: " + powerCalculator.getOxygenGenerator());
        System.out.println("CO2Scrubber: " + powerCalculator.getCO2Scrubber());
        System.out.println("LifeSupportRating: " + powerCalculator.getLifeSupportRating());
    }
}
