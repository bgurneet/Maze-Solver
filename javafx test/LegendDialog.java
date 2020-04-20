import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.*;

public class LegendDialog extends Dialog
{
    public LegendDialog() {
        this.setTitle("Help");
        this.setHeaderText("Maze Legend");
        Label img = new Label();
        img.getStyleClass().addAll("alert", "information", "dialog-pane");
        this.setGraphic(img);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //grid.add(new Rectangle(50, 50, Color.BLACK), 0, 0);
        Map<String, Color> colours = new HashMap<String, Color>();
        colours.put("Corridor", Color.SNOW);
        colours.put("Wall", Color.PERU);
        colours.put("Part of Route", Color.DARKTURQUOISE);
        colours.put("Visited but discarded from route", Color.AQUAMARINE);
        colours.put("Entrance", Color.GREEN);
        colours.put("Exit", Color.PERU);
        int counter = 0;
        for(String key: colours.keySet()) {
            Color value = colours.get(key);
            Rectangle rectangle = new Rectangle(75, 75, value);
            rectangle.setArcWidth(20);
            rectangle.setArcHeight(20);
            Label label = new Label(key);
            label.setStyle("-fx-font-size: 20px");
            grid.add(rectangle, 0, counter);
            grid.add(label, 2, counter);
            counter += 1;
        }
        
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
    }
}
