package day22;

import util.IterativeSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reactor implements IterativeSolver {
    public static final String REGEX_RANGE = "\\..";
    public static final String REGEX_VAR_ASSIGNMENT = "=";

    private final List<Cube> cubes = new ArrayList<>();
    private final List<Long> xes = new ArrayList<>();
    private final List<Long> yes = new ArrayList<>();
    private final List<Long> zes = new ArrayList<>();

    @Override
    public void iterate(String line) {
        final String[] split = line.split(" ");
        var on = split[0];
        var coor = split[1];
        var coors = coor.split(",");

        var xss = coors[0].split(REGEX_VAR_ASSIGNMENT)[1].split(REGEX_RANGE);
        var x0 = Long.parseLong(xss[0]);
        var x1 = Long.parseLong(xss[1])+1;

        var yss = coors[1].split(REGEX_VAR_ASSIGNMENT)[1].split(REGEX_RANGE);
        var y0 = Long.parseLong(yss[0]);
        var y1 = Long.parseLong(yss[1])+1;

        var zss = coors[2].split(REGEX_VAR_ASSIGNMENT)[1].split(REGEX_RANGE);
        var z0 = Long.parseLong(zss[0]);
        var z1 = Long.parseLong(zss[1])+1;

        cubes.add(new Cube("on".equals(on), x0, y0, z0, x1, y1, z1));
        xes.add(x0);
        xes.add(x1);
        yes.add(y0);
        yes.add(y1);
        zes.add(z0);
        zes.add(z1);
    }

    public long getResult() {
        Collections.sort(xes);
        Collections.sort(yes);
        Collections.sort(zes);
        final int dimension = xes.size();

        boolean[][][] grid = new boolean[dimension][dimension][dimension];

        for (Cube cube : cubes) {
            //this map the actual values to a new grid to handle the big load
            var x0 = xes.indexOf(cube.x0);
            var x1 = xes.indexOf(cube.x1);
            var y0 = yes.indexOf(cube.y0);
            var y1 = yes.indexOf(cube.y1);
            var z0 = zes.indexOf(cube.z0);
            var z1 = zes.indexOf(cube.z1);

            for (int x = x0; x < x1; x++) {
                for (int y = y0; y < y1; y++) {
                    for (int z = z0; z < z1; z++) {
                        grid[x][y][z] = cube.enable;
                    }
                }
            }
        }

        long totalVolume = 0;
        final int limit = dimension - 1;
        for (int x = 0; x < limit; x++) {
            for (int y = 0; y < limit; y++) {
                for (int z = 0; z < limit; z++) {
                    if (grid[x][y][z]) {
                        var xSide = xes.get(x + 1) - xes.get(x);
                        var ySide = yes.get(y + 1) - yes.get(y);
                        var zSide = zes.get(z + 1) - zes.get(z);
                        totalVolume += (xSide * ySide * zSide);
                    }
                }
            }
        }
        return totalVolume;
    }

    record Cube(boolean enable, long x0, long y0, long z0, long x1, long y1, long z1) {

    }
}
