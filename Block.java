public class Block {
    private int id;
    private char letter;
    private int x;
    private int y;
    private int x_orig;
    private int y_orig;

    private Block() {
        setId(0);
        setLetter(' ');
        setX(-1);
        setY(-1);
    }

    public Block(char _letter) {
        this();
        setLetter(_letter);
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public int getX_orig() {
        return x_orig;
    }

    public void setX_orig(int x_orig) {
        this.x_orig = x_orig;
    }

    public int getY_orig() {
        return y_orig;
    }

    public void setY_orig(int _y_orig) {
        y_orig = _y_orig;
    }

    public char getLetter() {
        return letter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLetter(char _letter) {
        letter = _letter;
    }

    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }

    @Override
    public String toString() {
        return "" + getLetter();
    }

    public boolean isBlockEmpty() {
        return (getLetter() == ' ');
    }
}
