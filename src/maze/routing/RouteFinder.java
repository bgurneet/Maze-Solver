package maze.routing;

import maze.*;
import java.util.*;
import java.io.*;

public class RouteFinder implements java.io.Serializable{

    private Maze maze;
    private Stack<Tile> route;
    private List<Tile> traversedTiles;
    private boolean finished;

    public RouteFinder(Maze maze) {
        this.maze = maze;
        Tile entrance = this.maze.getEntrance();
        this.route = new Stack<Tile>();
        this.route.push(entrance);
        this.traversedTiles = new ArrayList<Tile>();
        this.traversedTiles.add(entrance);
        this.finished = false;
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
        RouteFinder rf = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            rf = (RouteFinder) objIn.readObject();
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
            System.out.println("Saving: "+this.maze.toString());
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch(IOException ex) {
            throw new IOException(ex);
        }
    }

    public boolean step() /*throws NoRouteFoundException*/{
        if(!this.finished) {
            /*if(route.size() > 0) {*/
            Tile currentTile = route.peek();
            Tile nextTile;

            Tile northTile = maze.getAdjacentTile(currentTile, Maze.Direction.NORTH);
            Tile southTile = maze.getAdjacentTile(currentTile, Maze.Direction.SOUTH);
            Tile westTile = maze.getAdjacentTile(currentTile, Maze.Direction.WEST);
            Tile eastTile = maze.getAdjacentTile(currentTile, Maze.Direction.EAST);

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

            this.finished = nextTile == null? false : nextTile.toString().equals("x");
            /*} else {
            throw new NoRouteFoundException("No route found in this maze!");
            }*/
        } else if(!traversedTiles.contains(this.maze.getExit())) {
            traversedTiles.add(this.maze.getExit());
        }
        return this.finished;
    }

    public String toString() {
        return ""; // change this
    }

}