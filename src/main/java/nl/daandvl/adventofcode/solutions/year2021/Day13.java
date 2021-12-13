package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import lombok.ToString;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class Day13 extends Solution {
    @Getter private final int day = 13;
    @Getter private final int year = 2021;

    @Override
    public String[] getCorrectOutputText() {
        return new String[] {
                "737",
                "####.#..#...##.#..#..##..####.#..#.###.\n" +
                "...#.#..#....#.#..#.#..#.#....#..#.#..#\n" +
                "..#..#..#....#.#..#.#..#.###..####.#..#\n" +
                ".#...#..#....#.#..#.####.#....#..#.###.\n" +
                "#....#..#.#..#.#..#.#..#.#....#..#.#...\n" +
                "####..##...##...##..#..#.#....#..#.#...\n"
        };
    }

    @Override
    protected void run(List<String> input) {
        List<String> folds = new ArrayList<>();

        for(String line : input) {
            if (line.contains(",")) {
               String[] pos = line.split(",");
               int x = Integer.parseInt(pos[0]);
               int y = Integer.parseInt(pos[1]);

               new Dot(x, y);
            } else if (line.contains("fold")) {
                folds.add(line);
            }
        }

        boolean first = true;

        for (String line : folds) {
            int val = Integer.parseInt(line.split("=")[1]);
            if (line.contains("y")) {
                Dot.foldPaperY(val);
            } else {
                Dot.foldPaperX(val);
            }

            if (first) {
                a = Dot.grid.size();
                first = false;
            }
        }

        textB = Dot.gridString();
    }

    @ToString
    public class Dot {
        private int x;
        private int y;

        public static Set<Dot> grid = new HashSet<>();

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
            grid.add(this);
        }

        public static void foldPaperY(int y) {
            List<Dot> dots = new ArrayList<>(dotsAfterY(y));

            dots.forEach(dot -> {
                int diff = dot.y - y;
                int newY = y - diff;

                if(dotAtXY(dot.x, newY)) {
                    grid.removeIf(d -> d.x == dot.x && d.y == dot.y);
                    return;
                }

                dot.y = newY;
            });
        }

        public static void foldPaperX(int x) {
            List<Dot> dots = new ArrayList<>(dotsAfterX(x));

            dots.forEach(dot -> {
                int diff = dot.x - x;
                int newX = x - diff;

                if(dotAtXY(newX, dot.y)) {
                    grid.removeIf(d -> d.x == dot.x && d.y == dot.y);
                    return;
                }
                dot.x = newX;
            });
        }

        public static List<Dot> dotsAfterY(int y) {
            return grid.stream()
                    .filter(p -> p.y > y)
                    .collect(Collectors.toList());
        }

        public static List<Dot> dotsAfterX(int x) {
            return grid.stream()
                    .filter(p -> p.x > x)
                    .collect(Collectors.toList());
        }

        public static boolean dotAtXY(int x, int y) {
            return grid.stream()
                    .anyMatch(d -> d.x == x && d.y == y);
        }

        public static String gridString() {
            int highestX = grid.stream().max(Comparator.comparingInt(d -> d.x)).get().x + 1;
            int highestY = grid.stream().max(Comparator.comparingInt(d -> d.y)).get().y + 1;

            StringBuilder res = new StringBuilder();
            for(int iy = 0; iy < highestY; iy++) {
                for(int ix = 0; ix < highestX; ix++) {
                    res.append(dotAtXY(ix, iy) ? "#" : ".");
                }
                res.append("\n");
            }
            return res.toString();
        }
    }
}