package day1;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle1 {
    public static void main(String[] args) {
        final String fileName = "day1/input1.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);
//        final SweepSonarSimple sweep = new SweepSonarSimple();
        final SweepSonarList sweep = new SweepSonarList();
        FileUtil.iterateOverLine(inputStream, sweep);
        System.out.println("IncreaseTotal: " + sweep.getIncreaseTotal());
        System.out.println("DecreaseTotal: " + sweep.getDecreaseTotal());
    }
}
