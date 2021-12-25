import java.awt.*;

public class Block {
    private char letter;
    private double x;
    private double y;
    private Point gridSpace;

    public Block() {
        letter = ' ';
        double x = -1;
        double y = -1;
        gridSpace = new Point(-1, -1);
    }
    
    public Block(char letter) {
        this.letter = letter;
        double x = -1;
        double y = -1;
        gridSpace = new Point(-1, -1);
    }

    public char getLetter() {
        return letter;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point getGridSpace() {
        return gridSpace;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setGridSpace(Point gridSpace) {
        this.gridSpace = gridSpace;
    }

    public boolean isBlockEmpty(Block block) {
        return (block.getLetter() == ' ');
    }

}
