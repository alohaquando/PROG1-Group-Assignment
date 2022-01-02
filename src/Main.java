import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Process the CSV file to use throughout the function
        List<List<Object>> data = ProcessCSV.process();

        // Get all needed input
        Input.getAllInput(data);
        new Display(data);

    }
}
