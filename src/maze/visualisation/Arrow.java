package maze.visualisation;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

public class Arrow extends Group {
    
    private Line arrowBody;
    private Line arrowTip1;
    private Line arrowTip2;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double angle;
    private int bodyLength;
    private int tipLength;
    
    public Arrow(double startX, double startY, double angle, int bodyLength, int tipLength) {
        // startX and startY denote the point at which the tip of the arrow lies
        this.startX = startX;
        this.startY = startY;
        this.angle = Math.toRadians(angle);
        this.bodyLength = bodyLength;
        this.tipLength = tipLength;
        
        setArrowBody();
        setArrowTips();
 
        this.getChildren().addAll(arrowBody, arrowTip1, arrowTip2);
    }
    
    public void setArrowBody() {
        endX = startX + bodyLength + Math.sin(this.angle);
        endY = startY + bodyLength + Math.cos(this.angle);
        arrowBody = new Line(startX, startY, endX, endY);
        arrowBody.setStrokeWidth(5);
        arrowBody.setStroke(Color.LIGHTGRAY);
    }
    
    public double getEndX() {
        return this.endX;
    }
    
    public double getEndY() {
        return this.endY;
    }
    
    public void setArrowTips() {
        double endX = startX + tipLength * Math.sin(this.angle + Math.toRadians(35));
        double endY = startY + tipLength * Math.cos(this.angle + Math.toRadians(35));
        arrowTip1 = new Line(startX, startY, endX, endY);
        arrowTip1.setStrokeWidth(5);
        arrowTip1.setStroke(Color.LIGHTGRAY);
        
        endX = startX + tipLength * Math.cos(this.angle + Math.toRadians(35));
        endY = startY + tipLength * Math.sin(this.angle + Math.toRadians(35));
        arrowTip2 = new Line(startX, startY, endX, endY);
        arrowTip2.setStrokeWidth(5);
        arrowTip2.setStroke(Color.LIGHTGRAY);
    }
    
}