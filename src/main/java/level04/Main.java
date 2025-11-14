package level04;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var level = "level4";
        var example = "level4_2_large.in";
        var outputFileName = "example.out";
        var outputFilePath = "src/main/resources/" + level + "/" + outputFileName;

        var classLoader = Thread.currentThread().getContextClassLoader();
        var fileUrl = classLoader.getResource(level + "/" + example);

        if (fileUrl == null) {
            System.out.println("Error: Resource file " + level + "/" + example + " not found.");
            return;
        }

        List<String> inputLines;
        try {
            File inputFile = new File(fileUrl.toURI());
            inputLines = Files.readAllLines(inputFile.toPath(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        List<String> output = processLines(inputLines);

        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            for (String line : output) {
                writer.println(line);
            }
            System.out.println("Data written to output.txt successfully.");
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static List<String> processLines(List<String> inputLines) {
        var numberOfLines = Integer.parseInt(inputLines.getFirst());
        var outputLines = new ArrayList<String>();

        for (int i = 1; i <= numberOfLines; i++) {
            var line = inputLines.get(i);

            var locationAndTime = line.split(" ");

            var locationX = Integer.parseInt(locationAndTime[0].split(",")[0]);
            var locationY = Integer.parseInt(locationAndTime[0].split(",")[1]);
            var time = Integer.parseInt(locationAndTime[1]);
            var xSequence = handleLocation(locationX, time);
            var ySequence = handleLocation(locationY, time);

            outputLines.add(xSequence);
            outputLines.add(ySequence);
            outputLines.add("");
        }

        return outputLines;
    }

    private static String handleLocation(int location, int time) {
        if (location == 0) {
            return "0 0";
        }

        var sequence = "0";
        var modifier = 1;
        var startPace = 5;
        var lastPace = 0;

        if (location < 0) {
            modifier = -1;
        }

        if (location % 2 == 0) {
            for (int i = location * modifier; i >= 2; i -= 2) {
                if (startPace == 0) {
                    for (int j = i; j >= 1; j--) {
                        sequence += " " + lastPace * modifier;
                    }

                    break;
                }

                if (time / startPace > 0) {
                    sequence += " " + startPace * modifier;
                    lastPace = startPace;
                    startPace--;
                }
            }

            for (int i = lastPace; i <= 5; i++) {
                sequence += " " + i * modifier;
            }
        } else {
            for (int i = location * modifier; i >= 1; i -= 2) {
                if (startPace == 0) {
                    for (int j = i; j >= 0; j--) {
                        sequence += " " + lastPace * modifier;
                    }

                    break;
                }

                if (time / startPace > 0) {
                    sequence += " " + startPace * modifier;
                    lastPace = startPace;
                    startPace--;
                }
            }

            lastPace++;

            for (int i = lastPace; i <= 5; i++) {
                sequence += " " + i * modifier;
            }
        }

        sequence += " 0";

        return sequence;
    }
}
