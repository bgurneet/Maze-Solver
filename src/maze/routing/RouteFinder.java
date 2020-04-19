package maze.routing;

import maze.*;
import java.util.*;
import java.io.*;

/**
 * The Class RouteFinder.
* @author Gurneet Bhatia
* @version 1.0
*/
public class RouteFinder implements java.io.Serializable{

    /** The maze. */
    private Maze maze;
    
    /** The route. */
    private Stack<Tile> route;
    
    /** The best route. */
    private Queue<Tile> bestRoute;
    
    /** The traversed tiles. */
    private List<Tile> traversedTiles;
    
    /** The finished. */
    private boolean finished;

    /** The variable that denotes which maze solving algorithm is being used (true is depth first and false is breadth first). */
    private boolean algorithmInUse = false;

    /**
     * Instantiates a new route finder.
     *
     * @param maze the maze
     */
    public RouteFinder(Maze maze) {
        this.maze = maze;
        //Tile entrance = this.maze.getEntrance();
        this.route = new Stack<Tile>();
        this.bestRoute = new LinkedList<Tile>();
        //this.route.push(entrance);
        this.traversedTiles = new ArrayList<Tile>();
        //this.traversedTiles.add(entrance);
        this.finished = false;
    }

    /**
     * sets the algorithm variable.
     *
     * @return void
     */
    public void setAlgorithm(boolean b) {
        this.algorithmInUse = b;
    }

    /**
     * Gets the algorithm variable.
     *
     * @return the algorithmInUse
     */
    public boolean getAlgorithm() {
        return this.algorithmInUse;
    }

    /**
     * Gets the maze.
     *
     * @return the maze
     */
    public Maze getMaze() {
        return this.maze;
    }

    /**
     * Gets the route.
     *
     * @return the route
     */
    public List<Tile> getRoute() {
        return this.route;
    }

    /**
     * Checks if is finished.
     *
     * @return true, if is finished
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @return the route finder
     */
    public static RouteFinder load(String filename) throws IOException, ClassNotFoundException{
        RouteFinder rf = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            rf = (RouteFinder) objIn.readObject();
        } catch(IOException ex){
            throw ex;
        } catch(ClassNotFoundException ex) {
            throw ex;
        }
        return rf;
    }

    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
    
    /**
     * Gets the traversed.
     *
     * @return the traversed
     */
    public List<Tile> getTraversedTiles() {
        //return this.bestRoute;
        return this.traversedTiles;
    }
    
    /**
     * Best route step.
     *
     * @return true, if successful
     */
    public boolean bestRouteStep() {
        if(!this.finished) {
            if(!(this.traversedTiles.contains(this.maze.getEntrance()))) {
                this.bestRoute.add(this.maze.getEntrance());
                this.traversedTiles.add(this.maze.getEntrance());
            } else {
                if(this.bestRoute.size() > 0) {
                    int count = this.bestRoute.size();
                    Queue <Tile> newTiles = new LinkedList<Tile>();
                    while(count > 0) {
                        count -= 1;
                        Tile currentTile = this.bestRoute.remove();
                        if(currentTile == this.maze.getExit()) {
                            this.finished = true;
                            break;
                        } else {
                            Tile northTile = maze.getAdjacentTile(currentTile, Maze.Direction.NORTH);
                            Tile southTile = maze.getAdjacentTile(currentTile, Maze.Direction.SOUTH);
                            Tile westTile = maze.getAdjacentTile(currentTile, Maze.Direction.WEST);
                            Tile eastTile = maze.getAdjacentTile(currentTile, Maze.Direction.EAST);
                            Tile [] neighbours = new Tile[] {northTile, southTile, westTile, eastTile};

                            for(Tile neighbour: neighbours) {
                                if(neighbour!=null && neighbour.isNavigable() && !traversedTiles.contains(neighbour)) {
                                    newTiles.add(neighbour);
                                    this.traversedTiles.add(neighbour);
                                }
                            }
                            traversedTiles.add(currentTile);
                        }
                    }
                    bestRoute.addAll(newTiles);
                } else {
                    throw new NoRouteFoundException("No route found in this maze!");
                }
            }
        }
        return this.finished;
    }

    /**
     * Step.
     *
     * @return true, if successful
     */
    public boolean step() {
        if(!this.finished) {
            if(!(this.route.contains(this.maze.getEntrance()) || this.traversedTiles.contains(this.maze.getEntrance()))) {
                this.route.push(this.maze.getEntrance());
                this.traversedTiles.add(this.maze.getEntrance());
            } else {
                if(route.size() > 0) {
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
                } else {
                    throw new NoRouteFoundException("No route found in this maze!");
                }
            }
        } else if(!traversedTiles.contains(this.maze.getExit())) {
            traversedTiles.add(this.maze.getExit());
        }
        return this.finished;
    }

    /**
     * To string.
     *
     * @return the string
     */
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