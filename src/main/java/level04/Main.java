package level04;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static Map<Integer, Integer> PACE_TO_SPACE = new HashMap<>();
    private static Map<Integer, Integer> PACE_TO_TIME = new HashMap<>();

    public static void main(String[] args) {
        String level = "level4";
        String example = "level4_2_large.in";
        String outputFileName = "example.out";
        String outputFilePath = "src/main/resources/" + level + "/" + outputFileName;


        PACE_TO_SPACE.put(-1, -1);
        PACE_TO_SPACE.put(-2, -1);
        PACE_TO_SPACE.put(-3, -1);
        PACE_TO_SPACE.put(-4, -1);
        PACE_TO_SPACE.put(-5, -1);
        PACE_TO_SPACE.put(0, 0);
        PACE_TO_SPACE.put(5, 1);
        PACE_TO_SPACE.put(4, 1);
        PACE_TO_SPACE.put(3, 1);
        PACE_TO_SPACE.put(2, 1);
        PACE_TO_SPACE.put(1, 1);

        PACE_TO_TIME.put(-1, 1);
        PACE_TO_TIME.put(-2, 2);
        PACE_TO_TIME.put(-3, 3);
        PACE_TO_TIME.put(-4, 4);
        PACE_TO_TIME.put(-5, 5);
        PACE_TO_TIME.put(0, 1);
        PACE_TO_TIME.put(5, 5);
        PACE_TO_TIME.put(4, 4);
        PACE_TO_TIME.put(3, 3);
        PACE_TO_TIME.put(2, 2);
        PACE_TO_TIME.put(1, 1);


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
        int numberOfLines = Integer.parseInt(inputLines.getFirst());
        var outputLines = new ArrayList<String>();

        for (int i = 1; i <= numberOfLines; i++) {
            var line = inputLines.get(i);

            var locationAndTime = line.split(" ");

            int locationX = Integer.parseInt(locationAndTime[0].split(",")[0]);
            int locationY = Integer.parseInt(locationAndTime[0].split(",")[1]);
            int time = Integer.parseInt(locationAndTime[1]);
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
                    for (int j = i; j >= 1 ; j--) {
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
                    for (int j = i; j >= 0 ; j--) {
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
