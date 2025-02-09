package org.example;

import java.util.List;
import java.util.Map;

public class StatisticFile {
    private final ParserFile parserFile;
    private final Map<String, List<String>> mapStatistic;

    public StatisticFile(ParserFile parserFile) {
        this.parserFile = parserFile;
        mapStatistic = parserFile.getMap();
    }

    public void getBriefStatistics() {

        for (String key : mapStatistic.keySet()) {

            if (mapStatistic.get(key) != null) {
                int count = mapStatistic.get(key).size();
                System.out.printf("In file \"%s\" added %d elements.\n", key, count);
            }
        }
    }

    public void getFullStatistics() {

        for (String key : mapStatistic.keySet()) {

            if (mapStatistic.get(key) != null && key.contains("strings")) {
                int count = mapStatistic.get(key).size();

                List<Integer> list = mapStatistic.get(key).stream()
                        .map(String::length)
                        .toList();
                int minLength = list.stream().min(Integer::compareTo).orElse(0);
                int maxLength = list.stream().max(Integer::compareTo).orElse(0);

                System.out.printf("In file \"%s\" added %d lines.\n\t" +
                        "Min length line: %d\n\t" +
                        "Max length line: %d\n", key, count, minLength, maxLength);
            } else if (mapStatistic.get(key) != null && key.contains("integers")) {
                int count = mapStatistic.get(key).size();

                List<Long> list = mapStatistic.get(key).stream()
                        .map(Long::parseLong)
                        .toList();

                long min = list.stream().min(Long::compareTo).orElse(0L);
                long max = list.stream().max(Long::compareTo).orElse(0L);
                long sum = list.stream().reduce(Long::sum).orElse(0L);
                long avg = count != 0 ? sum / count : 0;

                System.out.printf("In file \"%s\" added %d numbers.\n\t" +
                        "Min number: %d\n\t" +
                        "Max number: %d\n\t" +
                        "Sum numbers: %d\n\t" +
                        "Avg numbers %d\n", key, count, min, max, sum, avg);
            } else if (mapStatistic.get(key) != null && key.contains("floats")) {
                int count = mapStatistic.get(key).size();

                List<Float> list = mapStatistic.get(key).stream()
                        .map(Float::parseFloat)
                        .toList();

                float min = list.stream().min(Float::compareTo).orElse(0f);
                float max = list.stream().max(Float::compareTo).orElse(0f);
                float sum = list.stream().reduce(Float::sum).orElse(0f);
                float avg = count != 0 ? sum / count : 0;

                System.out.printf("In file \"%s\" added %d numbers.\n\t" +
                        "Min number: %f\n\t" +
                        "Max number: %f\n\t" +
                        "Sum numbers: %f\n\t" +
                        "Avg numbers: %f\n", key, count, min, max, sum, avg);
            }
        }
    }
}
