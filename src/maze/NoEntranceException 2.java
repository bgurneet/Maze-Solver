package maze;

public class NoEntranceException extends InvalidMazeException {
    public NoEntranceException(String errorMessage) {
        super(errorMessage);
    }
}