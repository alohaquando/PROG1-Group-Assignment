package com.company;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.company.Input.*;

public class Display {

    Display(List<List<Object>> data){
        ProcessCSV processed_data = new ProcessCSV(); // call the processCSV class to take data
        if (tableType.equals("tabular")) {
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("| Range\t\t\t\t\t\t\t |\tValue                                 |");
            System.out.println("---------------------------------------------------------------------------");
            ArrayList<String> range_input_list = new ArrayList<>();
            List<LocalDate> date_pair_each_row = new ArrayList<>(); //include the start and end date of each group. If user select none then this will hold date of each group
            //Specify Groupings
            LocalDate tempDate = date_pair[0]; //initial value for temp_date is the start date to be use later on

            if (date_pair[0].isEqual(date_pair[1])) { //user input 1 date
                date_pair_each_row.add(date_pair[0]);
            }
            else { //user input 2 different dates
                if (date_pair[0].isAfter(date_pair[1])) {
                    //if date 1 occur after date 2. Switch place so that date 1 occur before date 2
                    LocalDate temp_date_swap = date_pair[0];
                    date_pair[0] = date_pair[1];
                    date_pair[1] = temp_date_swap;
                }
                if (groupType.equals("none")) { //loop and add until date reach end date
                    while (!tempDate.isAfter(date_pair[1])){
                        date_pair_each_row.add(tempDate);
                        tempDate = tempDate.plusDays(1);
                    }
                }
                else if (groupType.equals("by group")){
                    //we have exactly as many row as group value specified and find how many days each row
                    //To find how many days per row if user select by group
                    int[] days_per_row_if_select_by_group = new int[groupValue]; //each int elements represents how many days each row
                    for (int i = 0; i < days_per_row_if_select_by_group.length; i++) {
                        days_between -= days_per_row_if_select_by_group[i] = (days_between + groupValue - i - 1) / (groupValue - i);
                    }
                    date_pair_each_row.add(date_pair[0]); // Add the first date
                    //loop
                    int i;
                    for (i = 0; i < days_per_row_if_select_by_group.length; i ++){
                        tempDate = tempDate.plusDays(days_per_row_if_select_by_group[i] - 1);
                        date_pair_each_row.add(tempDate);
                        date_pair_each_row.add(tempDate.plusDays(1));
                        tempDate = tempDate.plusDays(1);
                    }
                    date_pair_each_row.remove(date_pair_each_row.size()-1); //remove the last element because it is over the end date
                }
                else { //if this doesnt work, change it into else if "by days". This is done
                    //have exactly as many row as groupValue
                    date_pair_each_row.add(tempDate); // add the start date of a group
                    while (tempDate.isBefore(date_pair[1])) {
                        tempDate = tempDate.plusDays(groupValue - 1);
                        date_pair_each_row.add(tempDate);
                        date_pair_each_row.add(tempDate.plusDays(1)); //add the start date of the next group
                        tempDate = tempDate.plusDays(1);
                    }
                    date_pair_each_row.remove(date_pair_each_row.size()-1); //remove the last element because it is over the end date
                }
            }
            String temp_date_string_output;
            if (date_pair[0].isEqual(date_pair[1]) || groupType.equals("none")) { //If only input 1 date or input GroupType = none
                for (LocalDate localDate : date_pair_each_row) {
                    temp_date_string_output = localDate.toString();
                    range_input_list.add(temp_date_string_output);
                }
            }
            else { //inputGroupType = the other 2
                for (int i = 0; i < date_pair_each_row.size(); i++) {
                    //Since if sorted by group or by days, it will always in pair. This for loop format localdate into string.
                    temp_date_string_output = date_pair_each_row.get(i).toString() + " - " + date_pair_each_row.get(i + 1).toString();
                    i = i + 1;
                    range_input_list.add(temp_date_string_output);
                }
            }

            //Convert list of range input to array and output it as range column
            String[] range_input = new String[range_input_list.size()];
            range_input = range_input_list.toArray(range_input);


            int date_pair_index = 0;

            //Input data for value columns. Loops through each rows date from Range collumn and print out the entire table
            for (String s : range_input) { //for each row

                List<LocalDate> every_date_each_row = new ArrayList<>();
                int sum = 0; //sum of data for each row/ reset after every row
                //int imaginary_sum = 0; //sum of imaginary date to use if groupType = None or select only 1 date
                LocalDate start_date_of_row = date_pair_each_row.get(date_pair_index);
                LocalDate end_date_of_row;
                List<List<Object>> chosen_metric_data;
                List<List<Object>> total_chosen_metric_data = new ArrayList<>();

                List<Integer> int_metric_data; //convert object list into integer list for calculation

                if (date_pair[0].isEqual(date_pair[1]) || groupType.equals("none")) {
                    end_date_of_row = start_date_of_row; //end date of row is the same as start day or row. Means each row only 1 date
                    date_pair_index = date_pair_index + 1;
                } else {                     // if user input multiple date and each row have multiple days
                    end_date_of_row = date_pair_each_row.get(date_pair_index + 1);
                    date_pair_index = date_pair_index + 2;
                }
                //2 way of calculating results
                if (valueType.equals("new total")) {
                    //Choose between three metric
                    if (metricType.equals("vaccinated")) {
                        if (start_date_of_row.compareTo(end_date_of_row) == 0) { // equals if they are both the same
                            chosen_metric_data = processed_data.find_data(data, location, start_date_of_row, 2, 3); //data of that date
                            total_chosen_metric_data.addAll(chosen_metric_data); //add data from that date to another list that contains all data of date in row

                            int_metric_data = total_chosen_metric_data.stream() //In case there's more than 1 data that have the same date. Convert object list into integer list
                                    .flatMap(Collection::stream)
                                    .map(ob -> (Integer) ob)
                                    .collect(Collectors.toList());

                            for (int i : int_metric_data) {
                                sum += i;
                            }
                            total_chosen_metric_data.clear(); //clear from start_date_of_row data still in total_chosen_metric_data list
                            LocalDate imaginary_date_before = start_date_of_row.minusDays(1); //create an imaginary date to minus.
                            chosen_metric_data = processed_data.find_data(data, location, imaginary_date_before, 2, 3);
                            total_chosen_metric_data.addAll(chosen_metric_data); //add data from that date to another list that contains all data of date in row

                            int_metric_data = total_chosen_metric_data.stream() //In case there's more than 1 data that have the same date. Convert object list into integer list
                                    .flatMap(Collection::stream)
                                    .map(ob -> (Integer) ob)
                                    .collect(Collectors.toList());

                            for (int i : int_metric_data) {
                                sum = sum - i; //Sum is the sum of date above. We need to new date only from the date of that row. So we have to minus it with imaginary accumlated data from yesterday
                            }
                        } else { // if more than 1 date for each row
                            chosen_metric_data = processed_data.find_data(data, location, end_date_of_row, 2, 3);
                            total_chosen_metric_data.addAll(chosen_metric_data); //add data from that date to another list that contains all data of date in row

                            int_metric_data = chosen_metric_data.stream() //In case there's more than 1 data that have the same date, because it means there will be more than 1 element in the list. Convert object list into integer list
                                    .flatMap(Collection::stream)
                                    .map(ob -> (Integer) ob)
                                    .collect(Collectors.toList());

                            for (int i : int_metric_data) {
                                sum += i;
                            }
                            chosen_metric_data = processed_data.find_data(data, location, start_date_of_row, 2, 3);
                            total_chosen_metric_data.addAll(chosen_metric_data); //add data from that date to another list that contains all data of date in row

                            int_metric_data = total_chosen_metric_data.stream() //In case there's more than 1 data that have the same date, because it means there will be more than 1 element in the list. Convert object list into integer list
                                    .flatMap(Collection::stream)
                                    .map(ob -> (Integer) ob)
                                    .collect(Collectors.toList());

                            for (int i : int_metric_data) {
                                sum = sum - i;
                            }
                        }
                    } else { //else the other 2 metric. Since they have the same calculation method
                        while (!start_date_of_row.isAfter(end_date_of_row)) {
                            every_date_each_row.add(start_date_of_row);
                            start_date_of_row = start_date_of_row.plusDays(1);
                        }
                        for (LocalDate localDate : every_date_each_row) {
                            chosen_metric_data = processed_data.find_data(data, location, localDate, 2, 3); //take data from that date
                            total_chosen_metric_data.addAll(chosen_metric_data); //add data from that date to another list that contains all data of date in row
                        }
                        int_metric_data = total_chosen_metric_data.stream()
                                .flatMap(Collection::stream)
                                .map(ob -> (Integer) ob)
                                .collect(Collectors.toList());

                        for (int i : int_metric_data) {
                            sum += i;
                        }
                    }
                } else { //if it is up to
                    if (metricType.equals("vaccinated")) {
                        System.out.println("hello");
                    } else {
                        start_date_of_row = date_pair[0];
                        while (!start_date_of_row.isAfter(end_date_of_row)) {
                            every_date_each_row.add(start_date_of_row);
                            start_date_of_row = start_date_of_row.plusDays(1);
                        }
                        for (LocalDate localDate : every_date_each_row) {
                            chosen_metric_data = processed_data.find_data(data, location, localDate, 2, 3); //take data from that date
                            total_chosen_metric_data.addAll(chosen_metric_data); //add data from that date to another list that contains all data of date in row
                        }
                        int_metric_data = total_chosen_metric_data.stream()
                                .flatMap(Collection::stream)
                                .map(ob -> (Integer) ob)
                                .collect(Collectors.toList());

                        for (int i : int_metric_data) {
                            sum += i;
                        }
                    }
                }
                //Print out table with range column on the left and value column(sum) on the right
                if (date_pair[0].isEqual(date_pair[1]) || groupType.equals("none")) {
                    System.out.println("  " + s + "\t\t\t\t\t | \t\t" + sum);
                } else {
                    System.out.println("  " + s + "\t\t | \t\t" + sum);
                }
            }
            System.out.println("---------------------------------------------------------------------------");

        }
    }
}

