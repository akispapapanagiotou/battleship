package system;

public class Player {
    
    // Statistics of the player
    private String name;
    private int totalShots = 0;
    private int missedShots = 0;
    private int successfulShots = 0;
    private int repeatedShots = 0;

    private boolean isShipPlaced;

    public Player() {}

    // Setters, Getters
    public void setName(String name) {
            this.name = name;
    }

    public String getName() {
            return name;
    }

    public int getSuccessfulShots() {
            return successfulShots;
    }

    public boolean getIsShipPlaced() {
            return isShipPlaced;
    }

    // Places all the ships of the player
    public void placeAllShips(String s) {
        Board.placeAllShips(s);
    }

    // Places a single ship at a given position and orientation
    public void placeShip(Ship ship, int array[], Ship.Orientation orientation) {
            if(ship.isShipOutOfArray(Board.myBoard[array[0]][array[1]], orientation, Board.myBoard, true) == false &&
                            ship.isThereAnotherShip(Board.myBoard[array[0]][array[1]], orientation, Board.myBoard, true) == false &&
                            ship.isShipNextToAnotherShip(Board.myBoard[array[0]][array[1]], orientation, Board.myBoard, true) == false) {
                    ship.placeShip(Board.myBoard[array[0]][array[1]], orientation, Board.myBoard, true);
                    isShipPlaced = true;
            }
            else
                    isShipPlaced = false;
    }

    // Fires at a given position on the board and updates statistics accordingly
    public void fire(Tile boardToFire[][], int array[]) {

            if(null != boardToFire[array[0]][array[1]].getTypeTile()) 
                switch (boardToFire[array[0]][array[1]].getTypeTile()) {
                    case SHIP:  
                        boardToFire[array[0]][array[1]].setTypeTile(Tile.Type.HIT);
                        System.out.println(name + " hit!");
                        totalShots++;
                        successfulShots++;
                        break;
                    case SEA:
                        boardToFire[array[0]][array[1]].setTypeTile(Tile.Type.MISS);
                        System.out.println(name + " missed!");
                        totalShots++;
                        missedShots++;
                        break;
                    case HIT:
                        System.out.println(name + " already hit there!");
                        totalShots++;
                        repeatedShots++;
                        break;
                    case MISS:
                        System.out.println(name + " already missed there!");
                        totalShots++;
                        repeatedShots++;
                        break;
                    default:
                        break;
                }
    }

    // Prints out the statistics of the player
    public void getStats() {
            System.out.println("Name: " + name);
            System.out.println("Total shots: " + totalShots);
            System.out.println("Missed shots: " + missedShots);
            System.out.println("Successful shots: " + successfulShots);
            System.out.println("Repeated shots: " + repeatedShots);
            System.out.print("\n");
    }
}
