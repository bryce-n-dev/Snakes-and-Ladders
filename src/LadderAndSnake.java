import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * LadderAndSnake - where the game logic is performed
 */
public class LadderAndSnake {

    //Fields
    private final int NUM_OF_PLAYERS;
    private final int[] BOARD = Board.getBoard(); //array index corresponds to actual space on board
    private boolean win = false;
    private Player[] playerOrder; //stores player objects and loops through for each turn
    private Player humanPlayer = null; //keeps track of which player object is not a computer


    //Constructor

    /**
     * Creates a LadderAndSnake object (used to play the game)
     * @param numOfPlayers number of players that are playing the game
     */
    public LadderAndSnake(int numOfPlayers) {
        NUM_OF_PLAYERS = numOfPlayers;
        playerOrder = new Player[NUM_OF_PLAYERS];
    }


    //Public methods

    /**
     * Method is called to start and run the snakes and ladders game
     */
    public void play() {

        //Get player order
        playerOrder = getPlayerOrder(createPlayers());

        //Keeps track of current player index for the array
        int currentPlayerIndex = -1;

        //Loop until a winner is found
        while (!win) {

            //Update to current player
            if (currentPlayerIndex < NUM_OF_PLAYERS - 1) {
                currentPlayerIndex++;
            } else {
                currentPlayerIndex = 0; //Avoids index array out of bounds
            }

            //If current player is human, call playerCommands() function
            Player currentPlayer = playerOrder[currentPlayerIndex];
            if (currentPlayer == humanPlayer) {
                playerCommands();
            }


            //Let current player role die
            int dieInt = flipDie();
            System.out.println(currentPlayer.getName() + " rolled a " + dieInt);

            //move player
            movePlayer(currentPlayer, dieInt);

            //checks for win
            if (currentPlayer.getPosition() == 100) {
                win = true; //player won
            }

        }

        //Once game is over, print the name of the winner
        printWinMessage();
    }


    //Helper methods

    /**
     * Creates Player objects and stores them in an ArrayList
     * @return array that stores Player objects
     */
    private Player[] createPlayers() {

        Scanner scanner = new Scanner(System.in);
        Player[] players = new Player[NUM_OF_PLAYERS];
        int playersAdded = 0;

        System.out.println("The first player is you! The rest will be computers.");

        //Loop until proper number of players has been created and added to array
        while (playersAdded < NUM_OF_PLAYERS) {
            int currentPlayer = playersAdded + 1;
            System.out.println("Please enter a name for player " + currentPlayer + ": ");
            String name = scanner.nextLine();
            players[playersAdded] = new Player(name); //Add Player object to array

            //Set humanPlayer to first player entered
            if (playersAdded == 0) {
                humanPlayer = players[0];
            }

            playersAdded++;
        }

        return players;

    }

    /**
     * Players try and roll highest for playing order in the game. Players who tie must re-roll.
     * @param playerArray array that stores all of the Player objects for the game
     * @return sorted array that stores the players. This is used to keep track of the player order for the game
     */
    private Player[] getPlayerOrder(Player[] playerArray) {

        double[] playerRolls = new double[NUM_OF_PLAYERS]; //Stores player roll values
        boolean isDuplicates = true;

        //Initial rolls, save to array. Array index corresponds to the same index as in the playerArray
        System.out.println("We will now roll for order of who goes first!");
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            double rollValue = flipDie();
            System.out.println(playerArray[i].getName() + " rolled a " + (int) rollValue);
            playerRolls[i] = rollValue;
        }

        //Checks for duplicates
        while (isDuplicates) {

            //Bubble sort the copied array, sorting the players at the same time
            for (int i = 0; i < playerRolls.length; i++) {
                for (int j = 0; j < playerRolls.length - 1 - i; j++) {
                    if (playerRolls[j] > playerRolls[j + 1]) {
                        double temp = playerRolls[j];
                        playerRolls[j] = playerRolls[j + 1];
                        playerRolls[j + 1] = temp;

                        //Sorting players according
                        Player tempPlayer = playerArray[j];
                        playerArray[j] = playerArray[j + 1];
                        playerArray[j + 1] = tempPlayer;

                    }
                }
            }

            //Invert the arrays
            for(int i = 0; i < playerRolls.length / 2; i++) {
                double temp = playerRolls[i];
                playerRolls[i] = playerRolls[playerRolls.length - i - 1];
                playerRolls[playerRolls.length - i - 1] = temp;

                //Player array
                Player tempPlayer = playerArray[i];
                playerArray[i] = playerArray[playerArray.length - i - 1];
                playerArray[playerArray.length - i - 1] = tempPlayer;
            }


            //Check for duplicates in playerRolls array. If duplicates are present, store the index at where the duplicates are found.
            ArrayList<Integer> duplicatesIndex = new ArrayList<>();

            for (int i = 0; i < NUM_OF_PLAYERS; i++) {
                for (int j = i + 1; j < NUM_OF_PLAYERS; j++) {
                    if (playerRolls[i] == playerRolls[j]) {
                        if (!duplicatesIndex.contains(i)) {
                            duplicatesIndex.add(i);
                        }
                        if (!duplicatesIndex.contains(j)) {
                            duplicatesIndex.add(j);
                        }
                    }
                }
            }

            //if duplicates, make the respective players re-roll and  add 0.001 * rollValue to their respective array spots. Loop continues and resorts, based on these new values
            if (!duplicatesIndex.isEmpty()) {
                System.out.println("Players who tied must now re-roll.");
                for (int index : duplicatesIndex) {
                    double roleValue = flipDie();
                    System.out.println(playerArray[index].getName() + " rolled a " + (int) roleValue);
                    playerRolls[index] += (roleValue * 0.001);
                }
            } else {
                isDuplicates = false;
            }
        }


        //Print statement lets user know what the final order has been decided as
        System.out.println(" ");
        System.out.print("Order has been decided as the following: ");
        StringBuilder stringBuilder = new StringBuilder();
        //Loop through players in the playerArray
        for (Player player : playerArray) {
            stringBuilder.append(player.getName()).append(", "); //Adding commas between player names
        }
        String orderOutput = stringBuilder.toString();
        orderOutput = orderOutput.substring(0, orderOutput.length() - 2); //Remove extra comma at the end
        System.out.println(orderOutput);

        return playerArray;
    }


    /**
     * Rolls a die and returns a value between 1 and 6
     * @return an integer value between 1 and 6
     */
    private int flipDie() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    /**
     * Gives human player options to print the game board or to roll
     */
    private void playerCommands() {
        Scanner scanner = new Scanner(System.in);
        boolean isRolling = false; //Return to the play() function and continue executing turn once

        //Loop until player wants to roll
        while (!isRolling) {
            System.out.println("What would you like to do? (b for print board, any other key to roll): ");
            String userInput = scanner.next();
            if (userInput.toLowerCase().equals("b")) {
                printBoard();
            } else {
                isRolling = true; //Stop loop and return to play()
            }
        }
    }

    /**
     * Moves player along the board according to the integer value they rolled
     * @param currentPlayer the player whose position you would like to update
     * @param dieInt the value the player got when calling the flipDie() function
     */
    private void movePlayer(Player currentPlayer, int dieInt) {
        //Moves player on board
        int currentPosition = currentPlayer.getPosition();
        if (currentPosition + dieInt <= 100) {
            currentPlayer.setPosition(currentPosition + dieInt);
        } else {
            //When the roll and currentPosition summed goes over 100, move player to position 100, then back by how many extra numbers they had in their roll
            currentPlayer.setPosition(100 - (dieInt - (100 - currentPosition)));
        }

        //Update position if landed on snake or ladder
        currentPosition = currentPlayer.getPosition();
        int moveTo = BOARD[currentPosition];

        //If landed on a snake or a ladder
        if (moveTo != 0) {
            if (moveTo > currentPosition) {
                System.out.println("Woo-hoo! " + currentPlayer.getName() + " landed on a ladder. Moving to position " + moveTo);
            } else if (moveTo < currentPosition) {
                System.out.println("Oh no! " + currentPlayer.getName() + " landed on a snake. Moving to position " + moveTo);
            } else System.out.println("Moving to position " + moveTo);
            //Move player to position
            currentPlayer.setPosition(moveTo);
        }
    }

    /**
     * Prints board with snakes, ladders, and player positions
     */
    private void printBoard() {

        for (int i = 10; i > 0; i--) {
            //Counts in reverse for values of 100-91, 80-71, etc.
            if (i % 2 == 0) {
                for (int j = i * 10; j > (i * 10) - 10; j--) {
                    System.out.print(printBoardPiece(j) + " "); //Prints the proper board piece at the space j
                }
            } else { //Counting in the proper direction for values of 81-90, 61-70, etc.
                for (int j = (i * 10) - 9; j <= i * 10; j++) {
                    System.out.print(printBoardPiece(j) + " "); //Prints the proper board piece at the space j
                }
            }
            System.out.println(" "); //New line for every 10 spaces
        }

    }

    /**
     * Returns a string of the corresponding object at boardSpace
     * @param boardSpace integer value of the boardSpace
     * @return String of the corresponding object at boardSpace
     */
    private String printBoardPiece(int boardSpace) {

        //Checks for players on boardSpace
        for (Player player : playerOrder) {
            if ((player.getPosition() == boardSpace)) {
                if (player == humanPlayer) {
                    return "ME"; //Me
                }
                return "CM"; //Computer
            }
        }

        //If no player on boardSpace
        if (BOARD[boardSpace] == 0) {
            //nothing at boardSpace
            return "**";
        } else if (boardSpace > BOARD[boardSpace]) {
            //snake at boardSpace
            return "SN";
        } else {
            //ladder at boardSpace
            return "LR";
        }
    }

    /**
     * Prints a congratulations message to the player who reached position 100, reveals final board layout
     */
    private void printWinMessage() {
        //loop through players until position == 100, print congrats message
        for (Player player : playerOrder) {
            if (player.getPosition() == 100) {
                System.out.println("Congratulations " + player.getName() + ", you won!");
            }
        }

        //Print final positions on the board
        System.out.println("Final board positions: ");
        printBoard();
    }


}
