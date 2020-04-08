package maze.routing;

public class NoRouteFoundException extends Exception {
    
    public NoRouteFoundException(String errorMessage) {
        super(errorMessage);
    }
    
}