package day6;

import lombok.Getter;
import util.IterativeSolver;

import java.util.Arrays;
import java.util.HashMap;

@Getter
public class FishPopulation implements IterativeSolver {
    private long totalPopulation;
    private final int days;
    private HashMap<Long, Long> pool;

    public FishPopulation(int days) {
        this.days = days;
        this.pool = new HashMap<>();
    }

    @Override
    public void iterate(String line) {

        Arrays.stream(line.split(",")).map(Long::parseLong).sorted().forEach(i -> addEntry(pool, i, 1L));
        var counter = new HashMap<Long, Long>();

        for (int i = 0; i < days; i++) {
            for (var entry : pool.entrySet()) {
                if (entry.getKey() == 0) {
                    addEntry(counter, 8L, entry.getValue());
                    addEntry(counter, 6L, entry.getValue());
                } else {
                    addEntry(counter, entry.getKey() - 1, entry.getValue());
                }
            }
            pool = counter;
            counter = new HashMap<>();
        }

        totalPopulation = this.pool.values().stream().reduce(0L, Long::sum);
    }

    public void addEntry(HashMap<Long, Long> map, final long day, final long value) {
        if (map.containsKey(day)) {
            map.put(day, map.get(day) + value);
        } else {
            map.put(day, value);
        }
    }


}
