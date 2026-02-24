package FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileUtils {

    public static <T> void writeToFile(String fileName, List<T> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T item : list) {
                bw.write(item.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
        }
    }

    public static <T> List<T> readFromFile(String fileName, Function<String, T> parser) {
        List<T> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                try {
                    T item = parser.apply(line);
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
