/**
 * Board class that stores static method which returns a snakes and ladders board
 */
public class Board {

    /**
     * Static method that returns a board to use for snakes and ladders. Stores mapping from beginning of snake/ladder to end of snake/ladder.
     * @return integer array that represents snakes and ladders board
     */
    public static int[] getBoard() {

        //Stores mapping from beginning of snake/ladder to end of snake/ladder
        int[] board = new int[101];
        board[1] = 38;
        board[4] = 14;
        board[9] = 31;
        board[16] = 6;
        board[21] = 42;
        board[28] = 84;
        board[36] = 44;
        board[48] = 30;
        board[51] = 67;
        board[64] = 60;
        board[71] = 91;
        board[79] = 19;
        board[80] = 100;
        board[93] = 68;
        board[95] = 24;
        board[97] = 76;
        board[98] = 78;

        return board;
    }

}
