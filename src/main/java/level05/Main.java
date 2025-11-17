package level05;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static Map<String, Integer> avoidAsteroidMap = new HashMap<>();

    public static void main(String[] args) {
        String level = "level5";
        String example = "level5_0_example.in";
        String outputFileName = "example.out";
        String outputFilePath = "src/main/resources/" + level + "/" + outputFileName;

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

        for (int i = 1; i <= numberOfLines; i += 2) {
            var locationAndTime = inputLines.get(i).split(" ");
            var asteroid = inputLines.get(i + 1).split(" ");

            int spaceStationX = Integer.parseInt(locationAndTime[0].split(",")[0]);
            int spaceStationY = Integer.parseInt(locationAndTime[0].split(",")[1]);
            int time = Integer.parseInt(locationAndTime[1]);

            int asteroidX = Integer.parseInt(asteroid[0].split(",")[0]);
            int asteroidY = Integer.parseInt(asteroid[0].split(",")[1]);

            boolean isYAxis = avoidAsteroidOnAxis(asteroidX, asteroidY, spaceStationX, spaceStationY);

            var xSequence = "0";
            var ySequence = "0";

            // Step 1
            if (isYAxis) {
                ySequence += handleLocation(avoidAsteroidMap.get("y"), time);
                int numberOfMoves = ySequence.split(" ").length - 1;

                for (int j = 0; j < numberOfMoves; j++) {
                    xSequence += " 0";
                }

                // Step 2
                xSequence += handleLocation(spaceStationX, time);

                // Step 3
                if (avoidAsteroidMap.get("y") != asteroidY) {
                    numberOfMoves = ySequence.split(" ").length - xSequence.split(" ").length;

                    for (int j = 0; j < numberOfMoves; j++) {
                        xSequence += " 0";
                    }

                    ySequence += handleLocation(-avoidAsteroidMap.get("y"), time);
                }
            } else {
                xSequence += handleLocation(avoidAsteroidMap.get("x"), time);

                int numberOfMoves = ySequence.split(" ").length - 1;

                for (int j = 0; j < numberOfMoves; j++) {
                    ySequence += " 0";
                }

                // Step 2
                xSequence += handleLocation(spaceStationX, time);

                // Step 3
                if (avoidAsteroidMap.get("x") != asteroidX) {
                    numberOfMoves = xSequence.split(" ").length - ySequence.split(" ").length;

                    for (int j = 0; j < numberOfMoves; j++) {
                        ySequence += " 0";
                    }

                    xSequence += handleLocation(-avoidAsteroidMap.get("x"), time);
                }
            }

            xSequence += " 0";
            ySequence += " 0";


            outputLines.add(xSequence);
            outputLines.add(ySequence);
            outputLines.add("");
        }

        return outputLines;
    }

    /**
     *
     * @param asteroidX
     * @param asteroidY
     * @param spaceStationX
     * @param spaceStationY
     * @return true = Y, false = X
     */
    private static boolean avoidAsteroidOnAxis(int asteroidX, int asteroidY, int spaceStationX, int spaceStationY) {
        if (asteroidY == spaceStationY) {
            avoidAsteroidMap.put("y", 3);
            return true;
        } else if (asteroidY + 1 == spaceStationY) {
            avoidAsteroidMap.put("y", 2);
            return true;
        } else if (asteroidY - 1 == spaceStationY) {
            avoidAsteroidMap.put("y", -2);
            return true;
        } else if (asteroidY + 2 == spaceStationY) {
            avoidAsteroidMap.put("y", 1);
            return true;
        } else if (asteroidY - 2 == spaceStationY) {
            avoidAsteroidMap.put("y", -1);
            return true;
        }

        if (asteroidX == spaceStationX) {
            avoidAsteroidMap.put("x", 3);
            return false;
        } else if (asteroidX + 1 == spaceStationX) {
            avoidAsteroidMap.put("x", 2);
            return false;
        } else if (asteroidX - 1 == spaceStationX) {
            avoidAsteroidMap.put("x", -2);
            return false;
        } else if (asteroidX + 2 == spaceStationX) {
            avoidAsteroidMap.put("x", 1);
            return false;
        } else if (asteroidX - 2 == spaceStationX) {
            avoidAsteroidMap.put("x", -1);
            return false;
        }

        avoidAsteroidMap.put("x", spaceStationX);
        return false;
    }

    private static String handleLocation(int location, int time) {
        if (location == 0) {
            return "0 0";
        }

        var sequence = "";
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

        return sequence;
    }
}
