package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 extends Solution {
    @Getter private final int day = 4;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{29440, 13884};
    }

    @Override
    protected void run(List<String> input) {
        //TODO write implementation
        String[] numbersToBePulled = input.get(0).split(",");

        for (int i = 2; i < input.size(); i += 6) {
            List<String> boardStrings = new ArrayList<>();

            for(int x = 0; x < 5; x++)
                boardStrings.add(input.get(i + x));

            new BingoBoard(boardStrings);
        }


        for (String num : numbersToBePulled) {
            BingoBoard.guess(num);

            BingoBoard winner = BingoBoard.findWinningBoard();
            if(winner != null) {
                int ans = Integer.parseInt(num);
                a = a == 0 ? (long) winner.uncheckedSum() * ans : a;
                BingoBoard.allBoards.remove(winner);
                System.out.println(ans);
                b = (long) winner.uncheckedSum() * ans;

                BingoBoard.findAllWinningBoards().forEach(BingoBoard.allBoards::remove);
            }
        }
    }

    class BingoBoard {
        private BoardEntry[][] board = new BoardEntry[5][5];
        boolean won = false;

        public static List<BingoBoard> allBoards = new ArrayList<>();

        public BingoBoard(List<String> boardStrings) {
            System.out.println(boardStrings);
            for (int line = 0; line < boardStrings.size(); line++) {
                String[] nums = boardStrings.get(line)
                        .trim()
                        .replaceAll(" +", " ")
                        .split(" ");

                for (int row = 0; row < boardStrings.size(); row++) {
                    board[line][row] = new BoardEntry(nums[row], false);
                }
            }

            allBoards.add(this);
        }

        public static void guess(String in) {
            allBoards.forEach(bingoBoard -> bingoBoard.guessMe(in));
        }

        public void guessMe(String in) {
            for (BoardEntry[] line : board) {
                for (BoardEntry entry : line) {
                    if (entry.val.equals(in)) {
                        entry.found = true;
                    }
                }
            }

            boolean sum = checkWin();
            if (sum) {
                won = true;
            }
        }

        /**
         * Function that checks when if this board is the winner
         * @return the sum of the winning numbers, if not the winner -1
         */
        public boolean checkWin() {
            // Check lines
            for (BoardEntry[] line : board) {
                boolean corr = true;

                for (BoardEntry entry : line) {
                    if (!entry.found) {
                        corr = false;
                    }
                }
                if(corr) {
//                    System.out.println(Arrays.asList(line));
                    return true;
                }
            }

            // Check rows
            for (int x = 0; x < board[0].length; x++) {
                boolean corr = true;
                for (int y = 0; y < board[0].length; y++) {
                    BoardEntry entry = board[y][x];

                    if (!entry.found) {
                        corr = false;
                    }
                }
                if(corr) {
                    return true;
                }
            }

            return false;
        }

        public int uncheckedSum() {
            int res = 0;
            for (BoardEntry[] line : board) {
                for (BoardEntry entry : line) {
                    if(!entry.found) res += Integer.parseInt(entry.val);
                }
            }

            return res;
        }

        public static BingoBoard findWinningBoard() {
            return allBoards.stream()
                    .filter(bingoBoard -> bingoBoard.won)
                    .findFirst()
                    .orElse(null);
        }

        public static List<BingoBoard> findAllWinningBoards() {
            return allBoards.stream()
                    .filter(bingoBoard -> bingoBoard.won)
                    .collect(Collectors.toList());
        }
    }

    class BoardEntry {
        public String val;
        public boolean found;

        public BoardEntry(String val, boolean found) {
            this.val = val;
            this.found = found;
        }

        @Override
        public String toString() {
            return val + (found ? "!" : "");
        }
    }
}