package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class CreateFile {
    private final ParserFile parserFile;

    public CreateFile(ParserFile parserFile) {
        this.parserFile = parserFile;
    }

    public void create(List<String> files, String path, boolean append) throws IOException {
        Util util = new Util();
        Path pathFile = util.fileToPath(files.get(0));
        Path parent = path == null ? pathFile.getParent() : Paths.get(path);

        Map<String, List<String>> map = parserFile.getMap();

        for (String key : map.keySet()) {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            String newFileName = key + ".txt";
            Path newPathFile = parent.resolve(newFileName);

            if (!util.exists(newPathFile)) {
                Files.createFile(newPathFile);
            }

            writeToFile(map.get(key), newPathFile, append);
        }
    }

    private void writeToFile(List<String> list, Path path, boolean append) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String s : list) {
            joiner.add(s);
        }

        String text = joiner.toString();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile(), append))) {
            bw.write(text);
            bw.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
