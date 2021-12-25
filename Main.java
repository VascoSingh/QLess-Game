import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.*;

public class Main {


    // Global objects
    


    public static void main(String[] args) throws Exception {

        boolean inPlay = true;
        Hand hand = new Hand();
        Block[] blocks = hand.getBlocks();
        Display background = new Display(hand);
        background.drawGame();
        int time = 0;

        while(true){

            if (StdDraw.hasNextKeyTyped() && inPlay){
                switch (StdDraw.nextKeyTyped()) {
                    case ' ': // space bar -> end game
                        inPlay = false;
                        break;
                }

            }
            //if(hand.isGridFull()) {
                //System.out.println("Grid is full");
            //}
            if(hand.isGridValid()) {
                System.out.println(hand.isGameWon());
            }
            
            StdDraw.show();
            StdDraw.pause(20);
                //do not change under any circumstances
                if(StdDraw.isMousePressed()) {
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                for(int i = 0; i < hand.getNumBlocks(); i++) {
                    double blockX = blocks[i].getX();
                    double blockY = blocks[i].getY();
                    if(Math.abs(mouseX - blockX) < background.getSquare_Size() / 2
                    && Math.abs(mouseY - blockY) < background.getSquare_Size() / 2) {
                        while(StdDraw.isMousePressed()) {
                            blockX = StdDraw.mouseX();
                            blockY = StdDraw.mouseY();
                            StdDraw.clear();
                            blocks[i].setX(blockX);
                            blocks[i].setY(blockY);
                            blocks[i].setGridSpace(new Point(-1, -1));
                            background.drawCurrentGame();
                    }
                    blockX = StdDraw.mouseX();
                    blockY = StdDraw.mouseY();
                    for(double x = 33.3333; x < 600; x += 66.6666) {
                        for(double y = 866.6666; y > 300; y -= 66.6666) {
                            //System.out.println("Still working");
                            Point gridPoints = new Point((int)((x-33.3333)/66), (int)(Math.abs(y-866.6666)/66));
                            if(Math.abs(x - blockX) < background.getSquare_Size() / 2
                            && Math.abs(y - blockY) < background.getSquare_Size() / 2) {
                                if(!hand.isSpaceFilled(gridPoints)) {
                                StdDraw.clear();
                                blocks[i].setX(x);
                                blocks[i].setY(y);
                                blocks[i].setGridSpace(gridPoints);
                                System.out.println("Block " + i + " Point is " + blocks[i].getGridSpace());
                                background.drawCurrentGame();
                                } else if (hand.isSpaceFilled(gridPoints) || x < 0 || x > 600 || y < 300 || y > 900) {
                                    blocks[i].setX((i%6)*background.getSquare_Size()+(background.getSquare_Size() - 6)); //check to make sure int works
                                    blocks[i].setY((i/6)*background.getSquare_Size()+(background.getSquare_Size() - 6)+(background.getSquare_Size() /2 ));
                                    blocks[i].setGridSpace(new Point(-1, -1));
                                    StdDraw.clear();
                                    background.drawCurrentGame();
                                }
                            } 
                        }
                    }
                    }
                }
            }
        }  
    }


}
