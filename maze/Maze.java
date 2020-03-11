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
            while((i=fr.read()) != -1) {
                char character = (char) i;
                if(character == '\n') {
                    maze.tiles.add(currentRow);
                    currentRow = new ArrayList<Tile>();
                } else {
                    Tile tile = Tile.fromChar(character);
                    if(character == 'e')
                        maze.setEntrance(tile);
                    if(character == 'x')
                        maze.setExit(tile);
                    currentRow.add(tile);
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
        Coordinate coords = getTileLocation(tile);
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
        int row, col = 0;
        boolean found = false;
        for(row=0;row<tiles.size();row++) {
            for(col=0;col<tiles.get(0).size();col++) {
                if(tiles.get(row).get(col).equals(tile)) {
                    found = true;
                    break;
                }
            }
            if(found)
                break;
        }
        
        return new Coordinate(col, row);
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