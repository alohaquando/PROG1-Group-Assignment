import java.util.List;
import java.util.Scanner;

public class Input {

    // Get country
    static String inputCountry(List<List<String>> data) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick country or continent. Write and capitalize first letter (ex: 'Vietnam' or 'Asia'): ");
        String input = scanner.nextLine();

        // Call method to find if country or continent exist in the data
        if (!ProcessCSV.validate(data, input, 2)) {
            System.out.println("Country or continent not found. Try again.");
            // Rerun the method if not found
            inputCountry(data);
        }

        // Return if found
        return input;
    }

    // Get date range
//    static String input_date(){
//        Scanner input = new Scanner(System.in);
//        System.out.print("\nSearch by 'date pair', 'days until', 'days from': ");
//        String data_date_type = input.nextLine();
//        String data_date = null;
//        String date_pair_start;
//        String date_pair_end;
//
//
//        switch (data_date_type) {
//            case "date pair" -> {
//                System.out.print("\nSearching by date pair.");
//                System.out.print("\nWrite START date (MM/DD/YYYY, ex: 5/20/2000:  ");
//                date_pair_start = input.nextLine();
//                System.out.print("\nSearching by date pair. Write END date (MM/DD/YYYY, ex: 5/20/2000: ");
//                date_pair_end = input.nextLine();
//            }
//            default -> {
//                System.out.println("Input invalid. Please try again.");
//                data_date = "none";
//            }
//        }
//        return data_date;

    // Get grouping type
    static String inputGroupType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick a grouping type. Write 'none', 'by group', or 'by date': ");
        String inputGroupType = scanner.nextLine();

        // Validate input
        switch (inputGroupType) {
            case "none":
            case "by group":
            case "by date":
                return inputGroupType;
            default:
                System.out.println("Invalid input. Please try again.");
                inputGroupType();
        }
        return null;
    }

    // Get grouping value
    static int inputGroup() {
        Scanner scanner = new Scanner(System.in);
        String type = inputGroupType();

        // Validate input
        switch (type) {
            case "by group" -> {
                System.out.print("\nNumber of groups: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Try again.");
                    System.out.print("\nNumber of groups: ");
                    scanner.nextLine();
                }
                return scanner.nextInt();
            }
            case "by date" -> {
                System.out.print("\nNumber of days: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Try again.");
                    System.out.print("\nNumber of groups: ");
                    scanner.nextLine();
                }
                return scanner.nextInt();
            }
        }
        return 0;
    }

    // Get metric type
    static String inputMetricType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick metric type. Write 'positive', 'death', or 'vaccinated': ");
        String input = scanner.nextLine();

        // Validate input
        switch (input) {
            case "positive":
            case "death":
            case "vaccinated":
                return input;
            default:
                System.out.println("Invalid input. Please try again.");
                inputMetricType();
        }
        return null;
    }

    // Get value type
    static String inputValueType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick value type. Write ' new total' or 'up to': ");
        String input = scanner.nextLine();

        // Validate input
        switch (input) {
            case "new total":
            case "up to":
                return input;
            default:
                System.out.println("Invalid input. Please try again.");
                inputValueType();
        }
        return null;
    }

    // Get table type
    static String inputTableType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick data display method. Write 'tabular' or 'chart': ");
        String input = scanner.nextLine();

        // Validate input
        switch (input) {
            case "tabular":
            case "chart":
                return input;
            default:
                System.out.println("Invalid input. Please try again.");
                inputTableType();
        }
        return null;
    }

}

