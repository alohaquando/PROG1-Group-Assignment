import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Process the CSV file to use throughout the function
        List<List<Object>> data = ProcessCSV.process();

        // Run program
        String user_input = "yes";

        while (user_input.equals("yes")) {
            // Get all inputs from user
            Input.getAllInput(data);
            new Display(data);

            // Ask to rerun program
            System.out.print("\nContinue running program? Write 'yes' or 'no': ");
            user_input = scanner.nextLine();

            // Validate input and ask again if invalid
            while (!user_input.equals("yes") && !user_input.equals("no")) {
                System.out.println("Invalid input. Please try again.");
                System.out.print("\nContinue running program? Write 'yes' or 'no': ");
                user_input = scanner.nextLine();
            }
        }

        // End program if "no" selected
        System.out.println("\nThank you for using our program!");
    }
}
