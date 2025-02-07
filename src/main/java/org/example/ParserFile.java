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

    public Map<String, List<String>> parse(String fileName) {

        map.put("integers", new ArrayList<>());
        map.put("strings", new ArrayList<>());
        map.put("floats", new ArrayList<>());

        Path path = util.fileToPath(fileName);

        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String line : lines) {
            if (isLong(line)) {
                map.get("integers").add(line);
            } else if (isFloat(line)) {
                map.get("floats").add(line);
            } else {
                map.get("strings").add(line);
            }
        }

        for (String key : map.keySet()) {
            if (map.get(key).isEmpty()) {
                map.remove(key);
            }
        }

        return map;
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
