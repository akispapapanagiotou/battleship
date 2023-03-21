package system;

import java.util.Random;

public class Board{
	
    // Board dimensions
    private final static int M = 7; // First dimension
    private final static int N = 7; // Second dimension
	
    // 2 boards: the user's and the PC's
    public static Tile myBoard[][] = new Tile[M][N];
    public static Tile PCBoard[][] = new Tile[M][N];
    
    // Constructor to initialize both boards
    public Board() {
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < M; i++) {
                // Initialize each tile in the user's board
                myBoard[i][j] = new Tile();
                myBoard[i][j].setTypeTile(Tile.Type.SEA);
                myBoard[i][j].setX(i);
                myBoard[i][j].setY(j);

                // Initialize each tile in the PC's board
                PCBoard[i][j] = new Tile();
                PCBoard[i][j].setTypeTile(Tile.Type.SEA);
                PCBoard[i][j].setX(i);
                PCBoard[i][j].setY(j);
            }
        }
    }
    
    // Getters for the board dimensions
    public static int getFirstDimension() {
            return M;
    }

    public static int getSecondDimension() {
            return N;
    }

    // Method to print both boards side by side
    public static void drawboards() {
        int i, j, c;

        System.out.print("\n -   -   Y   O   U   -   -\t");
        System.out.println("   - O  P  P  O  N  E  N  T -");
        
        for (c = 0; c < M; c++) {
                System.out.print("   " + c);
        }
        System.out.print("\t    ");
        for (c = 0; c < M; c++) {
                System.out.print(c + "   ");
        }
        System.out.print("\n");

        for (j = 0; j < N; j++) {
                System.out.print(j);
                System.out.print("  ");

                for (i = 0; i < M; i++) {
                        myBoard[i][j].draw(false);
                        System.out.print("   ");
                }

                System.out.print("\t ");

                System.out.print(j);
                System.out.print("  ");
                for (i = 0; i < M; i++) {
                        PCBoard[i][j].draw(true);
                        System.out.print("   ");
                }
                System.out.print("\n");
        }

        System.out.print("\n");
    }

    // Place all ships randomly on the board
    public static void placeAllShips(String player) {

        Random rand = new Random();
        int randomX, randomY, randomOrientation, i;
        Ship[] ships = {new Carrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer()};

        if (player.equals("user")) {
            for (i = 0; i < 5; i++) {
                // Randomly choose x, y and orientation until a valid position is found
                while (true) {
                    randomX = rand.nextInt(M);
                    randomY = rand.nextInt(N);
                    randomOrientation = rand.nextInt(2);

                    if (!ships[i].isShipOutOfArray(myBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], myBoard, false) && 
                            !ships[i].isThereAnotherShip(myBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], myBoard, false) &&
                            !ships[i].isShipNextToAnotherShip(myBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], myBoard, false)) {
                        ships[i].placeShip(myBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], myBoard, true);
                        break;
                    }
                }
            }
        } else if (player.equals("PC")) {
            for (i = 0; i < 5; i++) {
                // Randomly choose x, y and orientation until a valid position is found
                while (true) {
                    randomX = rand.nextInt(M);
                    randomY = rand.nextInt(N);
                    randomOrientation = rand.nextInt(2);

                    if (!ships[i].isShipOutOfArray(PCBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], PCBoard, false) && 
                            !ships[i].isThereAnotherShip(PCBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], PCBoard, false) &&
                            !ships[i].isShipNextToAnotherShip(PCBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], PCBoard, false)) {
                        ships[i].placeShip(PCBoard[randomX][randomY], Ship.Orientation.values()[randomOrientation], PCBoard, false);
                        break;
                    }
                }
            }
        }
    }
    
    //This method prints the type of the adjacent tiles of a given tile on the board.
    public void printAdjacentTileTypes(Tile tile) {
        int X = tile.getX();
        int Y = tile.getY();

        if(X>0) {
                System.out.println("("+(X-1)+", "+Y+"): " + myBoard[X-1][Y].getTypeTile());
        }
        if(X<myBoard.length-1) {
                System.out.println("("+(X+1)+", "+Y+"): " +myBoard[X+1][Y].getTypeTile());
        }
        if(Y>0) {
                System.out.println("("+X+", "+(Y-1)+"): " +myBoard[X][Y-1].getTypeTile());
        }
        if(Y<myBoard[0].length-1) {
                System.out.println("("+X+", "+(Y+1)+"): " +myBoard[X][Y+1].getTypeTile());
        }
    }
    
    /* The method "allShipsSunk()" checks if all ships are sunk by counting the number of "Ship" 
    type tiles on both boards, and returning true if one of the counts is zero. */
    public static boolean allShipsSunk() {
        int s1 = countShips(myBoard);
        int s2 = countShips(PCBoard);

        return (s1 == 0 || s2 == 0);
    }

    /* This method takes a board as a parameter and returns the count of "Ship" type tiles.*/
    private static int countShips(Tile[][] board) {
            int count = 0;
            for(int j=0; j<N; j++) {
                    for(int i=0; i<M; i++) {
                            if(board[i][j].getTypeTile() == Tile.Type.SHIP) {
                                    count++;
                            }
                    }
            }
            return count;
    }
}