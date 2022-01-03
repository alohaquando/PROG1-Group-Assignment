public class Board {
    static int NumOfRow;
    static int numOfColumn;

    public static void showBoard(int NumOfRow, int numOfColumn){
        for ( int i = 1; i <= 24; i++ ) // group of days
        {
            for ( int j = 1; j <= 80; j++ ) // summary result
            {
                if (j == 1)
                    System.out.print("|");
                else if (i == 24)
                    System.out.print("_");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
        int h = this.getHeight;
        int w = this.getWidth;
    // X axis drawing
        g.drawLine2D(0, h/2, w, h/2);
    // Y axis drawing
        g.drawLine2D(w/2, 0, w/2, h);

        int len = XArrList.length;
        for(int i=0; i < len; i++) {
            g.drawLine2D(XArr[i], YArr[i], XArr[i+1], YArr[i+1]);
        }
    }
    public static void main(String[] args) {
        if(Input.tableType.equals("chart")){
            showBoard(24, 80);
        }
    }
}
