package day2;

import lombok.Getter;
import util.IterativeSolver;

@Getter
public class VehicleNavigation implements IterativeSolver {

    private static final String DELIMITER = " ";
    private int x;
    private int y;
    private int aim;

    @Override
    public void iterate(String line) {
        if (line.isEmpty()) {
            return;
        }
        final String[] strings = line.split(DELIMITER);
        if (strings.length == 2) {
            final String directionString = strings[0];
            final int value = Integer.parseInt(strings[1]);

            final Direction direction = Direction.valueOf(directionString.toUpperCase());

            switch (direction) {
                case UP -> aim -= value;
                case DOWN -> aim += value;
                case FORWARD -> {
                    x += value;
                    y += (aim * value);
                }
            }
        }
    }

    public int getPosition() {
        return x * y;
    }
}
