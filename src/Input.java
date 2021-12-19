import java.util.Scanner;

public class Input {
    // Input -  Get country
    static String input_country(){
        Scanner input = new Scanner(System.in);
        System.out.print("\nSearch by country or by continent? Write 'country' or 'continent': ");
        String data_area_type = input.nextLine();
        String data_area = null;
        switch (data_area_type) {
            case "country" -> {
                System.out.print("\nSearching by country. Write country name, capitalize first letter (ex: 'Vietnam'): ");
                data_area = input.nextLine();
            }
            case "continent" -> {
                System.out.print("\nSearching by continent. Write continent name, capitalize first letter (ex: 'Asia'): ");
                data_area = input.nextLine();
            }
            default -> {
                System.out.println("Input invalid. Please try again.");
                input_country();
            }
        }
        return data_area;
    }

    // Input -  Get date range
    static String input_date(){
        Scanner input = new Scanner(System.in);
        System.out.print("\nSearch by 'date pair', 'days until', 'days from': ");
        String data_date_type = input.nextLine();
        String data_date = null;
        String date_pair_start;
        String date_pair_end;


        switch (data_date_type) {
            case "date pair" -> {
                System.out.print("\nSearching by date pair.");
                System.out.print("\nWrite START date (MM/DD/YYYY, ex: 5/20/2000:  ");
                date_pair_start = input.nextLine();
                System.out.print("\nSearching by date pair. Write END date (MM/DD/YYYY, ex: 5/20/2000: ");
                date_pair_end = input.nextLine();
            }
            default -> {
                System.out.println("Input invalid. Please try again.");
                data_date = "none";
                input_country();
            }
        }
        return data_date;
}}
