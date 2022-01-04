import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DrawChart {
    public static ArrayList<String> range_input = new ArrayList<>();
    public static ArrayList<Integer> value_input = new ArrayList<>(); //Array list of value input for each row

    public static void main(String[] args) {
        String y_start_of_value = "";
        String y_end_of_value = "";
        //value_input and range_input
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
        //String x_axis = " __________";

        String[] textual_chart = {y_axis_23,y_axis_22,y_axis_21,y_axis_20,y_axis_19,y_axis_18,y_axis_17,y_axis_16,y_axis_15,y_axis_14,y_axis_13,y_axis_12,y_axis_11,y_axis_10,y_axis_9,y_axis_8,y_axis_7,y_axis_6,y_axis_5,y_axis_4,y_axis_3,y_axis_2,y_axis_1,x_axis};
        List<String> textual_chart_list = Arrays.asList(textual_chart); //convert textual_chart array to list

        //3 .add() below is for testing
        range_input.add("23-09-2021");
        range_input.add("24-09-2021");
        range_input.add("25-09-2021");
        value_input.add(1);
        value_input.add(2);
        value_input.add(3);

        double group_position_double;
        int how_many_groups = range_input.size(); //find how many group we have
        if (how_many_groups >= 79) {
            group_position_double = Math.floor(how_many_groups / 79);
        } else {
            group_position_double = Math.floor(79 / how_many_groups);
        }
        int group_position = (int) group_position_double; //convert double to integer. This is the x coordinate
        //System.out.println("length of y_axis_3 " + y_axis_23.length());

        double num_each_pipe_double = 0; //initialize the value for num each pipe double
        double y_coordinate;
        int num_each_pipe;
        int num_in_between;
        Integer max_value = Collections.max(value_input); //biggest number in the value_input
        Integer min_value = Collections.min(value_input); //smallest number in the value_input
        if (min_value >= 0){ //if min_value != 0 or negative number
            num_in_between = (max_value - min_value) + 1; //plus 1 to count the max value. Ex: 10 - 1 = 9. But we have 10 numbers from 1 to 10 counting 1 and 10
        }
        else{ //min value = -1(for example) so we
            num_in_between = max_value - min_value;
        }

        int i;
        for (i = 0; i < range_input.size();i++) {
            //the goal is to find y coordinate and x coordinate and then use Strinbuilder to replace whitespace in
            //one of the string in textual_chart array

            if (num_in_between > 23) {
                num_each_pipe_double = Math.floor(num_in_between / 23);
                num_each_pipe = (int) num_each_pipe_double;
                if (value_input.get(i) == 0){ //if 0, auto default to the y_coor_01
                    y_coordinate = 1;
                }
                else {
                    y_coordinate = (value_input.get(i) - min_value) / num_each_pipe;
                    y_coordinate += 1;
                }
            }
            else { //every pipe represents 1-23
                y_coordinate = value_input.get(i);
            }

            Math.round(y_coordinate);
            int y_coordinate_backwards = (int) (23 - y_coordinate);

            int group_index = range_input.indexOf(range_input.get(i)) + 1; //use to find the x coordinate. Change 0 to i. PLus 1 bc index start of 0

            //to draw the star at the exact x coordinate that represents their group, dont need to plus 1.
            StringBuilder replace_with_asterisk;
            replace_with_asterisk = new StringBuilder(textual_chart_list.get(y_coordinate_backwards));//get the y_axis_xx line to input * in.
            replace_with_asterisk.setCharAt(group_position * group_index, '*');
            textual_chart[y_coordinate_backwards] = replace_with_asterisk.toString(); //replace 23 with y_coordinate_backwards
            //System.out.println(x_axis);

            //First look at range_input[i]. Input data for range_input[i]
            //get group_position, input group_position
            //save the y_axis_xx
            //To get the next group x coordinate position, multiple with index of that group + 1
            //move on to the next one.


        }

        for (String s : textual_chart) { //Print the chart out
            System.out.println(s);
        }
    }
}
      
           // funtion to sort value from min to max
            /*public static int[] sortArray(int[] OriginalArray) {
            int[] sortedArray = new int[OriginalArray.length];
            int temp;
            for (int i = 0; i <= OriginalArray.length; i++)
            {
                for (int k = i+1; k < OriginalArray.length; k++) // create for loop to continue to rearrange the value order
                {
                    if (OriginalArray[i] > OriginalArray[k])
                    {
                        temp = OriginalArray[i];
                        OriginalArray[i] = OriginalArray[k];
                        OriginalArray[k] = temp;
                        sortedArray = OriginalArray;
                    }
                }
            }
            return sortedArray;
        }*/
