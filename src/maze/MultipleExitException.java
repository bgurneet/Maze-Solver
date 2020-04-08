package maze;

public class MultipleExitException extends InvalidMazeException {
    public MultipleExitException(String errorMessage) {
        super(errorMessage);
    }
}
