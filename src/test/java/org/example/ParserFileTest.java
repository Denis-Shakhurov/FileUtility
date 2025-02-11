package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserFileTest {
    private ParserFile parserFile;

    @BeforeEach
    void setUp() {
        parserFile = new ParserFile();
    }

    @Test
    @DisplayName("Parse file containing mix of integers, floats and strings into separate categories")
    public void parseMixedContentFileTest() {
        List<String> files = List.of("src/test/resources/test.txt");

        parserFile.parse(files, null);

        Map<String, List<String>> result = parserFile.getMap();
        assertEquals(Arrays.asList("123", "999"), result.get("integers.txt"));
        assertEquals(Arrays.asList("45.67", "3.14"), result.get("floats.txt"));
        assertEquals(Arrays.asList("hello", "world"), result.get("strings.txt"));
    }

    @Test
    @DisplayName("Remove empty categories from final map")
    public void removeEmptyCategoriesTest() {
        List<String> files = List.of("src/test/resources/emptyTest.txt");

        parserFile.parse(files, null);

        Map<String, List<String>> result = parserFile.getMap();
        assertFalse(result.containsKey("integers.txt"));
        assertFalse(result.containsKey("floats.txt"));
        assertFalse(result.containsKey("strings.txt"));
    }

    @Test
    @DisplayName("Handle empty input files list")
    public void parseEmptyFilesListTest() {
        List<String> files = new ArrayList<>();

        parserFile.parse(files, null);

        Map<String, List<String>> result = parserFile.getMap();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Handle file with only one type of data")
    public void parseSingleTypeContentFileTest() {
        List<String> files = List.of("src/test/resources/integersTest.txt");

        parserFile.parse(files, null);

        Map<String, List<String>> result = parserFile.getMap();
        assertEquals(Arrays.asList("123", "456", "789"), result.get("integers.txt"));
        assertNull(result.get("floats.txt"));
        assertNull(result.get("strings.txt"));
    }
}
