package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public static void iterateOverLine(final InputStream stream, final IterativeSolver solver){
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
}
