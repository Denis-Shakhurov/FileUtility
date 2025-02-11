package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatisticFileTest {
    private ParserFile parserFile;
    private StatisticFile statisticFile;

    @BeforeEach
    void setUp() {
        parserFile = mock(ParserFile.class);
        statisticFile = new StatisticFile(parserFile);
    }

    @Test
    @DisplayName("Print brief statistics showing only count of elements for each file type")
    public void briefStatisticsPrintsElementCountsTest() {
        Map<String, List<String>> testMap = new HashMap<>();
        testMap.put("integers.txt", List.of("1", "2", "3"));
        testMap.put("strings.txt", List.of("a", "b"));
        when(parserFile.getMap()).thenReturn(testMap);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        statisticFile.printStatistics(true, false);

        String expectedOutput = "In file \"strings.txt\" added 2 elements.\n" +
                "In file \"integers.txt\" added 3 elements.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @DisplayName("Print full statistics showing all metrics for each file type")
    public void fullStatisticsPrintsAllMetricsTest() {
        Map<String, List<String>> testMap = new HashMap<>();
        testMap.put("integers.txt", List.of("1", "2", "3"));
        testMap.put("floats.txt", List.of("1.0", "2.5", "3.5"));
        testMap.put("strings.txt", List.of("a", "bb", "ccc"));
        when(parserFile.getMap()).thenReturn(testMap);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        statisticFile.printStatistics(false, true);

        String expectedOutput =
                "In file \"strings.txt\" added 3 elements.\n" +
                        "\tMin length: 1\n" +
                        "\tMax length: 3\n" +
                        "In file \"floats.txt\" added 3 elements.\n" +
                        "\tMin number: 1.0\n" +
                        "\tMax number: 3.5\n" +
                        "\tSum number: 7.0\n" +
                        "\tAvg number: 2.3333333\n" +
                        "In file \"integers.txt\" added 3 elements.\n" +
                        "\tMin number: 1\n" +
                        "\tMax number: 3\n" +
                        "\tSum number: 6\n" +
                        "\tAvg number: 2\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @DisplayName("Handle empty input map from ParserFile")
    public void emptyMapPrintsNothingTest() {
        Map<String, List<String>> emptyMap = new HashMap<>();
        when(parserFile.getMap()).thenReturn(emptyMap);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        statisticFile.printStatistics(true, false);

        assertEquals("", outContent.toString());
    }

    @Test
    @DisplayName("Handle null values in the input map")
    public void handleNullValuesInInputMapTest() {
        Map<String, List<String>> testMap = new HashMap<>();
        testMap.put("integers.txt", null);
        testMap.put("strings.txt", null);
        testMap.put("floats.txt", null);
        when(parserFile.getMap()).thenReturn(testMap);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        statisticFile.printStatistics(true, true);

        String expectedOutput = "";
        assertEquals(expectedOutput, outContent.toString());
    }
}
