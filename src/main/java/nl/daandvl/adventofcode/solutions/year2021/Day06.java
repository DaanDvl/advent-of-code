package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{365862, 1653250886439L};
    }

    @Override
    protected void run(List<String> input) {
        Map<Long, Long> allFish = new HashMap<>();

        List<Long> longInput = InputUtils.asNumberList(input).collect(Collectors.toList());
        for (long num : longInput) {
            allFish.put(num, 1 + allFish.getOrDefault(num, 0L));
        }

        for (int i = 1; i < 257; i++) {
            allFish = evolve(allFish);
            if(i == 80) a = allFish.values().stream().mapToLong(Long::longValue).sum();
        }

        b = allFish.values().stream().mapToLong(Long::longValue).sum();

    }

    public Map<Long, Long> evolve(Map<Long, Long> fish) {
        Map<Long, Long> res = new HashMap<>();

        res.put(0L, fish.getOrDefault(1L, 0L));
        res.put(1L, fish.getOrDefault(2L, 0L));
        res.put(2L, fish.getOrDefault(3L, 0L));
        res.put(3L, fish.getOrDefault(4L, 0L));
        res.put(4L, fish.getOrDefault(5L, 0L));
        res.put(5L, fish.getOrDefault(6L, 0L));
        res.put(6L, fish.getOrDefault(7L, 0L) + fish.getOrDefault(0L, 0L));
        res.put(7L, fish.getOrDefault(8L, 0L));
        res.put(8L, fish.getOrDefault(0L, 0L));

        return res;
    }

}