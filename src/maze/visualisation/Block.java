package maze.visualisation;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Block extends Rectangle {
    public Block(int width, int height, Color colour, int arcWidth, int arcHeight, int posx, int posy) {
        this.setWidth(width);
        this.setHeight(height);
        this.setFill(colour);
        this.setArcWidth(arcWidth);
        this.setArcHeight(arcHeight);
        this.setX(posx);
        this.setY(posy);
    }
}