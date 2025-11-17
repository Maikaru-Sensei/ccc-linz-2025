package level01;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String level = "level1";
        String example = "level1_0_example.in";
        String outputFileName = "level1_1.out";
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

           var numbers = line.split(" ");

           var sum = 0;

           for (String number : numbers) {
               sum += Integer.parseInt(number);
           }

           outputLines.add(sum + "");
       }

       return outputLines;
    }
}
