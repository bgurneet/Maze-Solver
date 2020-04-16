package maze;

/**
 * The Class InvalidMazeException is the parent class of most of the exceptions I use in this project
* @author Gurneet Bhatia
* @version 1.0
*/
public class InvalidMazeException extends Exception
{
    
    /**
     * Instantiates a new invalid maze exception.
     *
     * @param errorMessage the error message
     */
    public InvalidMazeException(String errorMessage)
    {
        super(errorMessage);
    }
}
