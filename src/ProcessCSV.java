import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessCSV {
    public static List<List<Object>> process() {
        // Create new 2D list to hold CSV data
        List<List<Object>> data = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        // Try to read file
        try (BufferedReader br = new BufferedReader(new FileReader("src/covid-data.csv"))) {
            String line;

            // While there is still a next line
            while (null != (line = br.readLine())) {

                // Split the line by the "," and add each value to an array
                String[] preValues = line.split(",", 8);

                // Copy values from String array to Object array to store more type of value
                Object[] values = new Object[8];
                for (int i = 0; i < preValues.length; i++) {
                    values[i] = preValues[i];
                }

                // Fill in 0 for empty values
                for (int i = 0; i < 8; i++) {
                    if (values[i].equals("")) {
                        values[i] = 0;
                    }
                }

                // Convert date into LocalDate format to make easier to calculate and use later
                if (!values[3].toString().equals("date")) {
                    LocalDate formattedDate = LocalDate.parse(values[3].toString(), dateFormatter);
                    values[3] = formattedDate;
                }

                // Add this array into the List
                data.add(Arrays.asList(values));
            }

            // Throw exception if file not found
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print out formatted content of 2D list
//        int lineNo = 1;
//        for (List<Object> line : data) {
//            System.out.println("\nLine " + lineNo);
//            int columnNo = 0;
//            for (Object value : line) {
//                System.out.println(data.get(0).get(columnNo) + ": " + value);
//                columnNo++;
//            }
//            lineNo++;
//        }
        System.out.println("\nCSV file processed successfully");


        return data;
    }

    public static boolean validate(List<List<String>> data, String to_validate, int column) {
        for (List<String> line : data) {
            if (to_validate.equals(line.get(column))) {
                System.out.println("Country or continent found.");
                return true;
            }
        }
        return false;
    }

    public static int calculate(List data, Timestamp from, Timestamp to) {
        return 0;
    }


}

