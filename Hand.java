import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.awt.*;

public class Hand extends Block {
    private Block[] blocks;
    private int numBlocks;

    public Hand() {
        numBlocks = 12;
        blocks = new Block[numBlocks];
        for(int i = 0; i < numBlocks; i++) {
            Random r = new Random();
            char charLetter = (char) (r.nextInt(26) + 'A');
            blocks[i] = new Block(charLetter);
        }
    }

    public Hand(int numBlocks) {
        this.numBlocks = numBlocks;
        blocks = new Block[numBlocks];
        for(int i = 0; i < numBlocks; i++) {
            Random r = new Random();
            char charLetter = (char) (r.nextInt(26) + 'A');
            blocks[i] = new Block(charLetter);
        }
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public void decrementNumBlocks() {
        numBlocks--;
    }

    public boolean isSpaceFilled(Point gridSpace) {
        for(int i = 0; i < 12; i++) {
            if(blocks[i].getGridSpace().equals(gridSpace)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGridFull() {
        for(int i = 0; i < 12; i++) {
            if(blocks[i].getGridSpace().x == -1 && blocks[i].getGridSpace().y == -1) {
                return false;
            }
        }
        return true;
    }

    public Block getGridBlock(Point gridSpace) {
        int x = gridSpace.x;
        int y = gridSpace.y;
        for(int i = 0; i < 12; i++) {
            if(blocks[i].getGridSpace().x == x && blocks[i].getGridSpace().y == y) {
                return blocks[i];
            }
        }
        return new Block();
    }

    //checks to see if all blocks in game are 1) placed in grid 2) touching another block
    public boolean isGridValid() {
        for(int i = 0; i < 12; i++) {
            int x = blocks[i].getGridSpace().x;
            int y = blocks[i].getGridSpace().y;
            if(!isSpaceFilled(new Point(x - 1, y)) && !isSpaceFilled(new Point(x + 1, y))
            && !isSpaceFilled(new Point(x, y - 1)) && !isSpaceFilled(new Point(x, y + 1))) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidWord(String word) throws IOException {
        BufferedReader dictionary = new BufferedReader(new FileReader("/Users/vasco/Desktop/CLABBERS/Clabbers-main/Clabbers Game/src/Dictionary.txt"));
        for (String currentWord = dictionary.readLine(); currentWord != null; currentWord = dictionary.readLine()) {
            if(currentWord.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameWon() throws IOException {
        Block currBlock;
        Point currPoint;
        String currString = "";
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                currPoint = new Point(column, row);
                if(isSpaceFilled(currPoint)) {
                    currBlock = getGridBlock(currPoint);
                    currString += currBlock.getLetter();
                    System.out.println("Test 1");
                } else if(!isSpaceFilled(currPoint) && currString.length() > 1 && !isValidWord(currString)) {
                    System.out.println("Test 2");
                    return false;
                    } else if(!isSpaceFilled(currPoint) && currString.length() == 1) {
                        currString = "";
                        System.out.println("Test 3");
                    } else if(!isSpaceFilled(currPoint) && currString.length() > 1) {
                        currString = "";
                    }
                }
            }
            for(int column = 0; column < 9; column++) {
                for(int row = 0; row < 9; row++) {
                    currPoint = new Point(column, row);
                    if(isSpaceFilled(currPoint)) {
                        currBlock = getGridBlock(currPoint);
                        currString += currBlock.getLetter();
                        System.out.println("Test 4");
                    } else if(!isSpaceFilled(currPoint) && currString.length() > 1 && !isValidWord(currString)) {
                        System.out.println("Test 5");
                        return false;
                        } else if(!isSpaceFilled(currPoint) && currString.length() == 1) {
                            currString = "";
                            System.out.println("Test 6");
                        } else if(!isSpaceFilled(currPoint) && currString.length() > 1) {
                            currString = "";
                        }
                    }
                }
                return true;
        }
        
}