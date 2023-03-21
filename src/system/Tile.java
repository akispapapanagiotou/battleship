package system;

public class Tile {
     
    private int x, y; // Coordinates of the tile
    
    // Enumeration of possible types for a tile
    public enum Type {
        SEA, SHIP, HIT, MISS
    }

    private Type typeTile; // The type of the tile
    
    // Setter and Getter methods for the coordinates x and y
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
    
    // Method to set the type of the tile
    public void setTypeTile(Type typeTile){
        switch(typeTile) {
            case SEA:
                this.typeTile = Type.SEA;
                break;

            case SHIP:
                this.typeTile = Type.SHIP;
                break;

            case HIT:
                this.typeTile = Type.HIT;
                break;

            case MISS:
                this.typeTile = Type.MISS;
                break;

            default:
                break;
        }
    }

    // Method to get the type of the tile
    public Type getTypeTile(){
            return typeTile;
    }	
	
    /*This method takes a boolean parameter hidden and prints out the corresponding symbol for each type 
    of tile based on the value of typeTile.*/
    public void draw(Boolean hidden) {
        switch(typeTile) {
        case SEA:
            System.out.print("~");
            break;
        case SHIP:
            if(hidden == true)
                System.out.print("~");
            else
                System.out.print("s");
            break;

        case HIT: 
            System.out.print("X");
            break;

        case MISS:
            System.out.print("o");
            break;

        default:
            break;
        }
    }
}