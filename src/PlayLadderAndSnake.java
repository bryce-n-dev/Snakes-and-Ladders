import java.util.Scanner;

/**
 * Game driver for snakes and ladders.
 */
public class PlayLadderAndSnake {

    public static void main(String[] args) {

        //Welcome Message
        System.out.println("Student: Bryce Nichol");
        System.out.println("Welcome to snakes and ladders!");
        System.out.println(" ");

        //initialize variables
        int numOfPlayers = 0;
        int attempts = 0;
        final int MIN_NUM_OF_PLAYERS = 2;
        final int MAX_NUM_OF_PLAYERS = 4;

        //Get input from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of players for your game. Number must be between 2 and 4 inclusively: ");


        //Loops until player either: enters a valid numOfPlayers OR attempts surpasses 4
        while (attempts < 4) {
            String input = scanner.next();

            //Try-catch to see if input is a number
            try {
                numOfPlayers = Integer.parseInt(input);
            } catch(NumberFormatException e) {
                System.out.println("Input was not an integer.");
            }


            //If invalid numOfPlayers, add 1 to attempts
            if (numOfPlayers < MIN_NUM_OF_PLAYERS || numOfPlayers > MAX_NUM_OF_PLAYERS) {
                attempts++; //Add 1 to attempts
                if (attempts == 4) { //If attempts limit reached then do not prompt for input
                    break;
                }
                System.out.println("Bad attempt " + attempts + " - invalid # of players. You have a total of 4 attempts. Please enter a valid number of players: ");
            } else {
                break; //user entered a valid numOfPlayers
            }
        }

        //Checks if numOfPlayers condition is met
        if (numOfPlayers >= MIN_NUM_OF_PLAYERS && numOfPlayers <= MAX_NUM_OF_PLAYERS) {
            //Create LadderAndSnake object and start game
            LadderAndSnake game = new LadderAndSnake(numOfPlayers);
            game.play();
            System.out.println("Game is finished. Program will terminate.");
        } else {
            System.out.println("You have exhausted all of your chances. Program will terminate.");
        }

    }

}
