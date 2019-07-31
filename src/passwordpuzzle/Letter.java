package passwordpuzzle;

/**
 * Represents a letter when finding the correct password.
 * @author Dominic
 */
public class Letter {
    private char character;
    private int position;
    
    public Letter(char c, int position){
        this.character = c;
        this.position = position;
    }

    public char getCharacter() {
        return character;
    }

    public int getPosition() {
        return position;
    }
    
    
}
