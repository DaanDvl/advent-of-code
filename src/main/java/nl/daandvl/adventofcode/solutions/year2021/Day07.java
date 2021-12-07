package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day07 extends Solution {
    @Getter private final int day = 7;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{337833, 96678050};
    }

    @Override
    protected void run(List<String> input) {
        List<Long> crabs = InputUtils.asNumberList(input).collect(Collectors.toList());

        long min = crabs.stream().mapToLong(Long::longValue).min().getAsLong();
        long max = crabs.stream().mapToLong(Long::longValue).max().getAsLong();

        a = Long.MAX_VALUE;
        b = Long.MAX_VALUE;

        for(long i = min; i < max; i++) {
            long fuel = getFuel(i, crabs);
            long fuelRec = getFuelRecalculated(i, crabs);

            if (fuel < a) a = fuel;
            if (fuelRec < b) b = fuelRec;
        }
    }

    public long getFuel(long to, List<Long> crabs) {
        long fuel = 0;
        for(long crab : crabs) {
            fuel += Math.abs(crab - to);
        }

        return fuel;
    }

    public long getFuelRecalculated(long to, List<Long> crabs) {
        long fuel = 0;
        for(long crab : crabs) {
            long steps = Math.abs(crab - to);
            fuel += 0.5 * steps * (steps + 1);
        }

        return fuel;
    }
}