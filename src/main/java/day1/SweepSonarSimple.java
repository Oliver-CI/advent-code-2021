package day1;

import lombok.Getter;
import util.IterativeSolver;

public class SweepSonarSimple implements IterativeSolver {

    private int previousReading = Integer.MAX_VALUE;
    @Getter
    private int increaseTotal;
    @Getter
    private int decreaseTotal;

    @Override
    public void iterate(final String line) {
        final int currentReading = Integer.parseInt(line);

        if (previousReading == Integer.MAX_VALUE) {
            previousReading = currentReading;
            return;
        }
        if (previousReading < currentReading) {
            increaseTotal++;
        } else if (previousReading > currentReading) {
            decreaseTotal++;
        }
        previousReading = currentReading;
    }
}
