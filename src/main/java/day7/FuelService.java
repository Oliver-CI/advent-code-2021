package day7;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class FuelService {

    public int calcLowFuel(final String line) {
        final List<Integer> integers = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();

        final IntSummaryStatistics stats = integers.stream().mapToInt(Integer::intValue).summaryStatistics();

        final int lowerBound = (int) (stats.getAverage() - (stats.getAverage() * .5));
        final int upperBound = (int) (stats.getAverage() + (stats.getAverage() * .5));
        var min = Math.max(stats.getMin(), lowerBound);
        var max = Math.min(stats.getMax(), upperBound);
        return IntStream.range(min, max).map(diff -> this.getDiffFuel(integers, diff)).min().orElse(0);
    }

    private Integer getDiffFuel(final List<Integer> integers, final int diff) {
        return integers.stream().map(calcDiff(diff)).reduce(0, Integer::sum);
    }

    private Function<Integer, Integer> calcDiff(final int diff) {
        return i -> {
            final int positiveDiff = Math.abs(i - diff);
            int total = 0;
            for (int j = 1; j <= positiveDiff; j++) {
                total += j;
            }
            return total;
        };
    }
}
