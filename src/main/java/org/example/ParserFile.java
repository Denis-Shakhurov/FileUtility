package org.example;

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

        if (!files.isEmpty()) {

            for (String file : files) {
                Path path = util.fileToPath(file);

                List<String> lines = util.readFromFile(path);

                for (String line : lines) {
                    if (isLong(line)) {
                        map.get(fileIntegers).add(line);
                    } else if (isFloat(line)) {
                        map.get(fileFloats).add(line);
                    } else {
                        map.get(fileStrings).add(line);
                    }
                }
            }

            map.entrySet()
                    .removeIf(entry -> entry.getValue().isEmpty());

        } else {
            System.out.println("File name not specified! Provide a file name and try again.");
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
