package maze;
import java.util.*;
import java.io.*;

public class Maze {
    
    private Tile entrance;
    private Tile exit;
    private List<List<Tile>> tiles;
    
    public Maze() {
        this.tiles = new ArrayList<List<Tile>>();
    }
    
    public static Maze fromTxt(String filename) {
        Maze maze = new Maze();
        try {
            FileReader fr = new FileReader("maze-test.txt");
            int i;
            List<Tile> currentRow = new ArrayList<Tile>();
            int row = 0;
            int column = 0;
            while((i=fr.read()) != -1) {
                char character = (char) i;
                if(character == '\n') {
                    maze.tiles.add(currentRow);
                    currentRow = new ArrayList<Tile>();
                    row = 0;
                    column += 1;
                } else {
                    Tile tile = Tile.fromChar(character, row, column);
                    if(character == 'e')
                        maze.setEntrance(tile);
                    if(character == 'x')
                        maze.setExit(tile);
                    currentRow.add(tile);
                    row += 1;
                }
            }
            if(currentRow.size() > 0)
                maze.tiles.add(currentRow);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return maze;
    }
    
    public Tile getAdjacentTile(Tile tile, Direction direction) {
        Coordinate coords = tile.getCoords();
        Tile adjacentTile;
        switch(direction) {
            case NORTH:
                adjacentTile = coords.getY() > 0? tiles.get(coords.getY() - 1).get(coords.getX()) : null;
                break;
            case SOUTH:
                adjacentTile = coords.getY() < tiles.size() - 1? tiles.get(coords.getY() + 1).get(coords.getX()) : null;
                break;
            case EAST:
                adjacentTile = coords.getX() < tiles.get(0).size() - 1? tiles.get(coords.getY()).get(coords.getX() + 1) : null;
                break;
            case WEST:
                adjacentTile = coords.getX() > 0? tiles.get(coords.getY()).get(coords.getX() - 1) : null;
                break;
            default:
                adjacentTile = null;
        }
        return adjacentTile;
    }
    
    public Tile getEntrance() {
        return this.entrance;
    }
    
    public Tile getExit() {
        return this.exit;
    }
    
    public Tile getTileAtLocation(Coordinate coord) {
        return tiles.get(coord.getY()).get(coord.getX()); // check later if this is correct or not
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
        String output = "";
        for(List<Tile> row: tiles) {
            for(Tile tile: row) {
                output += tile.toString() + " ";
            }
            output += "\n";
        }
        return output;
    }
}