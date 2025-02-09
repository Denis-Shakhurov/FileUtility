package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Util {

    public Path fileToPath(String fileName) {
        return Paths.get(fileName).toAbsolutePath().normalize();
    }

    public void createDirectory(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createFile(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeToFile(List<String> list, Path path, boolean append) {
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

    public List<String> readFromFile(Path path) {
        List<String> list = new ArrayList<>();
        try {
            list = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println(e.getMessage() + " - File not exists or invalid file name");
        }
        return list;
    }
}
