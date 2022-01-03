import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial") // object does not fully implement the Serial interface -> warning to turn off compiler
public class DrawChart {
    public static ArrayList<String> range_input = new ArrayList<>();
    public static ArrayList<Integer> value_input = new ArrayList<>(); //Array list of value input for each row

    public static void main(String[] args) {
        //value_input and range_input
        String y_axis_23 = "|                                                                         ";
        String y_axis_22 = "|                                                                         ";
        String y_axis_21 = "|                                                                         ";
        String y_axis_20 = "|                                                                         ";
        String y_axis_19 = "|                                                                         ";
        String y_axis_18 = "|                                                                         ";
        String y_axis_17 = "|                                                                         ";
        String y_axis_16 = "|                                                                         ";
        String y_axis_15 = "|                                                                         ";
        String y_axis_14 = "|                                                                         ";
        String y_axis_13 = "|                                                                         ";
        String y_axis_12 = "|                                                                         ";
        String y_axis_11 = "|                                                                         ";
        String y_axis_10 = "|                                                                         ";
        String y_axis_9 = "|                                                                         ";
        String y_axis_8 = "|                                                                         ";
        String y_axis_7 = "|                                                                         ";
        String y_axis_6 = "|                                                                         ";
        String y_axis_5 = "|                                                                         ";
        String y_axis_4 = "|                                                                         ";
        String y_axis_3 = "|                                                                         ";
        String y_axis_2 = "|                                                                         ";
        String y_axis_1 = "|                                                                         ";
        String x_axis = " _______________________________________________________________________________";
        //String x_axis = " __________";

        String[] textual_chart = {y_axis_23,y_axis_22,y_axis_21,y_axis_20,y_axis_19,y_axis_18,y_axis_17,y_axis_16,y_axis_15,y_axis_14,y_axis_13,y_axis_12,y_axis_11,y_axis_10,y_axis_9,y_axis_8,y_axis_7,y_axis_6,y_axis_5,y_axis_4,y_axis_3,y_axis_2,y_axis_1,x_axis};
        List<String> textual_chart_list = Arrays.asList(textual_chart);

        //3 .add() below is for testing
        range_input.add("a");
        range_input.add("b");
        range_input.add("c");
        value_input.add(12);
        value_input.add(12);
        value_input.add(20);

        int i;
        double group_position_double;
        int how_many_groups = range_input.size(); //find how many group we have
        if (how_many_groups >= 79) {
            group_position_double = Math.floor(how_many_groups / 79);
        } else {
            group_position_double = Math.floor(79 / how_many_groups);
        }
        int group_position = (int) group_position_double; //convert double to integer. This is the x coordinate

        for (i = 0; i < range_input.size();i++) {
            //the goal is to find y coordinate and x coordinate and then use Strinbuilder to replace whitespace in
            //one of the string in textual_chart array
            int group_index = range_input.indexOf(range_input.get(0)) + 1; //use to find the x coordinate. Change 0 to i. PLus 1 bc index start of 0

            //System.out.println(how_many_groups);
            //to draw the star at the exact x coordinate that represents their group, dont need to plus 1.
            StringBuilder replace_with_asterisk;
            replace_with_asterisk = new StringBuilder(textual_chart_list.get(23));//get the y_axis_xx line to input * in.
            replace_with_asterisk.setCharAt(group_position * group_index, '*');

            textual_chart[23] = replace_with_asterisk.toString(); //replace 23 with y_coordinate_backwards
            //x_axis = replace_with_asterisk.toString();
            //System.out.println(x_axis);

            //for (i = 0; i < range_input.size;i++) {}
            //First look at range_input[i]. Input data for range_input[i]
            //get group_position, input group_position
            //save the y_axis_xx
            //To get the next group x coordinate position, multiple with index of that group + 1
            //move on to the next one.


            double num_each_pipe_double;
            Integer max_value = Collections.max(value_input); //biggest number in the value_input
            if (max_value >= 23) {
                num_each_pipe_double = Math.floor(max_value / 23);
            } else {
                //
            }
            int num_each_pipe = (int) num_each_pipe_double;
            //y_coordinate = Take the value_input[i] / num_each_pipe
            //round using .round()
            //int y_coordinate_backwards = 23 - y_coordinate because we count from last to start
            //Use that to find the exact String y_axis_xx that we need. Use textual_chart_list.get(y_coordinate_backwards)
            //And then keep repeat it until end of arraylist

        }
        /*
        for (String s : textual_chart) { //Print the chart out
            System.out.println(s);
        } */
    }
}
