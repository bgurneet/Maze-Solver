import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import javafx.scene.*;
import javafx.fxml.FXML;
import maze.*;
import java.io.*;
import maze.routing.*;
import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.*;

public class MazeApplication extends Application
{
    @FXML
    Button StepButton;

    @FXML
    Pane canvas;

    @FXML
    MenuItem LoadMapMI;

    public List<List<Rectangle>> blocks;
    public RouteFinder rf;
    public Maze maze;

    @Override
    public void start(Stage stage) throws Exception
    {

        Parent root = FXMLLoader.load(getClass().getResource("BasicApplication_css.fxml"));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    public void LoadMapMIClicked() {
        Stage stage = (Stage) StepButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Open Maze Path");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            LoadMap(file.getAbsolutePath());
        }
    }

    public void LoadMap(String filename) {
        try {
            canvas.getChildren().clear();
            blocks = new ArrayList<List<Rectangle>>();
            maze = Maze.fromTxt(filename);
            rf = new RouteFinder(maze);
            String[] mazeStr = maze.toString().split("\n");
            int BlockHeight = 25;
            int BlockWidth = 50;
            for(int row=0;row<mazeStr.length;row++) {
                List<Rectangle> currentRow = new ArrayList<Rectangle>();
                for(int col=0;col<mazeStr[row].length();col++) {
                    Color colour = getColour(mazeStr[row].charAt(col));
                    Rectangle rectangle = new Rectangle(BlockWidth, BlockHeight,colour);
                    rectangle.relocate(col*BlockWidth, row*BlockHeight);
                    currentRow.add(rectangle);
                    canvas.getChildren().addAll(rectangle);
                }
                System.out.println();
                blocks.add(currentRow);
            }
        } catch(InvalidMazeException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getClass().getSimpleName() + " occured!");
            alert.setContentText(ex.getMessage());
            
            alert.showAndWait();
        }
    }

    public Color getColour(char c) {
        HashMap<Character, Color> colours = new HashMap<Character, Color>();
        colours.put('.', Color.SNOW);
        colours.put('e', Color.GREEN);
        colours.put('x', Color.RED);
        colours.put('#', Color.PERU);

        return colours.get(c);
    }

    public void StepButtonPressed() {
        int BlockHeight = 25;
        int BlockWidth = 50;
        String[] mazeStr = maze.toString().split("\n");
        try {
            if(rf != null && !rf.step()) {
                List<Tile> route = rf.getRoute();
                for(List<Tile> row: maze.getTiles()) {
                    for(Tile tile: row) {
                        Coordinate coords = maze.getTileLocation(tile);
                        Rectangle block = blocks.get(coords.getY()).get(coords.getX()); 
                        if(route.contains(tile)) {
                            if(block.getFill() != Color.DARKTURQUOISE) {
                                block.setFill(Color.DARKTURQUOISE);
                            }
                        } else if(block.getFill() == Color.DARKTURQUOISE){
                            block.setFill(getColour(tile.toString().charAt(0)));
                        }
                    }
                }
            }
        } catch(NoRouteFoundException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getClass().getSimpleName() + " occured!");
            alert.setContentText(ex.getMessage());
            
            alert.showAndWait();
        }
    }

}
