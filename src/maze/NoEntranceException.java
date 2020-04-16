package maze;

/**
 * The Class NoEntranceException.
 * @author Gurneet Bhatia
 * @version 1.0
 */
public class NoEntranceException extends InvalidMazeException {
    
    /**
     * Instantiates a new no entrance exception.
     *
     * @param errorMessage the error message
     */
    public NoEntranceException(String errorMessage) {
        super(errorMessage);
    }
}
