 import java.awt.*; 
 import java.awt.geom.*; 


public class Display{

    private double SCREEN_X = 600;
    private double SCREEN_Y = SCREEN_X * 1.5;
    private double Square_Number = 9;
    private double Board_Size = SCREEN_X;
    private double Square_Size = Board_Size / Square_Number;
    private double pen_Radius = 0.01;

    Block[] blocks;

    public Display(Hand hand) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize((int) SCREEN_X, (int) SCREEN_Y);
        StdDraw.setXscale(0, SCREEN_X);
        StdDraw.setYscale(0, SCREEN_Y);
        blocks = hand.getBlocks();
    }

    public double getSquare_Size() {
        return Square_Size;
    }

    // Master method to draw game board
    public void drawGame(){
        drawBackground();
        drawBoard();
        drawUserBlockGrid();
        drawHand();
        StdDraw.show();
    }

    public void drawCurrentGame(){
        drawBackground();
        drawBoard();
        drawHand();
        StdDraw.show();
    }

    // Draw the entire canvas for game
    private void drawBackground(){
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.setPenRadius(pen_Radius);
        StdDraw.filledRectangle(SCREEN_X/2, SCREEN_Y/2, SCREEN_X/2, SCREEN_Y/2);
    }

    double half_length = Square_Size / 2;
    double user_grid_adjust = (Square_Size - 6);

    // Draw the grid for blocks
    private void drawBoard(){

        double y_adjust = SCREEN_Y - SCREEN_X;

        for (int i = 0 ; i < Square_Number; i++){
            for (int j = 0; j < Square_Number; j++){
                drawBlockOutline(i*Square_Size + half_length, (j*Square_Size) + (y_adjust + half_length));
            }
        }
    }

    // Draw an individual square outline
    private void drawBlockOutline(double x_posn, double y_posn){
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.square(x_posn, y_posn, half_length);

    }


    // Draw the grid for user blocks as a 2x6 and add cords of grid to Blocks[]
    public void drawUserBlockGrid(){
        StdDraw.setPenColor(StdDraw.BLUE);
        //StdDraw.square(user_grid_adjust + half_length, user_grid_adjust + half_length, half_length);
        int n = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 6; j++){
                StdDraw.square(j*Square_Size + user_grid_adjust,i*Square_Size + user_grid_adjust + half_length, half_length);
                double tempX  = j*Square_Size + user_grid_adjust;
                double tempY = i*Square_Size + user_grid_adjust + half_length;
                blocks[n].setX(tempX);
                blocks[n].setY(tempY);
                n++;
                //System.out.println("This grid xposn is: " + blocks[n].getX());
                //System.out.println("The fence post is: " + n);

            }
        }
    }


    public void drawHand() {
        for(int i = 0; i < 12; i++){
            StdDraw.picture(blocks[i].getX(), blocks[i].getY(), blocks[i].getLetter() + "-BLOCK.png", Square_Size, Square_Size);
            //System.out.println("This block x posn is: " + blocks[i].getX());
        }
    }
}
