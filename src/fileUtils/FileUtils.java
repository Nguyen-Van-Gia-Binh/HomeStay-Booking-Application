package fileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * FileUtils class for file operations
 */
public class FileUtils {

    /**
     * Write a list of objects to a file
     * 
     * @param <T>      The type of objects in the list
     * @param fileName The name of the file to write to
     * @param list     The list of objects to write
     */
    public static <T> void writeToFile(String fileName, List<T> list) {
        // Write a list of objects to a file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T item : list) {
                bw.write(item.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * Read a list of objects from a file
     * 
     * @param <T>      The type of objects in the list
     * @param fileName The name of the file to read from
     * @param parser   The function to parse each line of the file
     * @return The list of objects read from the file
     */
    public static <T> List<T> readFromFile(String fileName, Function<String, T> parser) {
        // Read a list of objects from a file
        List<T> list = new ArrayList<>();
        // Check if the file exists
        File file = new File(fileName);
        if (!file.exists()) {
            return list;
        }

        // Read the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty())
                    continue;
                // Parse each line
                try {
                    T item = parser.apply(line);
                    // Add the parsed object to the list
                    if (item != null) {
                        list.add(item);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file " + fileName + ": " + e.getMessage());
        }
        return list;
    }
}
