package maze;

public class InvalidMazeException extends Exception
{
    public InvalidMazeException(String errorMessage)
    {
        super(errorMessage);
    }
}
