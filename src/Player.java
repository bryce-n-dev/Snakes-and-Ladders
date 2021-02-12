import java.util.Objects;

/**
 * Represents a player to be used in the snakes and ladders game
 */
public class Player {

    //Fields
    int position = 0;
    String name;

    //Constructor

    /**
     * Creates a player with a given name
     * @param name Name of the player
     */
    public Player(String name) {
        this.name = name;
    }


    //Setters and getters

    /**
     * Sets player's position on the board
     * @param position integer representing the board space the player is on
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets player's position on the board
     * @return int value of player's position on the board
     */
    public int getPosition() {
        return position;
    }

    /**
     * Gets player's name
     * @return String value of player's name
     */
    public String getName() {
        return name;
    }


    /**
     * Checks to see if player objects equal one another
     * @param o Any object
     * @return boolean value of if the player objects are equal to one another
     */
    @Override //Autogenerated from IntelliJ IDE
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return position == player.position &&
                Objects.equals(name, player.name);
    }

}
