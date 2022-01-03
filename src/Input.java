import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Input {

    public static String location;
    public static String dateType;
    public static LocalDate[] date_pair;
    public static int days_between;
    public  static String groupType;
    public static int groupValue;
    public static String metricType;
    public static String valueType;
    public static String tableType;
    // Get all Input
    static void getAllInput(List<List<Object>> data) {
        location = inputCountry(data);
        dateType = inputDateType();
        date_pair = inputDate(dateType);
        days_between = dateBetween(date_pair);
        groupType = inputGroupType();
        groupValue = inputGroup(groupType, days_between);
        metricType = inputMetricType();
        valueType = inputValueType();
        tableType = inputTableType();

        System.out.println("\n");
        System.out.println("Location: " + location);
        System.out.println("Date type: " + dateType);
        System.out.println("Date pair: " + Arrays.toString(date_pair));
        System.out.println("Days between: " + days_between);
        System.out.println("Group type: " + groupType);
        System.out.println("Group value: " + groupValue);
        System.out.println("Metric type: " + metricType);
        System.out.println("Value type: " + valueType);
        System.out.println("Table type: " + tableType);
    }


    // Get country
    static String inputCountry(List<List<Object>> data) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick country or continent. Write and capitalize first letter (ex: 'Vietnam' or 'Asia'): ");
        String input = scanner.nextLine();

        // Call method to find if country or continent exist in the data
        // Loop until country is found
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
        System.out.print("\nPick how to input date range. Write 'pair', 'days before', 'days after', 'weeks before', 'weeks after': ");
        String inputDateType = scanner.nextLine();

        // Validate date type
        while (true) {
            switch (inputDateType) {
                case "pair", "days before", "days after", "weeks before", "weeks after" -> {
                    return inputDateType;
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                    System.out.print("\nPick how to input date range. Write 'pair', 'days before', 'days after', 'weeks before', 'weeks after': ");
                    inputDateType = scanner.nextLine();
                }
            }
        }
    }

    // Get date
    static LocalDate[] inputDate(String type) {
        Scanner scanner = new Scanner(System.in);
        // Initialize new LocalDate array
        LocalDate[] result = new LocalDate[2];

        // Formatter to format input into LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        // Try to format input and ask for input again if invalid
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

        // Cases for each date type
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

            case "weeks before" -> {
                System.out.print("\nNumbers of weeks before 1st date: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Try again.");
                    System.out.print("\nNumbers of weeks before 1st date: ");
                    scanner.nextLine();
                }
                int weeks = scanner.nextInt();
                LocalDate date2Formatted = result[0].minusWeeks(weeks);
                result[1] = date2Formatted;
            }

            case "weeks after" -> {
                System.out.print("\nNumbers of weeks after 1st date: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Try again.");
                    System.out.print("\nNumbers of weeks after 1st date: ");
                    scanner.nextLine();
                }
                int weeks = scanner.nextInt();
                LocalDate date2Formatted = result[0].plusWeeks(weeks);
                result[1] = date2Formatted;
            }
        }
        return result;
    }

    // Get days between dates
    static int dateBetween(LocalDate[] pair) {
        int dateBetween = (int) ChronoUnit.DAYS.between(pair[0], pair[1]);
        // Make sure the number is positive
        dateBetween = Math.abs(dateBetween);
        dateBetween = dateBetween + 1;
        return dateBetween;
    }

    // Get grouping type
    static String inputGroupType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPick a grouping type. Write 'none', 'by group', or 'by days': ");
        String inputGroupType = scanner.nextLine();

        // Validate input
        while (true) {
            switch (inputGroupType) {
                case "none", "by group", "by days" -> {
                    return inputGroupType;
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                    System.out.print("\nPick a grouping type. Write 'none', 'by group', or 'by days': ");
                    inputGroupType = scanner.nextLine();
                }
            }
        }
    }

    // Get grouping value
    static int inputGroup(String type, int days) {
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
            case "by days" -> {
                System.out.println("\nNumber of days selected: " + days);
                System.out.print("Number of days SELECTED must be divisible by number of days to group. Number of days to group: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Try again.");
                    System.out.println("\nNumber of days selected: " + days);
                    System.out.print("Number of days SELECTED must be divisible by number of days to group. Number of days to group: ");
                    scanner.nextLine();
                }
                int daysGroup = scanner.nextInt();
                while (days % daysGroup != 0) {
                    System.out.println("Invalid input. Try again.");
                    System.out.println("\nNumber of days selected: " + days);
                    System.out.print("Number of days SELECTED must be divisible by number of days to group. Number of days to group: ");
                    scanner.nextLine();
                    daysGroup = scanner.nextInt();
                }
                return daysGroup;
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
