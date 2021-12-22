import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessCSV {
    public static List process() {
        // Create new 2D list to hold CSV data
        List<List<String>> data = new ArrayList<>();

        // Try to read file
        try (BufferedReader br = new BufferedReader(new FileReader("src/covid-data.csv"))) {
            String line;

            // While there is still a next line
            while (null != (line = br.readLine())) {

                // Split the line by the "," and add each value to an array
                String[] values = line.split(",", 8);

                // Fill in 0 for empty values
                for (int i = 0; i < 8; i++) {
                    if (values[i].equals("")) {
                        values[i] = "0";
                    }

                    // Add this array into the List
                    data.add(Arrays.asList(values));
                }
            }

            // Throw exception if file not found
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print out formatted content of 2D list
        int lineNo = 1;
        for (List<String> line : data) {
            System.out.println("\nLine " + lineNo);
            int columnNo = 0;
            for (String value : line) {
                System.out.println(data.get(0).get(columnNo) + ": " + value);
                columnNo++;
            }
            lineNo++;
        }
        System.out.println("\nCSV file processed successfully");


        return data;
    }

}

