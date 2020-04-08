package maze;

public class MultipleEntranceException extends InvalidMazeException {
    public MultipleEntranceException(String errorMessage) {
        super(errorMessage);
    }
}