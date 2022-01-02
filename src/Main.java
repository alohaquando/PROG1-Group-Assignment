import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Process the CSV file to use throughout the function
        List<List<Object>> data = ProcessCSV.process();

        System.out.println("Welcome to our group project");
        System.out.println("Type 'yes' if you want to continue using our program and 'no' if you want to quit");
        String user_input = scanner.nextLine();
        while (!user_input.equals("no")) {
            // Get all needed input
            Input.getAllInput(data);
            new Display(data);
            System.out.println("Type 'yes' if you want to continue using our program and 'no' if you want to quit");
            user_input = scanner.nextLine();
        }
        System.out.println("Thank you for using our program!");
    }
}
