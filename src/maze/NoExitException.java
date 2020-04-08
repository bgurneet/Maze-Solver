package maze;

public class NoExitException extends InvalidMazeException {
    public NoExitException(String errorMessage) {
        super(errorMessage);
    }
}
