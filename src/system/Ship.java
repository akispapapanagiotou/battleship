package system;

abstract class Ship{
    
// Enum to represent the orientation of the ship
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    // Enum to represent the type of ship
    public enum Type {
        CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER
    }
    
    private int size; // Size of the ship
    private Type type; // Type of the ship
	
	
    // Constructor to initialize the size of the ship
    public Ship(int size) {
        this.size = size;
    }
    
    // Getter method to retrieve the size of the ship
    public int getSize() {
        return size;
    }

    // Getter method to retrieve the type of the ship
    public Type getType() {
        return type;
    }

    // Setter method to set the type of the ship
    public void setType(Type type) {
        this.type = type;
    }
    
    
    // Method that places a ship on the board
    public boolean placeShip(Tile startCell, Orientation orientation, Tile[][] board, boolean verbose) {
        int x = startCell.getX();
        int y = startCell.getY();

        if(null == orientation) {
            return false;
        }
        
        else
        switch (orientation) {
            case HORIZONTAL -> {
                for(int i = x; i < x + size; i++) {
                    board[i][y].setTypeTile(Tile.Type.SHIP);
                }
            }
            case VERTICAL -> {
                for(int j = y; j < y + size; j++) {
                    board[x][j].setTypeTile(Tile.Type.SHIP);
                }
            }
            default -> {
                return false;
            }
        }

        // Print message indicating where the ship was placed if verbose is true
        if(verbose) {
            if(orientation == Orientation.HORIZONTAL) {
                System.out.println(getType() + " was placed at (" + x + ", " + y + ") horizontally.");
            }
            else {
                System.out.println(getType() + " was placed at (" + x + ", " + y + ") vertically.");
            }
        }

        return true;
    }
	
    //Exceptions

    class OversizeException extends Exception{
            public OversizeException() {}
    }

    class OverlapTilesException extends Exception{
            public OverlapTilesException() {}
    }

    class AdjacentTilesException extends Exception{
            public AdjacentTilesException() {}
    }
	
    /* This method checks if a ship placed at a given starting cell and orientation would be out of the bounds 
    of the game board. If the ship is out of bounds, an OversizeException is thrown */
    public boolean isShipOutOfArray(Tile startCell, Orientation orientation, Tile array[][], boolean exceptionMessages) {

            int X = startCell.getX();
            int Y = startCell.getY();
            boolean shipOutOfArray = false;

            try {
                    if(orientation == Orientation.HORIZONTAL) {
                            if(X<0 || X >= array.length || X + size > array.length)
                                    shipOutOfArray = true;
                    }
                    else if(orientation == Orientation.VERTICAL) {
                            if(Y<0 || Y >= array[0].length || Y + size > array.length)
                                    shipOutOfArray = true;
                    }

                    if(shipOutOfArray == true) {
                            throw new OversizeException();
                    }
            }
            catch(OversizeException e) {
                    if(exceptionMessages == true) {
                            System.out.println("OversizeException occured! Ship is out of array!");
                    }
            }
            return shipOutOfArray;
    }
	
    /* This method checks if there is another ship placed on the tiles where we want to place a new ship. 
    If there is, an OverlapTilesException is thrown and the method returns true. Otherwise, it returns false. */
    public boolean isThereAnotherShip(Tile startCell, Orientation orientation, Tile array[][], boolean exceptionMessages) {
        int i;
        int X = startCell.getX();
        int Y = startCell.getY();
        boolean thereAnotherShip = false;
	
        try {
            if(orientation == Orientation.HORIZONTAL) {
                for(i = X; i < X + size; i++) {
                        if(array[i][Y].getTypeTile() == Tile.Type.SHIP) {
                                thereAnotherShip = true;
                        }
                }
            }
            else if(orientation == Orientation.VERTICAL) {
                for(i = Y; i < Y + size; i++) {
                    if(array[X][i].getTypeTile() == Tile.Type.SHIP) {
                            thereAnotherShip = true;
                    }
                }
            }

            if(thereAnotherShip == true) {
                throw new OverlapTilesException();
            }
        }
        catch(OverlapTilesException e) {
            if(exceptionMessages == true) {
                System.out.println("OverlapTilesException occured! There is another ship there!");
            }
        }
        return thereAnotherShip;
    }
    
    /* This method checks if a ship is next to another ship in the given 2D Tile array, starting from the provided 
    Tile startCell and oriented according to the provided Orientation. It returns true if a ship is found to be 
    adjacent to the target ship. If exceptionMessages is true, it throws an AdjacentTilesException and prints 
    an appropriate message.*/
    public boolean isShipNextToAnotherShip(Tile startCell, Orientation orientation, Tile array[][], boolean exceptionMessages) {	
        int i;
        int X = startCell.getX();
        int Y = startCell.getY();
        boolean shipNextToAnotherShip = false;

        try {
            if(orientation == Orientation.HORIZONTAL) {
                if(X>0) {
                    if(array[X-1][Y].getTypeTile() == Tile.Type.SHIP) {
                            shipNextToAnotherShip = true;
                    }
                }
                if(X+size<array.length) {
                    if(array[X+size][Y].getTypeTile() == Tile.Type.SHIP) {
                            shipNextToAnotherShip = true;
                    }
                }
                for(i=X; i<X+size; i++) {
                        if(Y>0) {
                            if(array[i][Y-1].getTypeTile() == Tile.Type.SHIP){
                                    shipNextToAnotherShip = true;
                            }
                        }
                        if(Y<array[0].length-1) {
                            if(array[i][Y+1].getTypeTile() == Tile.Type.SHIP) {
                                    shipNextToAnotherShip = true;
                            }
                        }
                }
            }
            else if(orientation == Orientation.VERTICAL) {
                if(Y>0) {
                        if(array[X][Y-1].getTypeTile() == Tile.Type.SHIP) {
                                shipNextToAnotherShip = true;
                        }
                }
                if(Y+size<array.length) {
                        if(array[X][Y+size].getTypeTile() == Tile.Type.SHIP) {
                                shipNextToAnotherShip = true;
                        }
                }
                for(i=Y; i<Y+size; i++) {
                    if(X>0) {
                        if(array[X-1][i].getTypeTile() == Tile.Type.SHIP) {
                                shipNextToAnotherShip = true;
                        }
                    }
                    if(X<array.length-1) {
                        if(array[X+1][i].getTypeTile() == Tile.Type.SHIP) {
                                shipNextToAnotherShip = true;
                        }
                    }
                }
            }

            if(shipNextToAnotherShip == true) {
                    throw new AdjacentTilesException();
            }
        }
        catch(AdjacentTilesException e) {
            if(exceptionMessages == true) {
                    System.out.println("AdjacentTilesException occured! Ship is next to another ship!");
            }
        }
        return shipNextToAnotherShip;
    }
}

//Subclasses of Ship class
class Carrier extends Ship{
        static final int x = 5;
        public Carrier() {
            super(x);
            setType(Type.CARRIER);
        }
} 

class Battleship extends Ship{
        static final int x = 4;
        public Battleship() {
            super(x);
            setType(Type.BATTLESHIP);
        }
}

class Cruiser extends Ship{
        static final int x = 3;
        public Cruiser() {
            super(x);
            setType(Type.CRUISER);
        }
}

class Submarine extends Ship{
        static final int x = 3;
        public Submarine() {
            super(x);
            setType(Type.SUBMARINE);
        }
}

class Destroyer extends Ship{
        static final int x = 2;
        public Destroyer() {
            super(x);
            setType(Type.DESTROYER);
        }
}
