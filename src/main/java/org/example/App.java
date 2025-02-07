package org.example;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {

    @Parameter(names = {"-o"}, description = "Path for create files")
    private String path;

    @Parameter(names = {"-p"}, description = "Add prefix for name files")
    private String prefix;

    @Parameter(names = {"-a"}, description = "Append in files")
    private boolean append = false;

    @Parameter(names = {"-s"}, description = "Brief statistics")
    private boolean briefStatistics = false;

    @Parameter(names = {"-f"}, description = "Full statistics")
    private boolean fullStatistics = false;

    @Parameter(description = "Input files")
    private List<String> files = new ArrayList<>();

    public static void main(String[] args) throws URISyntaxException, IOException {
        App app = new App();
        JCommander.newBuilder().addObject(app).build().parse(args);

        ParserFile parserFile = new ParserFile();
        StatisticFile statisticFile = new StatisticFile(parserFile);

        for (String fileName : app.files) {
            parserFile.parse(fileName);
            parserFile.getMap().forEach((k, v) -> {
                System.out.println(k);
                System.out.println(v);
            });
        }

        if (app.briefStatistics) {
            statisticFile.getBriefStatistics(app.prefix);
        }

        if (app.fullStatistics) {
            statisticFile.getFullStatistics(app.prefix);
        }
    }
}
