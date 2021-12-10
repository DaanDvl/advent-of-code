package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day10 extends Solution {
    @Getter private final int day = 10;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{319233, 1118976874};
    }

    @Override
    protected void run(List<String> input) {
        List<Long> scores = new ArrayList<>();

        for (String line : input) {
            List<Character> openedList = new ArrayList<>();

            boolean fail = false;

            for (char c : line.toCharArray()) {
                char last = openedList.size() != 0 ? openedList.get(openedList.size() - 1) : '#';

                if (c == ')') {
                    if (last != '(') {
                        a += 3;
                        fail = true;
                        break;
                    }
                }
                else if (c == ']') {
                    if (last != '[') {
                        a += 57;
                        fail = true;
                        break;
                    }
                }
                else if (c == '}') {
                    if (last != '{') {
                        a += 1197;
                        fail = true;
                        break;
                    }
                }
                else if (c == '>') {
                    if (last != '<') {
                        a += 25137;
                        fail = true;
                        break;
                    }
                }
                else {
                    openedList.add(c);
                    continue;
                }
                openedList.remove(openedList.size() - 1);
            }

            if (fail) continue;

            Collections.reverse(openedList);

            long res = 0;

            for (char finish : openedList) {
                int opposite = points(finish);

                res *= 5;
                res += opposite;
            }

            scores.add(res);
            System.out.println();
        }

        Collections.sort(scores);
        b = scores.get(scores.size()/2);
    }

    public int points(char c) {
        switch (c) {
            case '(' -> {
                return 1;
            }
            case '[' -> {
                return 2;
            }
            case '{' -> {
                return 3;
            }
            case '<' -> {
                return 4;
            }
            default -> {
                return 0;
            }
        }
    }
}