package day2;

import util.FileUtil;

import java.io.InputStream;

public class Puzzle2 {
    public static void main(String[] args) {
//        final String fileName = "day2/example2.txt";
        final String fileName = "day2/input2.txt";
        final InputStream inputStream = FileUtil.readFile(fileName);

        var navigation = new VehicleNavigation();
        FileUtil.iterateOverLine(inputStream, navigation);
        System.out.println("X: " + navigation.getX());
        System.out.println("Y: " + navigation.getY());
        System.out.println("Current position: " + navigation.getPosition());
    }
}
