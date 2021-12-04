package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{3901196, 4412188};
    }

    @Override
    protected void run(List<String> input) {
        List<String> oxygenCopy = new ArrayList<>(input);
        List<String> co2Copy = new ArrayList<>(input);

        String binary1 = "";
        String binary2 = "";

        for (int i = 0; i < input.get(0).length(); i++) {
            int bit = getBitForIndex(i, input);
            binary1 += bit == 1 ? '1' : '0';
            binary2 += bit == 1 ? '0' : '1';

            // Solution B
            int finalI = i;

            int bitOxygen = getBitForIndex(i, oxygenCopy);
            if (oxygenCopy.size() > 1) oxygenCopy = oxygenCopy.stream().filter(line -> {
                return Integer.parseInt(line.charAt(finalI)+"") == bitOxygen;
            }).collect(Collectors.toList());

            int bitCO2 = getBitForIndex(i, co2Copy) == 1 ? 0 : 1;
            if (co2Copy.size() > 1) co2Copy = co2Copy.stream().filter(line -> {
                return Integer.parseInt(line.charAt(finalI)+"") == bitCO2;
            }).collect(Collectors.toList());

        }

        int bin1a = Integer.parseInt(binary1,2);
        int bin2a = Integer.parseInt(binary2,2);

        a = bin1a * bin2a;

        int bin1b = Integer.parseInt(oxygenCopy.get(0),2);
        int bin2b = Integer.parseInt(co2Copy.get(0),2);

        b = bin1b * bin2b;
    }

    public int getBitForIndex(int index, List<String> input) {
        long amountPerIndex = 0;

        for (String line : input) {
            if (line.charAt(index) == '1') amountPerIndex++;
        }

        return amountPerIndex >= Math.ceil((double)input.size() / (double)2) ? 1 : 0;
    }
}