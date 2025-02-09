package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class CreateFile {
    private final ParserFile parserFile;

    public CreateFile(ParserFile parserFile) {
        this.parserFile = parserFile;
    }

    public void create(List<String> files, String path, boolean isAppend) {
        if (!files.isEmpty()) {
            Util util = new Util();
            Path pathFile = util.fileToPath(files.get(0));
            Path parent = path == null ? pathFile.getParent() : Paths.get(path);

            Map<String, List<String>> map = parserFile.getMap();

            for (String key : map.keySet()) {
                util.createDirectory(parent);

                String newFileName = key + ".txt";
                Path newPathFile = parent.resolve(newFileName);

                util.createFile(newPathFile);

                util.writeToFile(map.get(key), newPathFile, isAppend);
            }
        }
    }
}
