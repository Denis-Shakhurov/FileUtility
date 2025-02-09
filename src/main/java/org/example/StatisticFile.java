package org.example;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatisticFile {
    private final ParserFile parserFile;

    private final String INTEGERS = "integers";
    private final String FLOATS = "floats";
    private final String STRINGS = "strings";

    private final Map<String, Map<String, String>> mapStatistic = new HashMap<>();

    public StatisticFile(ParserFile parserFile) {
        this.parserFile = parserFile;
    }

    public void printStatistics(boolean isBriefStatistics, boolean isFullStatistics) {
        getStatistics();

        if (isBriefStatistics) {
            mapStatistic.forEach((k, v) -> {
                System.out.printf("In file \"%s\" added %s elements.\n", k, v.get("count"));
            });
        }

        if (isFullStatistics) {
            mapStatistic.forEach((k, v) -> {
                System.out.printf("In file \"%s\" added %s elements.\n", k, v.get("count"));
                v.forEach((k2, v2) -> {
                    if (!k2.equals("count")) {
                        System.out.println("\t" + k2 + ": " + v2);
                    }
                });
            });
        }
    }

    private void getStatistics() {
        Map<String, List<String>> map = parserFile.getMap();

        for (String key : map.keySet()) {

            if (map.get(key) != null && key.contains(STRINGS)) {
                mapStatistic.put(key, new LinkedHashMap<>());

                int count = map.get(key).size();

                List<Integer> list = map.get(key).stream()
                        .map(String::length)
                        .toList();
                int minLength = list.stream().min(Integer::compareTo).orElse(0);
                int maxLength = list.stream().max(Integer::compareTo).orElse(0);

                mapStatistic.get(key).put("count", String.valueOf(count));
                mapStatistic.get(key).put("Min length", String.valueOf(minLength));
                mapStatistic.get(key).put("Max length", String.valueOf(maxLength));

            } else if (map.get(key) != null && key.contains(INTEGERS)) {
                mapStatistic.put(key, new LinkedHashMap<>());

                int count = map.get(key).size();

                List<Long> list = map.get(key).stream()
                        .map(Long::parseLong)
                        .toList();

                long min = list.stream().min(Long::compareTo).orElse(0L);
                long max = list.stream().max(Long::compareTo).orElse(0L);
                long sum = list.stream().reduce(Long::sum).orElse(0L);
                long avg = count != 0 ? sum / count : 0;

                mapStatistic.get(key).put("count", String.valueOf(count));
                mapStatistic.get(key).put("Min number", String.valueOf(min));
                mapStatistic.get(key).put("Max number", String.valueOf(max));
                mapStatistic.get(key).put("Sum number", String.valueOf(sum));
                mapStatistic.get(key).put("Avg number", String.valueOf(avg));

            } else if (map.get(key) != null && key.contains(FLOATS)) {
                mapStatistic.put(key, new LinkedHashMap<>());

                int count = map.get(key).size();

                List<Float> list = map.get(key).stream()
                        .map(Float::parseFloat)
                        .toList();

                float min = list.stream().min(Float::compareTo).orElse(0f);
                float max = list.stream().max(Float::compareTo).orElse(0f);
                float sum = list.stream().reduce(Float::sum).orElse(0f);
                float avg = count != 0 ? sum / count : 0;

                mapStatistic.get(key).put("count", String.valueOf(count));
                mapStatistic.get(key).put("Min number", String.valueOf(min));
                mapStatistic.get(key).put("Max number", String.valueOf(max));
                mapStatistic.get(key).put("Sum number", String.valueOf(sum));
                mapStatistic.get(key).put("Avg number", String.valueOf(avg));
            }
        }
    }
}
