package maze;
import java.util.*;

public class Maze {
    
    private Tile entrance;
    private Tile exit;
    private List<List<Tile>> tiles;
    
    private Maze() {
    }
    
    public void fromTxt(String s) {
    }
    
    public Tile getAdjacentTile(Tile tile, Direction direction) {
        return null; // change this
    }
    
    public Tile getEntrance() {
        return this.entrance;
    }
    
    public Tile getExit() {
        return this.exit;
    }
    
    public Tile getTileAtLocation(Coordinate coord) {
        return null; // change this
    }
    
    public Coordinate getTileLocation(Tile tile) {
        return null; // change this
    }
    
    public List<List<Tile>> getTiles() {
        return tiles;
    }
    
    private void setEntrance(Tile tile) {
        this.entrance = tile;
    }
    
    private void setExit(Tile tile) {
        this.exit = tile;
    }
    
    public String toString() {
        return ""; // change this
    }
}