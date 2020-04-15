import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.io.*;
import maze.*;
import maze.routing.*;

public class MazeApplication1 extends Application{
    Stage stage = null;
    Pane canvas;

    public List<List<Rectangle>> blocks;
    public RouteFinder rf;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Maze Application");

        primaryStage.setScene(getHomeScene());
        primaryStage.show();

    }

    public MenuBar getMenuBar(boolean disableControls) {
        MenuBar menuBar = new MenuBar();

        Menu actionsMenu = new Menu("Maze Solvers");

        MenuItem depth = new MenuItem("Depth First Algorithm");
        depth.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    depthFirstClicked();
                }
            });
        MenuItem breadth = new MenuItem("Breadth First Algorithm");
        breadth.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    breadthFirstClicked();
                }
            });
        actionsMenu.getItems().add(breadth);
        actionsMenu.getItems().add(depth);

        Menu controlsMenu = new Menu("Controls");
        MenuItem loadMap = new MenuItem("Load Map");
        loadMap.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LoadMapMIClicked();
                }
            });
        MenuItem loadRoute = new MenuItem("Load Route");
        loadRoute.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LoadRouteMIClicked();
                }
            });
        MenuItem saveRoute = new MenuItem("Save Route");
        controlsMenu.getItems().add(loadMap);
        controlsMenu.getItems().add(loadRoute);
        controlsMenu.getItems().add(saveRoute);
        if(disableControls) controlsMenu.setDisable(true);

        Menu helperMenu = new Menu("Help");
        MenuItem legend = new MenuItem("Legend");
        legend.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    legendClicked();
                }
            });
        MenuItem instructions = new MenuItem("Instructions");
        instructions.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    instructionsClicked();
                }
            });
        helperMenu.getItems().add(legend);
        helperMenu.getItems().add(instructions);

        menuBar.getMenus().add(actionsMenu);
        menuBar.getMenus().add(controlsMenu);
        menuBar.getMenus().add(helperMenu);
        return menuBar;
    }

    public Scene getHomeScene() {
        Label startInstructionLabel = new Label("Please choose one of the Maze Solvers to start");
        startInstructionLabel.setId("homeSceneLabel");
        startInstructionLabel.setTextFill(Color.WHITE);
        startInstructionLabel.setTranslateY(-50);
        startInstructionLabel.setMaxWidth(Double.MAX_VALUE);
        startInstructionLabel.setAlignment(Pos.CENTER);

        Button breadthFirstBtn = new Button("Breadth First Search");
        breadthFirstBtn.setId("homeSceneBtn");
        breadthFirstBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    breadthFirstClicked();
                }
            });
        Button depthFirstBtn = new Button("Depth First Search");
        depthFirstBtn.setId("homeSceneBtn");
        depthFirstBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    depthFirstClicked();
                }
            });
        depthFirstBtn.setTranslateY(25);

        MenuBar menuBar = getMenuBar(true);

        VBox vbox = new VBox(startInstructionLabel, breadthFirstBtn, depthFirstBtn);
        vbox.setId("vbox");
        vbox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox);

        Scene scene = new Scene(borderPane, 600, 500);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());

        return scene;
    }

    public Scene getDepthFirstScene() {
        MenuBar menuBar = getMenuBar(false);

        Button stepBtn = new Button("Step");
        stepBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DepthFirstStepButtonPressed();
                }
            });
        //stepBtn.setId("stepBtn");

        canvas = new Pane();
        canvas.setId("canvas");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("vbox");
        anchorPane.setTopAnchor(stepBtn, 10.0);
        anchorPane.setLeftAnchor(stepBtn, 100.0);
        anchorPane.setRightAnchor(stepBtn, 100.0);
        anchorPane.setTopAnchor(canvas, 50.0);
        anchorPane.setRightAnchor(canvas, 0.0);
        anchorPane.setLeftAnchor(canvas, 0.0);
        anchorPane.setBottomAnchor(canvas, 0.0);
        anchorPane.getChildren().addAll(stepBtn, canvas);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(anchorPane);

        Scene scene = new Scene(borderPane, 600, 500);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());

        return scene;
    }

    public Scene getBreadthFirstScene() {
        MenuBar menuBar = getMenuBar(false);

        Button stepBtn = new Button("Step");
        stepBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    BreadthFirstStepButtonPressed();
                }
            });
        //stepBtn.setId("stepBtn");

        canvas = new Pane();
        canvas.setId("canvas");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("vbox");
        anchorPane.setTopAnchor(stepBtn, 10.0);
        anchorPane.setLeftAnchor(stepBtn, 100.0);
        anchorPane.setRightAnchor(stepBtn, 100.0);
        anchorPane.setTopAnchor(canvas, 50.0);
        anchorPane.setRightAnchor(canvas, 0.0);
        anchorPane.setLeftAnchor(canvas, 0.0);
        anchorPane.setBottomAnchor(canvas, 0.0);
        anchorPane.getChildren().addAll(stepBtn, canvas);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(anchorPane);

        Scene scene = new Scene(borderPane, 600, 500);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());

        return scene;
    }

    public void depthFirstClicked() {
        System.out.println("Depth First Clicked!");
        //re-initialise the variables that are common
        blocks = null;
        rf = null;

        stage.setScene(getDepthFirstScene());
    }

    public void breadthFirstClicked() {
        System.out.println("Breadth First Clicked!");
        //re-initialise the variables that are common
        blocks = null;
        rf = null;
        stage.setScene(getBreadthFirstScene());
    }

    public void legendClicked() {
        System.out.println("Legend Clicked!");
    }

    public void instructionsClicked() {
        System.out.println("Instructions Clicked!");
    }

    public void LoadMapMIClicked() {
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("TXT file (*.txt)", "*.txt");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(ext);
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Open Maze Path");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            LoadMap(file.getAbsolutePath());
        }
    }

    public void LoadMap(String filename) {
        try {
            Maze maze = Maze.fromTxt(filename);
            rf = new RouteFinder(maze);
            renderMaze();
        } catch(InvalidMazeException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getClass().getSimpleName() + " occured!");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    public void renderMaze() {
        blocks = new ArrayList<List<Rectangle>>();
        canvas.getChildren().clear();
        Maze maze = rf.getMaze();
        String[] mazeStr = maze.toString().split("\n");
        int BlockHeight = 50;
        int BlockWidth = 50;
        for(int row=0;row<mazeStr.length;row++) {
            List<Rectangle> currentRow = new ArrayList<Rectangle>();
            for(int col=0;col<mazeStr[row].length();col++) {
                Color colour = getColour(mazeStr[row].charAt(col));
                Rectangle rectangle = new Rectangle(BlockWidth, BlockHeight,colour);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                rectangle.relocate(col*BlockWidth, row*BlockHeight);
                currentRow.add(rectangle);
                canvas.getChildren().addAll(rectangle);
            }
            blocks.add(currentRow);
        }
        List<Tile> route = rf.getRoute();
        for(List<Tile> row: maze.getTiles()) {
            for(Tile tile: row) {
                Maze.Coordinate coords = maze.getTileLocation(tile);
                int numOfRows = maze.getTiles().size() - 1;
                int coordY = numOfRows - coords.getY();
                Rectangle block = blocks.get(coordY).get(coords.getX());
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

    public Color getColour(char c) {
        HashMap<Character, Color> colours = new HashMap<Character, Color>();
        colours.put('.', Color.SNOW);
        colours.put('e', Color.GREEN);
        colours.put('x', Color.RED);
        colours.put('#', Color.PERU);

        return colours.get(c);
    }

    public void DepthFirstStepButtonPressed() {
        if(rf != null) {
            Maze maze = rf.getMaze();
            int BlockHeight = 25;
            int BlockWidth = 50;
            String[] mazeStr = maze.toString().split("\n");
            try {
                //finished
                if(rf != null && !rf.step()) {
                    List<Tile> route = rf.getRoute();
                    for(List<Tile> row: maze.getTiles()) {
                        for(Tile tile: row) {
                            Maze.Coordinate coords = maze.getTileLocation(tile);
                            int numOfRows = maze.getTiles().size() - 1;
                            int coordY = (coords.getY() - numOfRows) * -1;
                            Rectangle block = blocks.get(coordY).get(coords.getX()); 
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
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Maze Found!");
            alert.setContentText("You need to load a Maze before you can try stepping through its route.");

            alert.showAndWait();
        }
    }
    
    public void BreadthFirstStepButtonPressed() {
        System.out.println("Breadth First Step Button ");
    }

    public void LoadRouteMIClicked() {
        //FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("SER file (*.ser)", "*.ser");
        FileChooser fileChooser = new FileChooser();
        //fileChooser.getExtensionFilters().add(ext);
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Open Maze Route");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            //LoadMap(file.getAbsolutePath());
            rf = RouteFinder.load(file.getAbsolutePath());
            renderMaze();
        }
    }

    public void SaveRouteMIClicked() {
        if(rf == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Map Found!");
            alert.setContentText("You need to load a map before you can try saving a route.");

            alert.showAndWait();
        } else {
            //FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("SER file (*.ser)", "*.ser");
            FileChooser fileChooser = new FileChooser();
            //fileChooser.getExtensionFilters().add(ext);
            fileChooser.setInitialDirectory(new File("./"));
            fileChooser.setTitle("Save RouteFinder State");
            File file = fileChooser.showSaveDialog(stage);
            if(file != null) {
                try {
                    rf.save(file.getAbsolutePath());
                } catch(IOException ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(ex.getClass().getSimpleName() + " occured!");
                    alert.setContentText(ex.getMessage());

                    alert.showAndWait();
                }
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}