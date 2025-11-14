package level03;

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
        String level = "level3";
        String example = "level3_2_large.in";
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

            int location = Integer.parseInt(locationAndTime[0]);
            int time = Integer.parseInt(locationAndTime[1]);
            var sequence = "0";

            if (location > 0) {
                sequence = handlePositiveLocation(location, time, sequence);
            } else if (location < 0) {
                sequence = handleNegativeLocation(location, time, sequence);
            }


            sequence += " 0";

            outputLines.add(sequence);
        }

        return outputLines;
    }

    private static String handleNegativeLocation(int location, int time, String sequence) {
        location *= -1;
        int maxSteps = time / 5;
        if (maxSteps > location) {
            for (int i = 1; i <= location; i++) {
                sequence += " -5";
            }
        } else {
            sequence += " -5";
            location -= 2;
            time = time - 10;

            maxSteps = time / 4;
            if (maxSteps > location) {
                for (int i = 1; i <= location; i++) {
                    sequence += " -4";
                }
            } else {
                sequence += " -4";
                location -= 2;
                time = time - 8;

                maxSteps = time / 3;
                if (maxSteps > location) {
                    for (int i = 1; i <= location; i++) {
                        sequence += " -3";
                    }
                } else {
                    sequence += " -3";
                    location -= 2;
                    time = time - 6;

                    maxSteps = time / 2;
                    if (maxSteps > location) {
                        for (int i = 1; i <= location; i++) {
                            sequence += " -2";
                        }
                    } else {
                        sequence += " -2";
                        time = time - 4;

                        maxSteps = time;

                        for (int i = 0; i < maxSteps; i++) {
                            sequence += " -1";
                        }

                        sequence += " -2";
                    }

                    sequence += " -3";
                }

                sequence += " -4";
            }

            sequence += " -5";
        }

        return sequence;
    }

    private static String handlePositiveLocation(int location, int time, String sequence) {
        int maxSteps = time / 5;
        if (maxSteps > location) {
            for (int i = 1; i <= location; i++) {
                sequence += " 5";
            }
        } else {
            sequence += " 5";
            location -= 2;
            time = time - 10;

            maxSteps = time / 4;
            if (maxSteps > location) {
                for (int i = 1; i <= location; i++) {
                    sequence += " 4";
                }
            } else {
                sequence += " 4";
                location -= 2;
                time = time - 8;

                maxSteps = time / 3;
                if (maxSteps > location) {
                    for (int i = 1; i <= location; i++) {
                        sequence += " 3";
                    }
                } else {
                    sequence += " 3";
                    location -= 2;
                    time = time - 6;

                    maxSteps = time / 2;
                    if (maxSteps > location) {
                        for (int i = 1; i <= location; i++) {
                            sequence += " 2";
                        }
                    } else {
                        sequence += " 2";
                        time = time - 4;

                        maxSteps = time;

                        for (int i = 0; i < maxSteps; i++) {
                            sequence += " 1";
                        }

                        sequence += " 2";
                    }

                    sequence += " 3";
                }

                sequence += " 4";
            }

            sequence += " 5";
        }

        return sequence;
    }
}
