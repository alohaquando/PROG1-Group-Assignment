import java.util.List;

public class DataDisplay {

    // Needs calculation first
    private static void convertListObjectsToData (List<List<Object>> data) {

    }

    /*
     * display summary data in a table.
     * There are 2 columns: the first column named “Range” and the second column named “Value”.
     * In the table, display a row for each group. For each group, the “Range” column shows “date1 – date2”
     * where date1 and date2 are the first date and last date of a group respectively. If a group contains
     * just 1 date, shows that date only. The “Value” column of a group shows the calculated value
     * (New Total or Up To) described above.
     */

    static void tabularDisplay(Object[] data) {
        System.out.println("Range \t\t\tValue");
        System.out.println("----------------------------------");
        System.out.println("date1 - date2");
        System.out.println(data[2] + "\t\t\t\t" + data[3]);
    }

    /*
     * display summary data in a textual chart.
     * The chart area consists of 24rows x 80cols.
     * The x-coordinate direction is left to right,and the y-coordinate direction is from bottom to top.
     * The x-coordinate represents the groups and the y-coordinate represents the calculated summary results.
     * You should position the groups as equally as possible on the x-coordinate. And you should use the minimum
     * and maximum result values to position a result on the y-coordinate linearly. The left-most column should
     * display all|(pipe)characters while the bottom-most row should display all _(underscore)characters.
     * (That means you have 23rows and 79columns left to display data points).Each summary data point is
     * represented as an asterisk *.
     */

    static void chartDisplay(Object[] data) {

        int rows = 24, columns = 80;
        int[][] chart = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                chart[i][j] = i + j;
            }
        }

        System.out.println("Chart display");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(i == rows -1) {
                    System.out.print("_");
                }
                if(j == 0) {
                    System.out.print("|");
                }
                else {
                    System.out.print("");
                }
            }
            System.out.println();
        }
    }
}