package maze.routing;
import maze.*;
import java.util.*;

public class RouteFinder {
    
    private Maze maze;
    private Stack<Tile> route;
    private List<Tile> traversedTiles;
    private boolean finished;
    
    public RouteFinder(Maze maze) {
        this.maze = maze;
        Tile entrance = this.maze.getEntrance();
        System.out.println("Here" + entrance);
        this.route = new Stack<Tile>();
        this.route.push(entrance);
        this.traversedTiles = new ArrayList<Tile>();
        this.traversedTiles.add(entrance);
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
        Tile currentTile = route.peek();
        Tile nextTile;
        
        Tile northTile = maze.getAdjacentTile(currentTile, Direction.NORTH);
        Tile southTile = maze.getAdjacentTile(currentTile, Direction.SOUTH);
        Tile westTile = maze.getAdjacentTile(currentTile, Direction.WEST);
        Tile eastTile = maze.getAdjacentTile(currentTile, Direction.EAST);
        
        if(northTile != null && northTile.isNavigable() && !traversedTiles.contains(northTile))
            nextTile = northTile;
        else if(westTile != null && westTile.isNavigable() && !traversedTiles.contains(westTile))
            nextTile = westTile;
        else if(eastTile != null && eastTile.isNavigable() && !traversedTiles.contains(eastTile))
            nextTile = eastTile;
        else if(southTile != null && southTile.isNavigable() && !traversedTiles.contains(southTile))
            nextTile = southTile;
        else
            nextTile = null;
        
        if(nextTile != null) {
            traversedTiles.add(nextTile);
            route.push(nextTile);
        } else {
            route.pop();
        }
        
        return nextTile == null? false : nextTile.toString().equals("x");
    }
    
    public String toString() {
        return ""; // change this
    }
    
}