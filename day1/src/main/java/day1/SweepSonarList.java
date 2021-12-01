package day1;

import lombok.Getter;
import util.IterativeSolver;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public class SweepSonarList implements IterativeSolver {

    private static final int SLIDING_WINDOW = 3;
    private final Deque<Integer> previousValues = new LinkedBlockingDeque<>();

    @Getter
    private int increaseTotal;
    @Getter
    private int decreaseTotal;

    @Override
    public void iterate(final String line) {
        final int currentReading = Integer.parseInt(line);

        if (previousValues.isEmpty() || previousValues.size() < SLIDING_WINDOW) {
            previousValues.add(currentReading);
            return;
        }

        final long previousAmount = calcTotalAmount(previousValues);
        if (previousValues.size() == SLIDING_WINDOW) {
            previousValues.removeFirst();
        }
        previousValues.add(currentReading);
        final long currentAmount = calcTotalAmount(previousValues);
        if (previousAmount < currentAmount) {
            increaseTotal++;
        } else if (previousAmount > currentAmount) {
            decreaseTotal++;
        }
    }

    private int calcTotalAmount(final Deque<Integer> deque) {
        return deque.stream()
                .reduce(0, Integer::sum);
    }
}
