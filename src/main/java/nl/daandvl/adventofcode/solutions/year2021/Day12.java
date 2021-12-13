package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day12 extends Solution {
    @Getter private final int day = 12;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{4338, 114189};
    }

    @Override
    protected void run(List<String> input) {
        Cave start = null;

        for(String line : input) {
            String[] caves = line.split("-");
            Cave c = new CaveFactory()
                    .setCaveName(caves[0])
                    .setConnected(caves[1])
                    .build();

            if(caves[0].equals("start")) start = c;
        }

        Cave end = Cave.allCaves.stream().filter(c -> c.name.equals("end")).findAny().orElse(null);

        PathFinder pathFinder = new PathFinder(start, end);

        pathFinder.getAdjacentCaves(start, null);
        pathFinder.getAdjacentCavesVisitedTwice(start, null, null);
    }

    public class CaveFactory {
        private String caveName;
        private String connected;

        public CaveFactory setCaveName(String caveName) {
            this.caveName = caveName;
            return this;
        }

        public CaveFactory setConnected(String connected) {
            this.connected = connected;
            return this;
        }

        public Cave build() {
            Cave cave = getCaveByName(caveName);

            if (cave == null) {
                cave = new Cave(caveName);
            }

            Cave connection = getCaveByName(connected);

            if (connection == null) {
                connection = new Cave(connected);
            }

            cave.connections.add(connection);
            connection.connections.add(cave);

            return cave;
        }

        private Cave getCaveByName(String caveName) {
            return Cave.allCaves.stream()
                    .filter(cave -> Objects.equals(caveName, cave.name))
                    .findAny().orElse(null);
        }
    }

    public class Cave {
        private final String name;
        private final Set<Cave> connections;

        public static List<Cave> allCaves = new ArrayList<>();

        public Cave(String name) {
            this.name = name;
            this.connections = new HashSet<>();
            allCaves.add(this);
        }

        public boolean isBig() {
            return name.toUpperCase().equals(name);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cave cave = (Cave) o;

            return Objects.equals(name, cave.name);
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }

    public class PathFinder {
        private Cave from;
        private Cave to;

        public PathFinder(Cave from, Cave to) {
            this.from = from;
            this.to = to;

            System.out.println(from + ", " + to);
        }

        public Set<Cave> getAdjacentCaves(Cave cave, Set<Cave> visited) {
            if(visited == null) visited = new HashSet<>();

            visited = new HashSet<>(visited);

            if(cave == to) {
                a++;
                return new HashSet<>();
            }

            Set<Cave> clone = new HashSet<>(cave.connections);
            clone.removeAll(visited);

            if (!cave.isBig()) visited.add(cave);

            for (Cave c : clone) {
                getAdjacentCaves(c, visited);
            }

            return clone;
        }

        public Set<Cave> getAdjacentCavesVisitedTwice(Cave cave, List<Cave> visitedOnce, List<Cave> visited) {
            if(visited == null) {
                visited = new ArrayList<>();
                visited.add(from);
            }
            if(visitedOnce == null) visitedOnce = new ArrayList<>();

            visited = new ArrayList<>(visited);
            visitedOnce = new ArrayList<>(visitedOnce);

            if(cave == to) {
                if(visited.size() < 3) {
                    b++;
                }
                return new HashSet<>();
            }

            if (!cave.isBig() && visitedOnce.contains(cave)) {
                visited.add(cave);
            }

            visitedOnce.add(cave);

            Set<Cave> clone = new HashSet<>(cave.connections);
            visited.forEach(clone::remove);

            for (Cave c : clone) {
                getAdjacentCavesVisitedTwice(c, visitedOnce, visited);
            }

            return clone;
        }
    }
}