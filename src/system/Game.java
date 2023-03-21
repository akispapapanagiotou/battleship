package system;

import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.util.Random;

public class Game {
    
    private static int arrayOfTwoCoordinates[];  //pinakas ton 2 syntetagmenon
    private static final Scanner input = new Scanner(System.in);
    private static final Reader charInput = new InputStreamReader(System.in);
    private static final Random rand = new Random();
	
    // This is the main method of a Nautical Game program
    public static void main(String args[]) {
        
// Set up the game
        Board board = new Board();
        Player user = new Player();
        Player computer = new Player();
        user.setName("User");
        computer.setName("Computer");
		
        System.out.println("------------------\n--- BATTLESHIP ---\n------------------\n\n");
        System.out.println("Do you want to place the ships in random manner? (Y/N)");
        
        boolean isRandomPlace = randomPlace();
        if (isRandomPlace) {
            user.placeAllShips("user");
        } else {		
            /* This code firstly creates an array of Ship objects, then prompts the user to input the coordinates
            and oriantation of each ship and places it on the game board using the 'placeShip' method of user. 
            The loop continues until all ships are placed successfully. */
            Ship arrayOfShips[] = new Ship[5];

            arrayOfShips[0] = new Carrier();
            arrayOfShips[1] = new Battleship();
            arrayOfShips[2] = new Cruiser();
            arrayOfShips[3] = new Submarine();
            arrayOfShips[4] = new Destroyer();  

            for(int i=0; i<5; i++) {
                    do {
                            System.out.println("Enter the coordinates for the " + arrayOfShips[i].getType() + ": ");
                            arrayOfTwoCoordinates = getInput();
                            System.out.println("Enter the orientation for the " + arrayOfShips[i].getType() + ": (H/V)");
                            user.placeShip(arrayOfShips[i], arrayOfTwoCoordinates, getOrientation());
                    }
                    while(user.getIsShipPlaced() == false);
            }	
        }
        
        computer.placeAllShips("PC");
        Board.drawboards();
        
        /* This code represents the game loop where players take turns firing shots at each other's boards 
        until all ships of one player are sunk. */
        do {
            System.out.print("Enter the coordinates to fire: ");
            arrayOfTwoCoordinates = getInput();
            user.fire(Board.PCBoard, arrayOfTwoCoordinates);

            arrayOfTwoCoordinates = getRandInput();
            computer.fire(Board.myBoard, arrayOfTwoCoordinates);
            Board.drawboards();
        }
        while(Board.allShipsSunk() == false);
		
		
        // Determine the winner and display stats

        if(user.getSuccessfulShots() > computer.getSuccessfulShots()) {
                System.out.println(user.getName() + " is the winner!");
        }
        else if(user.getSuccessfulShots() < computer.getSuccessfulShots()) {
                System.out.println(computer.getName() + " is the winner!\n");
        }

        user.getStats();
        computer.getStats();
    }

    // Method to get user input for two coordinates from the keyboard and return as an array of two integers
    public static int[] getInput() {
        
        int twoCoordinates[] = new int[2];
        do {
            if(twoCoordinates[0]<0 || twoCoordinates[1]<0 || 
                    twoCoordinates[0]>(Board.getFirstDimension()-1) || 
                    twoCoordinates[1]>(Board.getSecondDimension()-1)) {
            System.out.println("Enter correct coordinates: ");
        }
        String string = input.nextLine();
        String stringToArray[] = string.split(" ");
        
        for(int i=0; i<2; i++) {
            twoCoordinates[i] = Integer.parseInt(stringToArray[i]);
            }
        }
        while(twoCoordinates[0]<0 || twoCoordinates[1]<0 || twoCoordinates[0]>(Board.getFirstDimension()-1) 
                || twoCoordinates[1]>(Board.getSecondDimension()-1));
        
        return twoCoordinates;
    }
    
    // Method to generate and return an array of two random integers
    public static int[] getRandInput() {
        // Initialize an array to hold two random coordinates
        int twoRandomCoordinates[] = new int[2];
        // Generate two random integers within the board dimensions
        twoRandomCoordinates[0] = rand.nextInt(Board.getFirstDimension()-1);
        twoRandomCoordinates[1] = rand.nextInt(Board.getSecondDimension()-1);
        return twoRandomCoordinates;
    }   

    /* Method to get user input for orientation (horizontal or vertical) from the keyboard and return as 
    an enum value*/
    public static Ship.Orientation getOrientation() {
        int ch = 0;
        try {
            ch = charInput.read();
        } 
        catch(IOException e) {}
        
        Ship.Orientation orientation;
        switch (ch) {
            case 'H': 
                orientation = Ship.Orientation.HORIZONTAL; 
                break;
            case 'V' : 
            default:
                orientation = Ship.Orientation.VERTICAL; 
                break;
        }
        
        return orientation;
    }
   
    /* The method takes user input from a character input stream and returns a boolean value 
    based on the user's input. */
    public static boolean randomPlace() {
    try {
        int inputChar = charInput.read();
        boolean value;
        switch (inputChar) {
           case 'Y': 
               value = true;
               break;
           case 'N': 
           default:
               value = false;
               break;
        }
        return value; 
    } catch (IOException e) {
        return false;
        }
    }
    
}