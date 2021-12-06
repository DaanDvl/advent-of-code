package nl.daandvl.adventofcode.solutions.year2021;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Disclaimer: VERY SLOW SOLUTION
 */
public class Day05 extends Solution {
    @Getter private final int day = 5;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5774, 18423};
    }

    @Override
    protected void run(List<String> input) {
        List<Line> lines = new ArrayList<>();

        for (String lineStr : input) {
            String[] coords = lineStr.split(" -> ");
            Coordinate c1 = new Coordinate(coords[0]);
            Coordinate c2 = new Coordinate(coords[1]);
            lines.add(new Line(c1, c2));
        }

        List<Coordinate> allPoints = new ArrayList<>();
        List<Coordinate> crossed = new ArrayList<>();

        for (Line line : lines) {
            for (Coordinate point : line.getPoints()) {
                if(allPoints.contains(point)) {
                    if(!crossed.contains(point)) crossed.add(point);
                } else {
                    allPoints.add(point);
                }
            }
        }

        a = crossed.size();

        for (Line line : lines) {
            for (Coordinate point : line.getDiagonals()) {
                if(allPoints.contains(point)) {
                    if(!crossed.contains(point)) crossed.add(point);
                } else {
                    allPoints.add(point);
                }
            }
        }

        b = crossed.size();
    }

    @Getter @AllArgsConstructor @EqualsAndHashCode
    public class Coordinate {
        private int x;
        private int y;

        public Coordinate(String coordStr) {
            String[] coord = coordStr.split(",");
            this.x = Integer.parseInt(coord[0]);
            this.y = Integer.parseInt(coord[1]);
        }

        @Override
        public String toString() {
            return x + "," + y;
        }
    }

    @Getter
    public class Line {
        private Coordinate c1, c2;
        private List<Coordinate> points;
        private List<Coordinate> diagonals;

        public Line(Coordinate c1, Coordinate c2) {
            this.c1 = c1;
            this.c2 = c2;
            calcAllPoints();
        }

        public boolean isPointOnLine(Coordinate c) {
            return points.contains(c);
        }

        public void calcAllPoints() {
            List<Coordinate> points = new ArrayList<>();
            List<Coordinate> diagonals = new ArrayList<>();

            if (c1.getX() == c2.getX()) {
                // Vertical Line
                int minY = Math.min(c1.getY(), c2.getY());
                int length = Math.abs(c1.getY() - c2.getY()) + 1;

                for (int y = 0; y < length; y++) {
                    points.add(new Coordinate(c1.getX(), minY + y));
                }
            }
            else if (c1.getY() == c2.getY()) {
                // Horizontal Line
                int minX = Math.min(c1.getX(), c2.getX());
                int length = Math.abs(c1.getX() - c2.getX()) + 1;

                for (int x = 0; x < length; x++) {
                    points.add(new Coordinate(minX + x, c1.getY()));
                }
            }
            else {
                int length = Math.abs(c1.getX() - c2.getX()) + 1;
                boolean xPosi = c1.x < c2.x;
                boolean yPosi = c1.y < c2.y;

                for (int i = 0; i < length; i++) {
                    int x = c1.getX() + i * (xPosi ? 1 : -1);
                    int y = c1.getY() + i * (yPosi ? 1 : -1);
                    diagonals.add(new Coordinate(x, y));
                }
            }


            this.diagonals = diagonals;
            this.points = points;
        }

        @Override
        public String toString() {
            return c1 + " -> " + c2;
        }
    }
}