package maze.routing;

import java.util.*;
import java.io.*;
import maze.*;

public class BestRouteFinder {
    
    private Queue<Tile> route;
    private Maze maze;
    private boolean finished;
    private List<Tile> traversedTiles;
    
    public BestRouteFinder(Maze maze) {
        this.maze = maze;
        this.route = new LinkedList<Tile>();
        this.traversedTiles = new ArrayList<Tile>();
        this.finished = false;
    }
    
    public Maze getMaze() {
        return maze;
    }

    public Queue<Tile> getRoute() {
        return route;
    }

    public boolean isFinished() {
        return finished;
    }

    public static BestRouteFinder load(String filename) {
        BestRouteFinder rf = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            rf = (BestRouteFinder) objIn.readObject();
        } catch(IOException ex){
            ex.printStackTrace();
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return rf;
    }

    public void save(String filename) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch(IOException ex) {
            throw new IOException(ex);
        }
    }
    
    public String toString() {
        String output = "";
        List<List<Tile>> mazeTiles = maze.getTiles();
        for(List<Tile> row: mazeTiles) {
            for(Tile tile: row) {
                output += route.contains(tile) ? "*" : tile.toString();
            }
            output += "\n";
        }
        return output;
    }
}