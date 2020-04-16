package maze;

/**
 * The Class MultipleEntranceException.
* @author Gurneet Bhatia
* @version 1.0
*/
public class MultipleEntranceException extends InvalidMazeException {
    
    /**
     * Instantiates a new multiple entrance exception.
     *
     * @param errorMessage the error message
     */
    public MultipleEntranceException(String errorMessage) {
        super(errorMessage);
    }
}
