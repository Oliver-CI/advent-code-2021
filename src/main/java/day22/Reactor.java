package day22;

import util.IterativeSolver;

public class Reactor implements IterativeSolver {
    private static final int MAX_DIMENSION = 50;
    private static final int MIN_DIMENSION = -50;
    public static final String REGEX_RANGE = "\\..";
    public static final String REGEX_VAR_ASSIGNMENT = "=";

    private final int[][][] grid = new int[2 * MAX_DIMENSION + 1][2 * MAX_DIMENSION + 1][2 * MAX_DIMENSION + 1];

    @Override
    public void iterate(String line) {
        final String[] split = line.split(" ");
        var on = split[0];
        var coor = split[1];
        var coors = coor.split(",");
        var xss = coors[0].split(REGEX_VAR_ASSIGNMENT)[1].split(REGEX_RANGE);
        var x0 = Math.max((Integer.parseInt(xss[0])), MIN_DIMENSION);
        var x1 = Math.min((Integer.parseInt(xss[1])), MAX_DIMENSION);
        var yss = coors[1].split(REGEX_VAR_ASSIGNMENT)[1].split(REGEX_RANGE);
        var y0 = Math.max((Integer.parseInt(yss[0])), MIN_DIMENSION);
        var y1 = Math.min(Integer.parseInt(yss[1]), MAX_DIMENSION);
        var zss = coors[2].split(REGEX_VAR_ASSIGNMENT)[1].split(REGEX_RANGE);
        var z0 = Math.max((Integer.parseInt(zss[0])), MIN_DIMENSION);
        var z1 = Math.min(Integer.parseInt(zss[1]), MAX_DIMENSION);
        final int enableCube = "on".equals(on) ? 1 : 0;
//        System.out.printf("x: %d, %d, y: %d, %d, z: %d, %d\n", x0, x1, y0, y1, z0, z1);

        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                for (int z = z0; z <= z1; z++) {
                    grid[x + MAX_DIMENSION][y + MAX_DIMENSION][z + MAX_DIMENSION] = enableCube;
                }
            }
        }
    }

    public long getResult() {
        long total = 0;
        for (int x = MIN_DIMENSION; x <= MAX_DIMENSION; x++) {
            for (int y = MIN_DIMENSION; y <= MAX_DIMENSION; y++) {
                for (int z = MIN_DIMENSION; z <= MAX_DIMENSION; z++) {
                    total += grid[x + MAX_DIMENSION][y + MAX_DIMENSION][z + MAX_DIMENSION];
                }
            }
        }
        return total;
    }
}
