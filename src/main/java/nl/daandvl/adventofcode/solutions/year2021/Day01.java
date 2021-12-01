package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day01 extends Solution {
    @Getter private final int day = 01;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

    @Override
    public String[] getCorrectOutputText() {
        return new String[]{};
    }

    @Override
    protected void run(List<String> input) {
        List<Long> inputNumbers = InputUtils.asNumberList(input).collect(Collectors.toList());;
        List<Long> sums = new ArrayList<>();

        long last = -1;
        for (int i = 0; i < inputNumbers.size(); i++) {
            long num = inputNumbers.get(i);
            if (last != -1 && last < num) {
                a++;
            }

            last = num;

            if (i + 3 <= inputNumbers.size()) {
                sums.add(
                        inputNumbers.get(i) +
                        inputNumbers.get(i + 1) +
                        inputNumbers.get(i + 2)
                );
            }
        }

        last = -1;

        for (long sum : sums) {
            if (last != -1 && last < sum) {
                b++;
            }
            last = sum;
        }
    }
}