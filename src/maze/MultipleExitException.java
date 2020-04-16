package maze;

/**
 * The Class MultipleExitException.
* @author Gurneet Bhatia
* @version 1.0
*/
public class MultipleExitException extends InvalidMazeException {
    
    /**
     * Instantiates a new multiple exit exception.
     *
     * @param errorMessage the error message
     */
    public MultipleExitException(String errorMessage) {
        super(errorMessage);
    }
}
