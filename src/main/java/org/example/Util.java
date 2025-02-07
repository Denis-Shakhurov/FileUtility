package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

    public Path fileToPath(String fileName) {
        Path path = Paths.get(fileName).toAbsolutePath().normalize();

        if (Files.notExists(path)) {
            try {
                throw new Exception("File not exists");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }

    public boolean exists(Path path) {
        return Files.exists(Paths.get(path.toString()));
    }
}
