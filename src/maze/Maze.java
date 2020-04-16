package maze;
import java.util.*;
import java.io.*;

/**
 * The Class Maze.
* @author Gurneet Bhatia
* @version 1.0
*/
public class Maze implements java.io.Serializable{

    /**
     * The Enum Direction.
     */
    public enum Direction
    {
        
        /** The north. */
        NORTH, 
        /** The south. */
        SOUTH, 
        /** The east. */
        EAST, 
        /** The west. */
        WEST;
    }

    /**
     * The Class Coordinate
     */
    public static class Coordinate {
        
        /** The x and the y. */
        private int x, y;

        /**
         * Instantiates a new coordinate.
         *
         * @param x the x
         * @param y the y
         */
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Gets the x.
         *
         * @return the x
         */
        public int getX() {
            return this.x;
        }

        /**
         * Gets the y.
         *
         * @return the y
         */
        public int getY() {
            return this.y;
        }

        /**
         * To string.
         *
         * @return the Coordinates as a string
         */
        public String toString() {
            return "("+this.x+", "+this.y+")";
        }
    }

    /** The entrance. */
    private Tile entrance;
    
    /** The exit. */
    private Tile exit;
    
    /** The tiles. */
    private List<List<Tile>> tiles;

    /**
     * Instantiates a new maze.
     */
    private Maze() {
        this.tiles = new ArrayList<List<Tile>>();
    }

    /**
     * From txt.
     *
     * @param filename the filename
     * @return the maze
     * @throws InvalidMazeException thrown when certain conditions are met
     */
    public static Maze fromTxt(String filename) throws InvalidMazeException {
        Maze maze = new Maze();
        try {
            FileReader fr = new FileReader(filename);
            int i;
            boolean mazeValid = true;
            int row = 0;
            maze.tiles.add(new ArrayList<Tile>());
            while((i=fr.read()) != -1) {
                char character = (char) i;
                if(!(character=='e' || character=='x' || character=='#' || character=='.' || character=='\n')) {
                    throw new InvalidMazeException("Maze file contains invalid character!");
                }
                else if(character == '\n') {
                    maze.tiles.add(new ArrayList<Tile>());
                } else {
                    Tile tile = Tile.fromChar(character);
                    maze.tiles.get(maze.tiles.size() - 1).add(tile);
                    if(character == 'e') {
                        try {
                            maze.setEntrance(tile);
                        } catch(MultipleEntranceException ex) {
                            throw new MultipleEntranceException("Multiple Entrances found in the Maze!");
                            //ex.printStackTrace();
                        }
                    }
                    if(character == 'x') {
                        try {
                            maze.setExit(tile);
                        } catch(MultipleExitException ex) {
                            throw new MultipleExitException("Multiple Exits found in the Maze!");
                            //ex.printStackTrace();
                        }
                    }
                }
            }
            /*if(currentRow.size() > 0)
            maze.tiles.add(currentRow);*/
        } catch(IOException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if(!(maze.tiles.stream().allMatch(l -> l.size() == maze.tiles.get(0).size()))) {
            throw new RaggedMazeException("Number of tiles in each row do not match! ");
        } else if(maze.getEntrance() == null) {
            throw new NoEntranceException("No Entrance found in Maze!");
        } else if(maze.getExit() == null) {
            throw new NoExitException("No Exit found in Maze!");
        }

        return maze;
    }

    /**
     * Gets the adjacent tile.
     *
     * @param tile the tile
     * @param direction the direction
     * @return the adjacent tile
     */
    public Tile getAdjacentTile(Tile tile, Direction direction) {
        Coordinate coords = getTileLocation(tile);
        Tile adjacentTile;
        switch(direction) {
            case NORTH:
            adjacentTile = coords.getY() < tiles.size() - 1? getTileAtLocation(new Coordinate(coords.getX(), coords.getY()+1)) : null;
            break;
            case SOUTH:
            adjacentTile = coords.getY() > 0? getTileAtLocation(new Coordinate(coords.getX(), coords.getY()-1)) : null;
            break;
            case EAST:
            adjacentTile = coords.getX() < tiles.get(0).size() - 1? getTileAtLocation(new Coordinate(coords.getX()+1, coords.getY())) : null;
            break;
            case WEST:
            adjacentTile = coords.getX() > 0? getTileAtLocation(new Coordinate(coords.getX()-1, coords.getY())): null;
            break;
            default:
            adjacentTile = null;
        }
        return adjacentTile;
    }

    /**
     * Gets the entrance.
     *
     * @return the entrance
     */
    public Tile getEntrance() {
        return this.entrance;
    }

    /**
     * Gets the exit.
     *
     * @return the exit
     */
    public Tile getExit() {
        return this.exit;
    }

    /**
     * Gets the tile at location.
     *
     * @param coord the coord
     * @return the tile at location
     */
    public Tile getTileAtLocation(Coordinate coord) {
        // (0, 0) is the bottom-left of the maze
        int numOfRows = tiles.size() - 1;
        int coordY = numOfRows - coord.getY();
        return tiles.get(coordY).get(coord.getX());
    }

    /**
     * Gets the tile location.
     *
     * @param tile the tile
     * @return the tile location
     */
    public Coordinate getTileLocation(Tile tile) {
        int row, col = 0;
        boolean found = false;
        for(row=0;row<tiles.size();row++) {
            for(col=0;col<tiles.get(0).size();col++) {
                try {
                    // if we don't have this try-catch block we will not be able to throw RaggedMazeExceptions
                    if(tiles.get(row).get(col).equals(tile)) {
                        found = true;
                        break;
                    }
                } catch(IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }

            }
            if(found)
                break;
        }

        int numOfRows = tiles.size() - 1;
        row = numOfRows - row;

        return found ? new Coordinate(col, row) : null;
    }

    /**
     * Gets the tiles.
     *
     * @return the tiles
     */
    public List<List<Tile>> getTiles() {
        return tiles;
    }

    /**
     * Sets the entrance.
     *
     * @param tile the new entrance
     * @throws MultipleEntranceException the multiple entrance exception
     * @throws IllegalAccessException the illegal access exception
     */
    private void setEntrance(Tile tile) throws MultipleEntranceException, IllegalAccessException {
        if(getTileLocation(tile) == null){
            throw new IllegalArgumentException("Illegal Access to Entrance Detected!");
        }
        else if(tile == null) {
            throw new IllegalArgumentException("Illegal Access to Entrance Detected!");
        }
        else if(this.getEntrance() == null) {
            this.entrance = tile;
        } else {
            throw new MultipleEntranceException("Multiple entrances found in Maze!");
        }
    }

    /**
     * Sets the exit.
     *
     * @param tile the new exit
     * @throws MultipleExitException the multiple exit exception
     * @throws IllegalAccessException the illegal access exception
     */
    private void setExit(Tile tile) throws MultipleExitException, IllegalAccessException {
        if(getTileLocation(tile) == null){
            throw new IllegalArgumentException("Illegal Access to Entrance Detected!");
        }
        else if(this.getExit() == null) {
            this.exit = tile;
        } else {
            throw new MultipleExitException("Multiple entrances found in Maze!");
        }
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
        String output = "";
        for(List<Tile> row: tiles) {
            for(Tile tile: row) {
                output += tile.toString();
            }
            output += "\n";
        }
        return output;
    }
}
