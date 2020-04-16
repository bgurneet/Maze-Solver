package maze;

/**
 * The Class NoExitException.
 */
public class NoExitException extends InvalidMazeException {
    
    /**
     * Instantiates a new no exit exception.
     *
     * @param errorMessage the error message
     */
    public NoExitException(String errorMessage) {
        super(errorMessage);
    }
}
