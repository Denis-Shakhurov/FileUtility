package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserFile {
    private final Map<String, List<String>> map = new HashMap<>();
    private final Util util = new Util();

    public Map<String, List<String>> getMap() {
        return map;
    }

    public void parse(List<String> files, String prefix) {
        String fileIntegers = prefix != null ? prefix + "integers" : "integers";
        String fileStrings = prefix != null ? prefix + "strings" : "strings";
        String fileFloats = prefix != null ? prefix + "floats" : "floats";

        map.put(fileIntegers, new ArrayList<>());
        map.put(fileStrings, new ArrayList<>());
        map.put(fileFloats, new ArrayList<>());

        for (String file : files) {
            Path path = util.fileToPath(file);

            List<String> lines = null;
            try {
                lines = Files.readAllLines(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (String line : lines) {
                if (isLong(line)) {
                    map.get(fileIntegers).add(line);
                } else if (isFloat(line)) {
                    map.get(fileFloats).add(line);
                } else {
                    map.get(fileStrings).add(line);
                }
            }

            for (String key : map.keySet()) {
                if (map.get(key).isEmpty()) {
                    map.remove(key);
                }
            }
        }
    }

    private boolean isLong(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String line) {
        try {
            Float.parseFloat(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
