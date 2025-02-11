package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateFileTest {
    private ParserFile parserFile;
    private CreateFile createFile;

    @BeforeEach
    void setUp() {
        parserFile = mock(ParserFile.class);
        createFile = new CreateFile(parserFile);
    }

    @Test
    @DisplayName("Create files with valid input files and path, writing data from map")
    public void createFilesWithValidInput() {
        CreateFile create = mock(CreateFile.class);
        Util util = mock(Util.class);

        List<String> inputFiles = List.of("src/test/resources/test.txt");
        Map<String, List<String>> testMap = new HashMap<>();
        testMap.put("integers.txt", List.of("1", "2"));
        testMap.put("strings.txt", List.of("a", "b"));

        when(parserFile.getMap()).thenReturn(testMap);

        String testPath = "src/test/resources/test/path";
        Path expectedPath = Paths.get(testPath);
        when(util.fileToPath(testPath)).thenReturn(expectedPath);
        when(util.fileToPath(inputFiles.get(0))).thenReturn(expectedPath);
        create.create(inputFiles, testPath, false);
    }

    @Test
    @DisplayName("Handle empty input files list")
    public void createWithEmptyFilesListTest() {
        List<String> emptyFiles = new ArrayList<>();
        String testPath = "test/path";

        createFile.create(emptyFiles, testPath, false);

        verify(parserFile, never()).getMap();
        Path expectedPath = Paths.get(testPath);
        assertFalse(Files.exists(expectedPath));
    }
}
