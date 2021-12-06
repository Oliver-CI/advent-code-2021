package day6;

import lombok.Getter;
import util.IterativeSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FishPopulation implements IterativeSolver {
    private long totalPopulation;
    private final int days;

    public FishPopulation(int days) {
        this.days = days;
    }

    @Override
    public void iterate(String line) {
        List<Fish> fishes = Arrays.stream(line.split(",")).map(Integer::parseInt).map(Fish::new).collect(Collectors.toList());

        growFishes(days, fishes);

    }

    private void growFishes(int startingDays, List<Fish> fishes) {
        while (startingDays > 0) {
            var newFishes = new ArrayList<Fish>();
            for (Fish fish : fishes) {
                if (fish.getDays() == 0) {
                    final Fish newFish = fish.bearChildren();
                    newFishes.add(newFish);
                } else {
                    fish.age();
                }
            }
            fishes.addAll(newFishes);
            startingDays--;
            if (fishes.size() > 1000000) {
                //todo does not compute
                int finalStartingDays = startingDays;
                fishes.stream().collect(Collectors.groupingBy(Fish::getDays)).values().forEach(list -> growFishes(finalStartingDays, list));
                return;
            }
        }
        totalPopulation = fishes.size();
    }

    @Getter
    static class Fish {
        private static final int NEW_DAYS = 8;
        private static final int OLD_DAYS = 6;

        private boolean newBorn;
        private int days;

        public Fish() {
            this.newBorn = true;
            this.days = NEW_DAYS;
        }

        public Fish(int days) {
            this.newBorn = false;
            this.days = days;
        }

        public Fish bearChildren() {
            if (newBorn) this.newBorn = false;
            this.days = OLD_DAYS;
            return new Fish();
        }

        public void age() {
            this.days--;
        }
    }
}
