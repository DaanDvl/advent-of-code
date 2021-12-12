package nl.daandvl.adventofcode.solutions.year2021;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 extends Solution {
    @Getter private final int day = 11;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1793, 247};
    }

    @Override
    protected void run(List<String> input) {
        int height = input.size();

        for (int y = 0; y < height; y++) {
            char[] chars = input.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                int val = Integer.parseInt(chars[x] + "");
                new Squid(x, y, val);
            }
        }

        int step = 0;

        while(!Squid.isAllZeroes()) {
            step++;

            Squid.allSquid.forEach(Squid::regen);

            List<Squid> nines = Squid.getAllNines();

            nines.forEach(Squid::flash);

            long count = Squid.allSquid.stream().filter(Squid::die).count();

            if(step <= 100) a += count;
        }

        b = step;
    }

    @ToString @EqualsAndHashCode
    public class Squid {
        private final int x;
        private final int y;
        private int val;

        public static List<Squid> allSquid = new ArrayList<>();

        public Squid(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;

            allSquid.add(this);
        }

        public void flash() {
            List<Squid> nearby = nearbySquid();

            for (Squid squid : nearby) {
                squid.val++;
                if (squid.val == 10) squid.flash();
            }

        }

        public void regen() {
            this.val++;
        }

        public boolean die() {
            if(this.val > 9) {
                this.val = 0;
                return true;
            }

            return false;
        }

        public List<Squid> nearbySquid() {
            return allSquid.stream()
                    .filter(squid ->
                            squid.x >= this.x - 1 &&
                            squid.x <= this.x + 1 &&
                            squid.y >= this.y - 1 &&
                            squid.y <= this.y + 1
                    )
                    .collect(Collectors.toList());
        }

        public static boolean isAllZeroes() {
            return allSquid.stream().allMatch(squid -> squid.val == 0);
        }

        public static List<Squid> getAllNines() {
            return allSquid.stream()
                    .filter(squid -> squid.val > 9)
                    .collect(Collectors.toList());
        }

        public static String gridToString() {
            StringBuilder res = new StringBuilder();

            for(Squid squid : allSquid) {
                res.append(squid.val);
                if(squid.x == 9) res.append("\n");
            }

            return res.toString();
        }
    }
}