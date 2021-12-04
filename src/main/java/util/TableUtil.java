package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TableUtil {

    public static <T> List<List<T>> columnOperation(int[][] array, int column, Function<Integer, T> operation) {
        //cols
        var result = new ArrayList<List<T>>();
        for (int i = 0; i < column; i++) {
            int columnIndex = i;
            final List<T> list = Arrays.stream(array).map(ints -> ints[columnIndex]).map(operation).toList();
            result.add(list);
        }
        return result;
    }

    public static <T> List<List<T>> columnOperation(int[][] array, Function<Integer, T> operation) {
        return TableUtil.columnOperation(array, array[0].length, operation);
    }
}
