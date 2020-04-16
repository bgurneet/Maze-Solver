package maze;
import java.util.*;
import java.io.*;


/**
 * The Class Tile.
 */
public class Tile implements java.io.Serializable{

    /**
     * The Enum Type.
     */
    public enum Type
    {
        
        /** The corridor. */
        CORRIDOR, 
        /** The entrance. */
        ENTRANCE, 
        /** The exit. */
        EXIT, 
        /** The wall. */
        WALL;
    }

    /** The type. */
    private Type type;

    /**
     * Instantiates a new tile.
     *
     * @param type the type
     */
    private Tile(Type type) {
        this.type = type;
    }

    /**
     * From char.
     *
     * @param c the c
     * @return the tile
     */
    protected static Tile fromChar(char c) {
        HashMap<Character, Type> types = new HashMap<Character, Type>();
        types.put('.', Type.CORRIDOR);
        types.put('e', Type.ENTRANCE);
        types.put('x', Type.EXIT);
        types.put('#', Type.WALL);
        return new Tile(types.get(c));
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Checks if is navigable.
     *
     * @return true, if is navigable
     */
    public boolean isNavigable() {
        if(this.type == Type.WALL)
            return false;
        else
            return true;
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
        String output = "";

        switch(this.type) {
            case CORRIDOR:
            output = ".";
            break;
            case ENTRANCE:
            output = "e";
            break;
            case EXIT:
            output = "x";
            break;
            case WALL:
            output = "#";
            break;
            default:
            output = "";
        }
        return output;
    }

}
