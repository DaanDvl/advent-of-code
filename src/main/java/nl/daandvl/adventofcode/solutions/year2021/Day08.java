package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

    @Override
    protected void run(List<String> input) {

        for(String line : input) {
            String[] parts = line.split("( \\| )");
            String[] in = parts[0].split(" ");
            String[] notes = parts[1].split(" ");

            Arrays.sort(in, Comparator.comparingInt(String::length));
            Display display = new Display(in);
            System.out.println(display.getFinalBoard());

            String res = "";

            for (String note : notes) {
                res += display.createNumber(note);
            }

            System.out.println(res);

            b += Long.parseLong(res);
        }
    }

    @Getter
    public class Display {
        private Possibility finalBoard;

        public Display(String[] in) {
            List<Possibility> possibilities = new ArrayList<>();
            String[] val = new String[9];
            for (String num : in) {
                int length = num.length();
                if (length == 2) {
                    val[0] = num;
                    char[] poss1 = new char[7];
                    poss1[5] = num.charAt(0);
                    poss1[2] = num.charAt(1);
                    possibilities.add(new Possibility(poss1));

                    char[] poss2 = new char[7];
                    poss2[2] = num.charAt(0);
                    poss2[5] = num.charAt(1);
                    possibilities.add(new Possibility(poss2));
                }

                if (length == 3) {
                    val[6] = num;
                    char topVal = removeChars(num, val[0]).charAt(0);
                    possibilities.forEach(pos -> pos.poss[0] = topVal);
                }

                if (length == 4) {
                    val[3] = num;

                    for (int i = 0; i < 2; i++) possibilities.add(possibilities.get(i).clone());

                    String newVals = removeChars(num, val[6]);

                    for (int i = 0; i < 2; i++) {
                        Possibility change = possibilities.get(i);

                        change.poss[1] = newVals.charAt(0);
                        change.poss[3] = newVals.charAt(1);

                        change = possibilities.get(2 + i);
                        change.poss[3] = newVals.charAt(0);
                        change.poss[1] = newVals.charAt(1);
                    }
                }

                if (length == 7) {
                    val[3] = num;

                    for (int i = 0; i < 4; i++) possibilities.add(possibilities.get(i).clone());

                    String newVals = removeChars(num, possibilities.get(0).allChars());

                    for (int i = 0; i < 4; i++) {
                        Possibility change = possibilities.get(i);

                        change.poss[4] = newVals.charAt(0);
                        change.poss[6] = newVals.charAt(1);

                        change = possibilities.get(4 + i);
                        change.poss[6] = newVals.charAt(0);
                        change.poss[4] = newVals.charAt(1);
                    }
                }
            }

            List<Possibility> survivedFive = new ArrayList<>();

            for (Possibility possibility : possibilities) {
                char[] p = possibility.poss;
                String fiveString = p[0] + "" + p[1] + "" + p[3] + "" + p[5] + "" + p[6];

                for (String otherNums : in) {
                    if (containsSameCharsAndHasSameSize(fiveString, otherNums)) {
                        survivedFive.add(possibility);
                    }
                }
            }

            List<Possibility> survivedTwo = new ArrayList<>();

            for (Possibility possibility : survivedFive) {
                char[] p = possibility.poss;
                String twoString = p[0] + "" + p[2] + "" + p[3] + "" + p[4] + "" + p[6];

                for (String otherNums : in) {
                    if (containsSameCharsAndHasSameSize(twoString, otherNums)) {
                        survivedTwo.add(possibility);
                    }
                }
            }

            finalBoard = survivedTwo.get(0);
        }

        public int createNumber(String in) {
            String boardString = new String(finalBoard.poss);
            int[] indexes = new int[in.length()];

            for (int i = 0; i < in.length(); i++) {
                char c = in.charAt(i);
                indexes[i] = boardString.indexOf(c);
            }

            Arrays.sort(indexes);
            String arrayString = Arrays.toString(indexes);

            switch(arrayString) {
                case "[2, 5]" -> {
                    return 1;
                }
                case "[0, 2, 3, 4, 6]" -> {
                    return 2;
                }
                case "[0, 2, 3, 5, 6]" -> {
                    return 3;
                }
                case "[1, 2, 3, 5]" -> {
                    return 4;
                }
                case "[0, 1, 3, 5, 6]" -> {
                    return 5;
                }
                case "[0, 1, 3, 4, 5, 6]" -> {
                    return 6;
                }
                case "[0, 2, 5]" -> {
                    return 7;
                }
                case "[0, 1, 2, 3, 4, 5, 6]" -> {
                    return 8;
                }
                case "[0, 1, 2, 3, 5, 6]" -> {
                    return 9;
                }
                default -> {
                    return 0;
                }
            }
        }

        private String removeChars(String s, String chars) {
            String res = "";
            char[] charArray = s.toCharArray();
            for (char c : charArray) {
                if (!chars.contains(c+"")) {
                    res += c;
                }
            }

            return res;
        }

        private boolean containsSameCharsAndHasSameSize(String s1, String s2) {
            if(s1.length() != s2.length()) return false;

            char[] charArray = s1.toCharArray();
            for (char c : charArray) {
                if (!s2.contains(c + "")) {
                    return false;
                }
            }

            return true;
        }
    }

    public class Possibility {
        char[] poss;

        public Possibility(char[] poss) {
            this.poss = poss;
        }

        @Override
        public String toString() {
            return  "\n " + poss[0] + poss[0] + poss[0] + "\n" +
                    poss[1] + "   " + poss[2] + "\n" +
                    poss[1] + "   " + poss[2] + "\n" +
                    " " + poss[3] + poss[3] + poss[3] + "\n" +
                    poss[4] + "   " + poss[5] + "\n" +
                    poss[4] + "   " + poss[5] + "\n" +
                    " " + poss[6] + poss[6] + poss[6] + "\n";
        }

        public String allChars() {
            return new String(poss).replaceAll("\u0000", "");
        }

        public Possibility clone() {
            return new Possibility(this.poss.clone());
        }
    }
}