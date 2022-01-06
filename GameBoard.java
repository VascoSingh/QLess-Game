import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.awt.Color;

public final class GameBoard {

    private final static double min_Screen_X = 0;
    private final static double min_Screen_Y = 0;
    private final static double max_Screen_X = 600;
    private final static double max_Screen_Y = 900;
    private final static int square_Number = 9;
    private final static int num_Blocks = 12;
    private final static double block_Size = max_Screen_X / square_Number;
    private final static double offset_X = max_Screen_X - (block_Size * square_Number) + block_Size / 2;
    private final static double offset_Y = max_Screen_Y - (block_Size * square_Number) + block_Size / 2;
    private final static double pen_Radius = 0.01;

    private static Block[][] _grid;

    public final static Block[][] get_grid() {
        if (_grid == null) {
            _grid = new Block[square_Number][square_Number];
            for (int i = 0; i < square_Number * square_Number; i++) {
                int row = i / square_Number;
                int col = i % square_Number;
                _grid[row][col] = new Block(' ');
                _grid[row][col].setX((int) (col * block_Size + offset_X));
                _grid[row][col].setY((int) (row * block_Size + offset_Y));
            }
            return _grid;
        }
        return _grid;
    }

    private final static Block[][] grid = get_grid();
    private static Block[] hand;

    public static void startGame() {
        hand = dealHand(num_Blocks);
        drawGame();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (StdDraw.nextKeyTyped()) {
                    case 'q': // space bar -> end game
                        System.out.println("GoodBye!");
                        System.exit(0);
                }
            }
            StdDraw.show();
            if (StdDraw.isMousePressed()) {
                dragAndDrop();
            } else {
                validateGrid();
            }
        }
    }

    private static void dragAndDrop() {
        var mouseX = (int) StdDraw.mouseX();
        var mouseY = (int) StdDraw.mouseY();
        for (int i = 0; i < num_Blocks; i++) {
            var blockX = hand[i].getX();
            var blockY = hand[i].getY();
            if (isInProximity(mouseX, mouseY, blockX, blockY)) {
                while (StdDraw.isMousePressed()) {
                    blockX = (int) StdDraw.mouseX();
                    blockY = (int) StdDraw.mouseY();
                    hand[i].setX(blockX);
                    hand[i].setY(blockY);
                    drawCurrentGame();
                }
                for (int j = 0; j < square_Number * square_Number; j++) {
                    int row = j / square_Number;
                    int col = j % square_Number;
                    int grid_X = grid[row][col].getX();
                    int grid_Y = grid[row][col].getY();
                    if (isInProximity(blockX, blockY, grid_X, grid_Y)) {
                        hand[i].setX(grid_X);
                        hand[i].setY(grid_Y);
                        drawCurrentGame();
                    }
                }
            }
        }
    }

    private static void validateGrid() {
        var wordBag = new ArrayList<String>();
        StringBuilder hWords = new StringBuilder();
        StringBuilder vWords = new StringBuilder();
        for (int i = 0; i < square_Number * square_Number; i++) {
            wordBag.clear();
            StringBuilder sbh = new StringBuilder();
            StringBuilder sbv = new StringBuilder();
            int row = i / square_Number;
            int col = i % square_Number;
            for (int j = 0; j < num_Blocks; j++) {
                // reset
                if (!isBlockOverlapping(hand[j], grid[row][col]) && grid[row][col].getId() == hand[j].getId()) {
                    grid[row][col].setLetter(' ');
                    grid[row][col].setId(0);
                }
                // confirm in grid
                if (isBlockOverlapping(hand[j], grid[row][col]) && grid[row][col].getId() == 0) {
                    grid[row][col].setLetter(hand[j].getLetter());
                    grid[row][col].setId(hand[j].getId());
                }
                // prevent card overlap
                if (isBlockOverlapping(hand[j], grid[row][col]) && grid[row][col].getId() != hand[j].getId()) {
                    hand[j].setX(hand[j].getX_orig());
                    hand[j].setY(hand[j].getY_orig());
                }
            }
            if (grid[col][row].getLetter() != ' ') {
                sbv.append(grid[col][row].getLetter());
            } else if (grid[col][row].getLetter() == ' ') {
                sbv.setLength(0);
            }
            if (grid[row][col].getLetter() != ' ') {
                sbh.append(grid[row][col].getLetter());
            } else if (grid[row][col].getLetter() == ' ') {
                sbh.setLength(0);
            }
            sbh.append("\"");
            sbv.append("\"");
            hWords.append(sbh.toString());
            vWords.append(sbv.toString());
            ExtractWords(hWords, wordBag);
            ExtractWords(vWords, wordBag);
        }
        clearScreen();
        for (String string : wordBag) {
            if (isValidWord(string)) {
                System.out.println("Word Found: " + string + " " + word_List.get(string));
            }
        }
        drawCurrentGame();
    }

    private static void ExtractWords(StringBuilder sb, ArrayList<String> dest) {
        var chunks = sb.toString().split("\"{2,}");
        for (String chunk : chunks) {
            chunk = chunk.replaceAll("\"", "");
            if (chunk.length() > 1) {
                var reverse = new StringBuffer(chunk).reverse().toString();
                dest.add(chunk);
                dest.add(reverse);
            }
        }
    }

    private static Block[] dealHand(int numblocks) {
        var _blocks = new Block[numblocks];
        for (int i = 0; i < numblocks; i++) {
            Random r = new Random();
            char charLetter = (char) (r.nextInt(26) + 'A');
            _blocks[i] = new Block(charLetter);
        }
        return _blocks;
    }

    private static boolean isBlockOverlapping(Block A, Block B) {
        return (A.getX() == B.getX() && A.getY() == B.getY());
    }

    private static boolean isInProximity(int X, int Y, int blockX, int blockY) {
        {
            if (Math.abs(X - blockX) < block_Size / 2
                    && Math.abs(Y - blockY) < block_Size / 2) {
                return true;
            }
            return false;
        }
    }

    private static Hashtable<String, String> word_List = getWord_List();
    private static Hashtable<String, String> _word_List;

    private static Hashtable<String, String> getWord_List() {
        if (_word_List == null) {
            try {
                _word_List = loadDictionary();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return _word_List;
    }

    private static boolean isValidWord(String word) {
        if (word_List.containsKey(word)) {
            return true;
        } else {
            return false;
        }
    }

    private static Hashtable<String, String> loadDictionary() throws IOException {
        var _word_List = new Hashtable<String, String>();
        try (BufferedReader dictionary = new BufferedReader(
                new FileReader("Assets/Dictionary.txt"))) {
            for (String currentWord = dictionary.readLine(); currentWord != null; currentWord = dictionary.readLine()) {
                int i = currentWord.indexOf(' ');
                _word_List.put(currentWord.substring(0, i), currentWord.substring(i));
            }
        }
        return _word_List;
    }

    // Master method to draw game board
    private static void drawGame() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize((int) max_Screen_X, (int) max_Screen_Y);
        StdDraw.setXscale(min_Screen_X, max_Screen_X);
        StdDraw.setYscale(min_Screen_Y, max_Screen_Y);
        drawBackground();
        drawBoard();
        drawInitUserBlockGrid();
        drawHand();
        StdDraw.show();
    }

    private static void drawCurrentGame() {
        drawBackground();
        drawBoard();
        drawHand();
        drawUserBlockGrid();
        StdDraw.show();
    }

    // Draw the entire canvas for game
    private static void drawBackground() {
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.setPenRadius(pen_Radius);
        StdDraw.filledRectangle(800, 600, 800, 600);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Draw the grid for blocks
    private static void drawBoard() {

        for (int i = 0; i < square_Number * square_Number; i++) {
            int row = i / square_Number;
            int col = i % square_Number;
            drawBlockOutline(grid[row][col], Color.RED);
        }
        // FOR GRID DEBUGGING ONLY
        // clearScreen();
        // System.out.println(Arrays.deepToString(grid)
        // .replace("],", "\n").replace(",", "\t| ")
        // .replaceAll("[\\[\\]]", " "));
    }

    // Draw an individual square outline
    private static void drawBlockOutline(Block _block, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.square(_block.getX(), _block.getY(), block_Size / 2);
    }

    // Draw the grid for user blocks as a 2x6 and add cords of grid to Blocks[]
    private static void drawInitUserBlockGrid() {
        StdDraw.setPenColor(StdDraw.BLUE);
        for (int i = 0; i < num_Blocks; i++) {
            int row = i / 6;
            int col = i % 6;
            hand[i].setX_orig((int) (col * block_Size + offset_X));
            hand[i].setY_orig((int) (row * block_Size + block_Size / 2));
            hand[i].setId(i);
            hand[i].setX(hand[i].getX_orig());
            hand[i].setY(hand[i].getY_orig());
            drawBlockOutline(hand[i], Color.BLUE);
        }
    }

    private static void drawUserBlockGrid() {
        StdDraw.setPenColor(StdDraw.BLUE);
        for (int i = 0; i < num_Blocks; i++) {
            drawBlockOutline(hand[i], Color.BLUE);
        }
    }

    private static void drawHand() {
        for (int i = 0; i < num_Blocks; i++) {
            StdDraw.picture(hand[i].getX(), hand[i].getY(), "Assets/" + hand[i].getLetter() + "-BLOCK.png",
                    block_Size,
                    block_Size);
        }
    }
}
