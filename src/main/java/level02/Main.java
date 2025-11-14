package level02;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String level = "level2";
        String example = "level2_2_large.in";
        String outputFileName = "level2_2_large.out";
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

    // Adjust this to your actual implementation
    private static List<String> processLines(List<String> inputLines) {
       int numberOfLines = Integer.parseInt(inputLines.getFirst());
       var outputLines = new ArrayList<String>();

       for (int i = 1; i <= numberOfLines; i++) {
           var line = inputLines.get(i);

           var paces = line.split(" ");

           int spaceMoved = 0;
           int timeMoved = 0;

           for (String pace : paces) {
               int unitOfSpace = Integer.parseInt(pace);
               int unitOfTime = unitOfSpace;

               if (unitOfSpace > 0) {
                   unitOfSpace = 1;
               } else if (unitOfSpace < 0) {
                   unitOfSpace = -1;
               }

               spaceMoved += unitOfSpace;

               if (unitOfTime < 0) {
                   unitOfTime *= -1;
               }
               if (unitOfTime == 0) {
                   unitOfTime = 1;
               }

               timeMoved += unitOfTime;
           }

           outputLines.add(spaceMoved + " " + timeMoved);
       }

       return outputLines;
    }
}
