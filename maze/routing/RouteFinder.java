package maze.routing;
import maze.*;
import java.util.*;

public class RouteFinder {
    
    private Maze maze;
    private Stack<Tile> route;
    private boolean finished;
    
    public RouteFinder(Maze maze) {
        this.maze = maze;
    }
    
    public Maze getMaze() {
        return maze;
    }
    
    public List<Tile> getRoute() {
        return route;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    public static RouteFinder load(String filename) {
        return null; // change this
    }
    
    public void save(String filename) {
    }
    
    public boolean step() {
        return true; // change this
    }
    
    public String toString() {
        return ""; // change this
    }
    
}