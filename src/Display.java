import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Display extends Input {
    Display(List<List<Object>> data){
        //Start of Calculation Part //This will give two array list of which every element represents date each row and value each row
        ProcessCSV processed_data = new ProcessCSV(); // call the processCSV class to take data
        ArrayList<String> range_input = new ArrayList<>();
        List<LocalDate> date_pair_each_row = new ArrayList<>(); //include the start and end date of each group. If user select none then this will hold date of each group
        ArrayList<Integer> value_input = new ArrayList<>(); //Array list of value input for each row


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
            else {
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
                range_input.add(temp_date_string_output);
            }
        }
        else { //inputGroupType = the other 2
            for (int i = 0; i < date_pair_each_row.size(); i++) {
                //Since if sorted by group or by days, it will always in pair. This for loop format localdate into string.
                temp_date_string_output = date_pair_each_row.get(i).toString() + " - " + date_pair_each_row.get(i + 1).toString();
                i = i + 1;
                range_input.add(temp_date_string_output);
            }
        }
        
        int date_pair_index = 0;
        //Input data for value columns. Loops through each rows date from Range collumn and print out the entire table
        for (int counter = 0; counter < range_input.size(); counter ++) { //for each row

            List<LocalDate> every_date_each_row = new ArrayList<>();
            int sum = 0; //sum of data for each row/ reset after every row
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
                        total_chosen_metric_data.clear();//clear from end_date_of_row data still in total_chosen_metric_data list
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
            } else { //if it is Up to
                if (metricType.equals("vaccinated")) {
                    chosen_metric_data = processed_data.find_data(data, location, end_date_of_row, 2, 3);
                    total_chosen_metric_data.addAll(chosen_metric_data);
                } else { //the other two, since they have the same way of calculation
                    start_date_of_row = date_pair[0];
                    while (!start_date_of_row.isAfter(end_date_of_row)) {
                        every_date_each_row.add(start_date_of_row);
                        start_date_of_row = start_date_of_row.plusDays(1);
                    }
                    for (LocalDate localDate : every_date_each_row) {
                        chosen_metric_data = processed_data.find_data(data, location, localDate, 2, 3); //take data from that date
                        total_chosen_metric_data.addAll(chosen_metric_data); //add data from that date to another list that contains all data of date in row
                    }
                }
                int_metric_data = total_chosen_metric_data.stream()
                        .flatMap(Collection::stream)
                        .map(ob -> (Integer) ob)
                        .collect(Collectors.toList());

                for (int i : int_metric_data) {
                    sum += i;
                }
            }
            value_input.add(sum);
        }
        //------------------------------------//End of calculation part.
        if (tableType.equals("tabular")) {
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("| Range\t\t\t\t\t\t\t |\tValue                                 |");
            System.out.println("---------------------------------------------------------------------------");
            int i;

            //Print out table with range column on the left and value column(sum) on the right
            if (date_pair[0].isEqual(date_pair[1]) || groupType.equals("none")) {
                for (i = 0; i < range_input.size(); i ++){
                    System.out.println("  " + range_input.get(i) + "\t\t\t\t\t | \t\t" + value_input.get(i));

                }
            } else {
                for (i = 0; i < range_input.size(); i ++){
                    System.out.println("  " + range_input.get(i) + "\t\t | \t\t" + value_input.get(i));

                }
            }

            System.out.println("---------------------------------------------------------------------------");
            if (valueType.equals("up to") && metricType.equals("vaccinated") ){
                System.out.println("Since you have select vaccinated and calculation method 'up to', the program will print out only the last date of each group because it is the accumulated values up to that date");
                System.out.println("If in case you input a country/continent and a date that have multiple vaccinated value(for example, 'Asia' and '03-09-2021' means every country data in Asia with date '03-09-2021'), the program will sum up all of that accumulated values up to that date");
            }
        }
        else {//tableType.equals("chart")
            String y_axis_23 = "|                                                                               ";
            String y_axis_22 = "|                                                                               ";
            String y_axis_21 = "|                                                                               ";
            String y_axis_20 = "|                                                                               ";
            String y_axis_19 = "|                                                                               ";
            String y_axis_18 = "|                                                                               ";
            String y_axis_17 = "|                                                                               ";
            String y_axis_16 = "|                                                                               ";
            String y_axis_15 = "|                                                                               ";
            String y_axis_14 = "|                                                                               ";
            String y_axis_13 = "|                                                                               ";
            String y_axis_12 = "|                                                                               ";
            String y_axis_11 = "|                                                                               ";
            String y_axis_10 = "|                                                                               ";
            String y_axis_9 = "|                                                                               ";
            String y_axis_8 = "|                                                                               ";
            String y_axis_7 = "|                                                                               ";
            String y_axis_6 = "|                                                                               ";
            String y_axis_5 = "|                                                                               ";
            String y_axis_4 = "|                                                                               ";
            String y_axis_3 = "|                                                                               ";
            String y_axis_2 = "|                                                                               ";
            String y_axis_1 = "|                                                                               ";
            String x_axis = " _______________________________________________________________________________";

            String[] textual_chart = {y_axis_23,y_axis_22,y_axis_21,y_axis_20,y_axis_19,y_axis_18,y_axis_17,y_axis_16,y_axis_15,y_axis_14,y_axis_13,y_axis_12,y_axis_11,y_axis_10,y_axis_9,y_axis_8,y_axis_7,y_axis_6,y_axis_5,y_axis_4,y_axis_3,y_axis_2,y_axis_1,x_axis};
            List<String> textual_chart_list = Arrays.asList(textual_chart); //convert textual_chart array to list
            List<Integer> number_range_each_pipe = new ArrayList<>();

            double group_position_double;
            int how_many_groups = range_input.size(); //find how many group we have
            if (how_many_groups >= 79) {
                group_position_double = Math.floor(how_many_groups / 79);
            } else {
                group_position_double = Math.floor(79 / how_many_groups);
            }
            int group_position = (int) group_position_double; //convert double to integer. This is the x coordinate


            double num_each_pipe_double; //initialize the value for num each pipe double
            double y_coordinate;
            int num_each_pipe = 0;
            int num_in_between;
            Integer max_value = Collections.max(value_input); //biggest number in the value_input
            Integer min_value = Collections.min(value_input); //smallest number in the value_input
            if (min_value >= 0){ //if min_value is a positive number or 0
                num_in_between = (max_value - min_value) + 1; //plus 1 to count the max value. Ex: 10 - 1 = 9. But we have 10 numbers from 1 to 10 counting 1 and 10
            }
            else{ //min value = -1(for example) so we
                num_in_between = max_value - min_value;
            }
            int i;
            int start_value_pipe = min_value;
            int c = 0;
            for (i = 0; i < range_input.size();i++) {
                //the goal is to find y coordinate and x coordinate and then use Strinbuilder to replace whitespace in
                //one of the string in textual_chart array
                if (num_in_between > 23) {
                    num_each_pipe_double = Math.ceil((double)num_in_between/(double)23);
                    num_each_pipe = (int) num_each_pipe_double;

                    //start_value_pipe = start_value_pipe - 1;
                    while(c != 23) {
                        number_range_each_pipe.add(start_value_pipe);
                        start_value_pipe = start_value_pipe + num_each_pipe;
                        number_range_each_pipe.add(start_value_pipe);
                        c = c + 1;
                    }

                    if (min_value == 0 && value_input.get(i) != 0){
                        if (value_input.get(i) < num_each_pipe){ //if the range of data is too big and value_input.get(i) compare to num_each_pipe is too small, default it to y_coor_1
                            y_coordinate = 1;
                        }
                        else{
                            y_coordinate = (value_input.get(i) - min_value) / num_each_pipe;
                        }
                    }
                    else { //dont have zero as min_value
                        if (value_input.get(i) == 0){ //if 0, auto default to the y_coor_01
                            y_coordinate = 1;
                        }
                        else {
                            y_coordinate = (value_input.get(i) - min_value) / num_each_pipe;
                            y_coordinate += 1;
                            if (y_coordinate == 24) {
                                y_coordinate -= 1;
                            }
                        }
                    }
                    Math.round(y_coordinate);
                    if (value_input.get(i) > (((int) y_coordinate * num_each_pipe) + min_value) ){
                        y_coordinate += 1;
                    }
                }
                else { //every pipe represents 1-23 or 0 - 22 //This is done
                    if (min_value == 0) {
                        y_coordinate = value_input.get(i) + 1;
                    }
                    else {
                        y_coordinate = value_input.get(i);
                    }
                    while (c != 23){
                        number_range_each_pipe.add(start_value_pipe);
                        start_value_pipe = start_value_pipe + 1;
                        c = c + 1;
                    }
                }
                Math.round(y_coordinate);
                int y_coordinate_backwards = (int) (23 - y_coordinate);

                int group_index = range_input.indexOf(range_input.get(i)) + 1; //use to find the x coordinate. Change 0 to i. PLus 1 bc index start of 0

                //to draw the star at the exact x coordinate that represents their group, dont need to plus 1.
                StringBuilder replace_with_asterisk;
                replace_with_asterisk = new StringBuilder(textual_chart_list.get(y_coordinate_backwards));//get the y_axis_xx line to input * in.
                replace_with_asterisk.setCharAt(group_position * group_index, '*');
                textual_chart[y_coordinate_backwards] = replace_with_asterisk.toString();
            }
            String add_value_range;
            String add_value_range_2;
            String temp_pipe;
            int if_smaller_than_23 = 22;
            int if_bigger_than_23 = 45;
            for (String s : textual_chart) { //Print the chart out
                if (!s.equals(" _______________________________________________________________________________")){//To input number on the side
                    if (number_range_each_pipe.size() > 23){
                        temp_pipe = s;
                        add_value_range = Integer.toString(number_range_each_pipe.get(if_bigger_than_23));
                        if_bigger_than_23 -= 1;
                        add_value_range_2 = Integer.toString(number_range_each_pipe.get(if_bigger_than_23));
                        if_bigger_than_23 -= 1;
                        s = temp_pipe + "(" + add_value_range_2 + "-" +add_value_range + ")";
                    }
                    else {
                        temp_pipe = s;
                        add_value_range = Integer.toString(number_range_each_pipe.get(if_smaller_than_23));
                        if_smaller_than_23 -= 1;
                        s = temp_pipe + "(" + add_value_range + ")";
                    }
                }
                System.out.println(s);
            }
            System.out.println("Each group date: " + range_input);
            System.out.println("Value for each group(same index with each group date): " + value_input);
            System.out.println("There are  " + num_in_between + " between the smallest value and largest value(including smallest and largest");
            System.out.println("There are " + num_each_pipe + " number each pipe");
            System.out.println("The number on the side represents the range of that pipe.");
            System.out.println("If maximum data value larger than 23, value data in one pipe is from (i - i_2) and cannot be bigger than i_2 ");
            System.out.println("If largest data value is smaller than 23 or 0-22, value data that pipe is equal to number on the right");
        }
    }
}
