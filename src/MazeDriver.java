


import maze.*;
import java.io.*;
import maze.routing.*;
import java.util.*;

/**
 * The Class MazeDriver.
* @author Gurneet Bhatia
* @version 1.0
*/
public class MazeDriver {
    /*public static void main(String [] args) {
        
        Maze maze = Maze.fromTxt("../maze-test.txt");
        System.out.println(maze.toString());
        //System.out.println(maze.getAdjacentTile(maze.getTiles().get(1).get(0), Direction.NORTH).toString());
        
        RouteFinder rf = new RouteFinder(maze);
        String outChars = "";
        List<Tile> route = rf.getRoute();
        System.out.print(maze.getTileLocation(route.get(route.size() - 1)).toString());
        outChars += route.get(route.size() - 1).toString();
        while(!rf.step()) {
            route = rf.getRoute();
            System.out.print(maze.getTileLocation(route.get(route.size() - 1)).toString());
            outChars += route.get(route.size() - 1).toString();
        }
        System.out.print(maze.getTileLocation(route.get(route.size() - 1)).toString());
        outChars += route.get(route.size() - 1).toString();
        System.out.println("\n"+outChars);
        
    }*/
}