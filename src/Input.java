import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Input {

    // Get all Input
    static Object[] getAllInput(List<List<Object>> data) {
        Object[] input = new Object[9];

        input[0] = inputCountry(data);

        String dateType = inputDateType();
        input[1] = dateType;

        LocalDate[] pair = inputDate(dateType);
        input[2] = pair;

        input[3] = dateBetween(pair);

        String groupType = inputGroupType();
        input[4] = groupType;

        input[5] = inputGroup(groupType);
        input[6] = inputMetricType();
        input[7] = inputValueType();
        input[8] = inputTableType();

        System.out.println("\n");
        System.out.println("Location: " + input[0]);
        System.out.println("Date type: " + input[1]);
        System.out.println("Date pair: " + Arrays.toString((LocalDate[]) input[2]));
        System.out.println("Days between: " + input[3]);
        System.out.println("Group type: " + input[4]);
        System.out.println("Group value: " + input[5]);
        System.out.println("Metric type: " + input[6]);
        System.out.println("Value type: " + input[7]);
        System.out.println("Table type: " + input[8]);

        return input;
    }


    // Get country
    static String inputCountry(List<List<Object>> data) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick country or continent. Write and capitalize first letter (ex: 'Vietnam' or 'Asia'): ");
        String input = scanner.nextLine();

        // Call method to find if country or continent exist in the data

        while (!ProcessCSV.validate(data, input, 2)) {
            System.out.println("Country or continent not found. Try again.");
            System.out.print("\nPick country or continent. Write and capitalize first letter (ex: 'Vietnam' or 'Asia'): ");
            input = scanner.nextLine();

        }
        return input;
    }


    // Get date type
    static String inputDateType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick how to input date range. Write 'pair', 'days before', or 'days after': ");
        String inputDateType = scanner.nextLine();

        while (true) {
            switch (inputDateType) {
                case "pair", "days before", "days after" -> {
                    return inputDateType;
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                    System.out.print("\nPick how to input date range. Write 'pair', 'days before', or 'days after': ");
                    inputDateType = scanner.nextLine();
                }
            }
        }
    }

    // Get date
    static LocalDate[] inputDate(String type) {
        Scanner scanner = new Scanner(System.in);
        LocalDate[] result = new LocalDate[2];
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        while (true) {
            System.out.print("Write 1st date as MM/DD/YYYY (ex: 05/20/2021): ");
            try {
                LocalDate date1Formatted = LocalDate.parse(scanner.nextLine(), dateFormatter);
                result[0] = date1Formatted;
                break;
            } catch (Exception a) {
                System.out.println("Invalid input. Try again.");
            }
        }

        switch (type) {
            case "pair" -> {
                while (true) {
                    System.out.print("Write 2nd date as MM/DD/YYYY (ex: 05/20/2021): ");
                    try {
                        LocalDate date1Formatted = LocalDate.parse(scanner.nextLine(), dateFormatter);
                        result[1] = date1Formatted;
                        break;
                    } catch (Exception a) {
                        System.out.println("Invalid input. Try again.");
                    }
                }
            }
            case "days before" -> {
                System.out.print("\nNumbers of days before 1st date: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Try again.");
                    System.out.print("\nNumbers of days before 1st date: ");
                    scanner.nextLine();
                }
                int days = scanner.nextInt();
                LocalDate date2Formatted = result[0].minusDays(days);
                result[1] = date2Formatted;
            }
            case "days after" -> {
                System.out.print("\nNumbers of days after 1st date: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Try again.");
                    System.out.print("\nNumbers of days after 1st date: ");
                    scanner.nextLine();
                }
                int days = scanner.nextInt();
                LocalDate date2Formatted = result[0].plusDays(days);
                result[1] = date2Formatted;
            }
        }
        return result;
    }

    // Get date amount between
    static int dateBetween(LocalDate[] pair) {
        int dateBetween = (int) ChronoUnit.DAYS.between(pair[0], pair[1]);
        dateBetween = Math.abs(dateBetween);
        return dateBetween;
    }

    // Get grouping type
    static String inputGroupType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick a grouping type. Write 'none', 'by group', or 'by date': ");
        String inputGroupType = scanner.nextLine();

        // Validate input
        while (true) {
            switch (inputGroupType) {
                case "none", "by group", "by date" -> {
                    return inputGroupType;
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                    System.out.print("\nPick a grouping type. Write 'none', 'by group', or 'by date': ");
                    inputGroupType = scanner.nextLine();
                }
            }
        }
    }

    // Get grouping value
    static int inputGroup(String type) {
        Scanner scanner = new Scanner(System.in);
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
        while (true) {
            switch (input) {
                case "positive":
                case "death":
                case "vaccinated":
                    return input;
                default:
                    System.out.println("Invalid input. Please try again.");
                    System.out.print("\nPick metric type. Write 'positive', 'death', or 'vaccinated': ");
                    input = scanner.nextLine();
            }
        }
    }

    // Get value type
    static String inputValueType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick value type. Write 'new total' or 'up to': ");
        String input = scanner.nextLine();

        // Validate input
        while (true) {
            switch (input) {
                case "new total":
                case "up to":
                    return input;
                default:
                    System.out.println("Invalid input. Please try again.");
                    System.out.print("\nPick value type. Write 'new total' or 'up to': ");
                    input = scanner.nextLine();
            }
        }
    }

    // Get table type
    static String inputTableType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick data display method. Write 'tabular' or 'chart': ");
        String input = scanner.nextLine();

        // Validate input
        while (true) {
            switch (input) {
                case "tabular":
                case "chart":
                    return input;
                default:
                    System.out.println("Invalid input. Please try again.");
                    System.out.print("\nPick data display method. Write 'tabular' or 'chart': ");
                    input = scanner.nextLine();
            }
        }
    }
}

