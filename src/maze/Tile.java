package maze;
import java.util.*;
import java.io.*;


public class Tile implements java.io.Serializable{

    public enum Type
    {
        CORRIDOR, ENTRANCE, EXIT, WALL;
    }

    private Type type;

    private Tile(Type type) {
        this.type = type;
    }

    protected static Tile fromChar(char c) {
        HashMap<Character, Type> types = new HashMap<Character, Type>();
        types.put('.', Type.CORRIDOR);
        types.put('e', Type.ENTRANCE);
        types.put('x', Type.EXIT);
        types.put('#', Type.WALL);
        return new Tile(types.get(c));
    }

    public Type getType() {
        return this.type;
    }

    public boolean isNavigable() {
        if(this.type == Type.WALL)
            return false;
        else
            return true;
    }

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
