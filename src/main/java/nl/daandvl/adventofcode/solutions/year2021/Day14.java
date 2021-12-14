package nl.daandvl.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.*;

public class Day14 extends Solution {
    @Getter private final int day = 14;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

    @Override
    protected void run(List<String> input) {
        String template = input.get(0);

        // Add Rules
        for (String line : input) {
            if (line.contains("->")) {
                new Rule(line);
            }
        }

        // List of molecule pairs
        Map<String, Long> groups = new HashMap<>();
        // List of 'ghost' pairs, to be used in the next calculation
        Map<String, Long> ghostGroups = new HashMap<>();

        // Add first template
        for (int i = 0; i < template.length() - 1; i++) {
            String pair = template.substring(i, i+2);
            groups.put(pair, groups.getOrDefault(pair, 0L) + 1L);
        }

        // Calculate new groups and ghostgroups
        for (int i = 0; i < 40; i++) {
            Map<String, Long> newGroups = new HashMap<>();
            Map<String, Long> newGhostGroups = new HashMap<>();

            Map<String, Long> clone = new HashMap<>(groups);

            for (Map.Entry<String, Long> ghost : ghostGroups.entrySet()) {
                clone.put(ghost.getKey(), clone.getOrDefault(ghost.getKey(), 0L) + ghost.getValue());
            }

            for (Map.Entry<String, Long> group : clone.entrySet()) {
                Rule groupRule = Rule.findRule(group.getKey());

                String[] res = groupRule.out();

                newGroups.put(res[0], newGroups.getOrDefault(res[0], 0L) + group.getValue());
                newGhostGroups.put(res[1], newGhostGroups.getOrDefault(res[1], 0L) + group.getValue());
            }

            groups = newGroups;
            ghostGroups = newGhostGroups;

            // On the 10th gen, calculate the amount of chars
            if(i == 9) a = getAnswer(groups);
        }

        b = getAnswer(groups);
    }

    public long getAnswer(Map<String, Long> groups) {
        LinkedHashMap<Character, Long> map = (LinkedHashMap<Character, Long>) MapUtils.sortByValue(countChars(groups));

        List<Long> l = new ArrayList<>(map.values());

        return l.get(l.size() - 1) - l.get(0);
    }

    public Map<Character, Long> countChars(Map<String, Long> groups) {
        Map<Character, Long> charMap = new HashMap<>();

        for (Map.Entry<String, Long> group : groups.entrySet()) {
            char[] chars = group.getKey().toCharArray();
            for(char c : chars) {
                charMap.put(c, charMap.getOrDefault(c, 0L) + group.getValue());
            }
        }

        charMap.put('B', charMap.getOrDefault('B', 0L) + 1);

        return charMap;
    }

    public class Rule {
        private String in;
        private String add;

        public static List<Rule> rules = new ArrayList<>();

        public Rule(String rule) {
            String[] ruleParts = rule.split(" -> ");
            this.in = ruleParts[0];
            this.add = ruleParts[1];

            rules.add(this);
        }

        public String[] out() {
            String[] out = new String[2];
            out[0] = in.charAt(0) + add;
            out[1] = add + in.charAt(1);

            return out;
        }

        public static Rule findRule(String in) {
            return rules.stream()
                    .filter(r -> r.in.equals(in))
                    .findFirst().orElse(null);
        }

        @Override
        public String toString() {
            return in + " -> " + add;
        }
    }
}