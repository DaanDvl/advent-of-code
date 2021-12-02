package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1488669, 1176514794};
    }

    @Override
    protected void run(List<String> input) {
        long x = 0;
        long y = 0;

        long yFixed = 0;
        long aim = 0;

        for (String line : input) {
            String[] instruction = line.split(" ");
            String task = instruction[0];
            int amount = Integer.parseInt(instruction[1]);

            switch (task) {
                case "forward" -> {
                    x += amount;
                    yFixed -= aim * amount;
                }
                case "up" -> {
                    y += amount;
                    aim -= amount;
                }
                case "down" -> {
                    y -= amount;
                    aim += amount;
                }
            }
        }

        a = x*-y;
        b = x*-yFixed;
    }
}