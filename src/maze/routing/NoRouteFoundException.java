package maze.routing;

/**
 * The Class NoRouteFoundException.
* @author Gurneet Bhatia
* @version 1.0
*/
public class NoRouteFoundException extends RuntimeException {
    
    /**
     * Instantiates a new no route found exception.
     *
     * @param errorMessage the error message
     */
    public NoRouteFoundException(String errorMessage) {
        super(errorMessage);
    }
    
}