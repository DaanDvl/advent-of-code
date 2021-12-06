package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.Arrays;
import java.util.List;
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
        long[] allFish = new long[9];

        List<Integer> longInput = InputUtils.asIntegerList(input).collect(Collectors.toList());

        for (int i = 0; i < 9; i++) {
            allFish[i] = 0;
        }

        for (int i : longInput) {
            allFish[i] = 1 + allFish[i];
        }

        for (int i = 1; i <= 256; i++) {
            allFish = evolve(allFish);
            if (i == 80) a = Arrays.stream(allFish).sum();
        }

        b = Arrays.stream(allFish).sum();
    }

    public long[] evolve(long[] fish) {
        long[] res = new long[9];

        for (int i = 0; i < 9; i++) {
            if(i == 8) {
                res[i] = fish[0];
                continue;
            }
            res[i] = fish[i+1];
        }

        res[6] += fish[0];
        return res;
    }

}