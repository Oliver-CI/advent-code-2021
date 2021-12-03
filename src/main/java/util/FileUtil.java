package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class FileUtil {

    public static InputStream readFile(final String fileName) {
        InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (isNull(inputStream)) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        return inputStream;
    }

    public static List<String> getAllLines(final InputStream stream) {
        System.out.println("getAllLines");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            return br.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void iterateOverLine(final InputStream stream, final IterativeSolver solver) {
        System.out.println("start iterate over lines");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            var line = br.readLine();
            while (nonNull(line)) {
                solver.iterate(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> iterateOverColumn(final InputStream stream, final IterativeSolver solver) {
        System.out.println("start iterate over column");
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            lines = br.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lines.get(0).length(); i++) {
            int startIndex = i;
            int endIndex = i + 1;
            final String column = lines.stream().map(line -> line.substring(startIndex, endIndex)).collect(Collectors.joining());
            solver.iterate(column);
        }
        return lines;
    }
}
