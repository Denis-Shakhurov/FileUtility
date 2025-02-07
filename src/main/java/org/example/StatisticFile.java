package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticFile {
    private final ParserFile parserFile;

    public StatisticFile(ParserFile parserFile) {
        this.parserFile = parserFile;
    }

    public void getBriefStatistics(String prefix) {
        Map<String, List<String>> map = parserFile.getMap();

        for (String key : map.keySet()) {
            String fullNameFile = prefix == null ? key : prefix + key;
            if (map.get(key) != null) {
                int count = map.get(key).size();
                System.out.printf("В файл \"%s\" заисано: %d элементов.\n", fullNameFile, count);
            }
        }
    }

    public void getFullStatistics(String prefix) {
        Map<String, List<String>> map = parserFile.getMap();

        for (String key : map.keySet()) {
            String fullNameFile = prefix == null ? key : prefix + key;
            if (map.get(key) != null && key.equals("strings")) {
                int count = map.get(key).size();

                List<Integer> list = map.get(key).stream()
                        .map(String::length)
                        .sorted()
                        .toList();
                int minLength = list.stream().min(Integer::compareTo).orElse(0);
                int maxLength = list.stream().max(Integer::compareTo).orElse(0);

                System.out.printf("В файл \"%s\" заисано: %d строк.\n\t" +
                        "Минимальная длина строки: %d\n\t" +
                        "Максимальная длина строки: %d\n", fullNameFile, count, minLength, maxLength);
            } else if (map.get(key) != null && key.equals("integers")) {
                int count = map.get(key).size();

                List<Long> list = map.get(key).stream()
                        .map(Long::parseLong)
                        .toList();

                long min = list.stream().min(Long::compareTo).orElse(0L);
                long max = list.stream().max(Long::compareTo).orElse(0L);
                long sum = list.stream().reduce(Long::sum).orElse(0L);
                long avg = count != 0 ? sum / count : 0;

                System.out.printf("В файл \"%s\" заисано: %d целых чисел.\n\t" +
                        "Минимальное число: %d\n\t" +
                        "Максимальное число: %d\n\t" +
                        "Суииа чисел: %d\n\t" +
                        "Среднее: %d\n", fullNameFile, count, min, max, sum, avg);
            } else if (map.get(key) != null && key.equals("floats")) {
                int count = map.get(key).size();

                List<Float> list = map.get(key).stream()
                        .map(Float::parseFloat)
                        .toList();

                float min = list.stream().min(Float::compareTo).orElse(0f);
                float max = list.stream().max(Float::compareTo).orElse(0f);
                float sum = list.stream().reduce(Float::sum).orElse(0f);
                float avg = count != 0 ? sum / count : 0;

                System.out.printf("В файл \"%s\" заисано: %d вещественных чисел.\n\t" +
                        "Минимальное число: %f\n\t" +
                        "Максимальное число: %f\n\t" +
                        "Суииа чисел: %f\n\t" +
                        "Среднее: %f\n", fullNameFile, count, min, max, sum, avg);
            }
        }
    }

//    private void printStatistics(List<String> list) {
//        List<Number> numbers = new ArrayList<>();
//        numbers.addAll(list.stream().map().collect(Collectors.toList()));
//    }
}
