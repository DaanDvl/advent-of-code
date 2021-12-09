package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day09 extends Solution {
    @Getter private final int day = 9;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{530, 1019494};
    }

    List<Integer> foundIndexes;

    @Override
    protected void run(List<String> input) {
        List<Integer> grid = new ArrayList<>();
        int length = 0;

        for (String line : input) {
            if (length == 0) length = line.length();
            for (char c : line.toCharArray()) {
                grid.add(Integer.parseInt(c+""));
            }
        }

        int gridSize = grid.size();

        List<Integer> basinLengths = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            int val = grid.get(i);

            int up = i - length < 0 ? 10 : grid.get(i - length);
            int down = i + length >= gridSize ? 10 : grid.get(i + length);

            boolean hasLeft = i - 1 >= 0 && ((i - 1) / length == i / length);
            boolean hasRight = i + 1 < gridSize && ((i + 1) / length == i / length);

            int left = hasLeft ? grid.get(i - 1) : 10;
            int right = hasRight ? grid.get(i + 1) : 10;

            if (val < up && val < down && val < left && val < right) {
                a += val+1;

                // Basin Low Found
                foundIndexes = new ArrayList<>();
                checkBasin(i, length, grid);
                basinLengths.add(foundIndexes.size());
            }
        }

        basinLengths.sort(Comparator.reverseOrder());
        b = (long) basinLengths.get(0) * basinLengths.get(1) * basinLengths.get(2);
    }

    public void checkBasin(int i, int length, List<Integer> grid) {
        int val = grid.get(i);

        if (val >= 9) return;

        int gridSize = grid.size();

        int up = i - length < 0 ? -1 : grid.get(i - length);
        int down = i + length >= gridSize ? -1 : grid.get(i + length);

        boolean hasLeft = i - 1 >= 0 && ((i - 1) / length == i / length);
        boolean hasRight = i + 1 < gridSize && ((i + 1) / length == i / length);

        int left = hasLeft ? grid.get(i - 1) : -1;
        int right = hasRight ? grid.get(i + 1) : -1;

        if (foundIndexes.contains(i)) {
            return;
        } else {
            foundIndexes.add(i);
        }

        if (right >= val) {
            checkBasin(i + 1, length, grid);
        }
        if (left >= val) {
            checkBasin(i - 1, length, grid);
        }
        if (up >= val) {
            checkBasin(i - length, length, grid);
        }
        if (down >= val) {
            checkBasin(i + length, length, grid);
        }
    }
}