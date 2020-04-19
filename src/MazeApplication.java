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
import javafx.scene.control.MenuButton;
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

/**
* MazeApplication.java - a javafx class for displaying the application as GUI to the user
* @author Gurneet Bhatia
* @version 1.0
*/
public class MazeApplication extends Application{
    
    /** The stage. */
    // the stage that is used through the application
    Stage stage = null;
    
    /** the Pane on which the blocks of the maze are rendered */
    Pane canvas;
    
    /** the parts of the maze */
    public List<List<Rectangle>> blocks;
    
    /** The RouteFinder object. */
    public RouteFinder rf;
    
    /** The screen width (for the stage). */
    public int screenWidth = 600;
    
    /** The screen height (for the stage). */
    public int screenHeight = 500;
    
    /**
     * Start.
     *
     * @param primaryStage the primary stage on which the GUI is rendered for the user
     * @throws Exception thrown when any kind of error occurs while loading the stage and its scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Maze Application");
        primaryStage.setScene(getHomeScene());
        primaryStage.show();

    }

    
    /**
     * Gets the menu bar - most of its features are common to all the scenes.
     *
     * @param disableControls used to disable the Controls Menu when on the home scene
     * @return the menu bar
     */
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
        saveRoute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SaveRouteMIClicked();
            }
        });
        controlsMenu.getItems().add(loadMap);
        controlsMenu.getItems().add(loadRoute);
        controlsMenu.getItems().add(saveRoute);
        // the controls menu is not accessible when it is called from the home page
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

    /**
     * Gets the home scene.
     *
     * @return the home scene
     */
    public Scene getHomeScene() {
        // this shows the home page/start page and returns a scene
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

        Scene scene = new Scene(borderPane, screenWidth, screenHeight);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());

        return scene;
    }

    /**
     * Gets the depth first scene.
     *
     * @return the depth first scene
     */
    public Scene getDepthFirstScene() {
        // renders the scene for the depth first search

        MenuBar menuBar = getMenuBar(false);

        Button stepBtn = new Button("Step");
        stepBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DepthFirstStepButtonPressed();
                }
            });

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

        Scene scene = new Scene(borderPane, screenWidth, screenHeight);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());

        return scene;
    }

    /**
     * Gets the breadth first scene.
     *
     * @return the breadth first scene
     */
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

        Scene scene = new Scene(borderPane, screenWidth, screenHeight);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());

        return scene;
    }

    /**
     * Depth first Button or MenuItem clicked.
     */
    public void depthFirstClicked() {
        //re-initialise the variables that are common
        blocks = null;
        rf = null;
        // change the scene on the stage to the one that sets the step button to call the relevant step method
        stage.setScene(getDepthFirstScene());
        stage.setTitle("Depth First Solver");
    }

    /**
     * Breadth first Button or MenuItem clicked.
     */
    public void breadthFirstClicked() {
        //re-initialise the variables that are common
        blocks = null;
        rf = null;
        stage.setScene(getBreadthFirstScene());
        stage.setTitle("Breadth First Solver");
    }

    /**
     * Legend MenuItemclicked.
     */
    public void legendClicked() {
        System.out.println("Legend Clicked!");
    }

    /**
     * Instructions MenuItem clicked.
     */
    public void instructionsClicked() {
        System.out.println("Instructions Clicked!");
    }

    /**
     * Load map MI clicked.
     * Displays a FileChooser to the user that allows them to load a text file
     */
    public void LoadMapMIClicked() {
        // display a file chooser to the user that allows them to select .txt files which represent the maze structure
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("TXT file (*.txt)", "*.txt");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(ext);
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Open Maze Path");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            // once the file is chosen, load the map
            LoadMap(file.getAbsolutePath());
        }
    }

    /**
     * Load map.
     *
     * @param filename the filename that is used to load the maze structure
     */
    public void LoadMap(String filename) {
        // validate the maze file and if no errors are found render the maze on the Pane
        // if an error is encountered, inform the user
        try {
            Maze maze = Maze.fromTxt(filename);
            rf = new RouteFinder(maze);
            rf.setAlgorithm(stage.getTitle().equals("Depth First Solver"));
            renderMaze();
        } catch(InvalidMazeException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getClass().getSimpleName() + " occured!");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    /**
     * Render maze graphically and display on the canvas Pane object.
     */
    public void renderMaze() {
        // start by re-initialising the 2D list that represents the graphics of the maze
        blocks = new ArrayList<List<Rectangle>>();
        // and resetting the pane that the rectangle objects are rendered on
        canvas.getChildren().clear();
        // since maze.maze has private access, call the relevant method to acces it
        Maze maze = rf.getMaze();
        String[] mazeStr = maze.toString().split("\n");
        // made BlockHeight and BlockWidth variables so that if I wanted to change them later on, it would be much easier than changing every number individually
        int BlockHeight = 50;
        int BlockWidth = 50;
        // auto-resize the stage depending on the size of the maze
        screenWidth = screenWidth < (BlockWidth * mazeStr[0].length()) ? (BlockWidth * mazeStr[0].length()) : screenWidth;
        screenHeight = (screenHeight - 50) <= (BlockHeight * mazeStr.length) ? (BlockHeight * (mazeStr.length+1)) + 75 : screenHeight;
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        for(int row=0;row<mazeStr.length;row++) {
            // each row consists of a list of rectangle object, so iterate over those too
            List<Rectangle> currentRow = new ArrayList<Rectangle>();
            for(int col=0;col<mazeStr[row].length();col++) {
                // use the getColour to find out what colour the rectangle at certain coordinates should be
                // it depends on what character is held in the maze string at that index
                Color colour = getColour(mazeStr[row].charAt(col));
                // generate the rectangle object
                Rectangle rectangle = new Rectangle(BlockWidth, BlockHeight,colour);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                // place the rectangle on the canvas at the relevant coordinates
                rectangle.relocate(col*BlockWidth, row*BlockHeight);
                currentRow.add(rectangle);
                // add the rectangle to the canvas to show to the user
                canvas.getChildren().addAll(rectangle);
            }
            blocks.add(currentRow);
        }
        List<Tile> route = rf.getRoute();
        List<Tile> traversedTiles = rf.getTraversedTiles();
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
                } else if(traversedTiles.contains(tile)){
                    block.setFill(Color.AQUAMARINE);
                }
            }
        }
    }

    /**
     * Gets the colour.
     * The colour returned depends on the type of character that was provided.
     *
     * @param c the c
     * @return the colour
     */
    public Color getColour(char c) {
        HashMap<Character, Color> colours = new HashMap<Character, Color>();
        colours.put('.', Color.SNOW);
        colours.put('e', Color.GREEN);
        colours.put('x', Color.RED);
        colours.put('#', Color.PERU);

        return colours.get(c);
    }

    /**
     * Depth first step button pressed.
     * Calls the RouteFinder.step() method to determine what happens next
     */
    public void DepthFirstStepButtonPressed() {
        if(rf != null) {
            Maze maze = rf.getMaze();
            int BlockHeight = 50;
            int BlockWidth = 50;
            String[] mazeStr = maze.toString().split("\n");
            try {
                //finished
                if(rf != null && !rf.step()) {
                    List<Tile> route = rf.getRoute();
                    List<Tile> traversedTiles = rf.getTraversedTiles();
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
                            } else if(traversedTiles.contains(tile)){
                                block.setFill(Color.AQUAMARINE);
                            }
                        }
                    }
                }
                rf.setAlgorithm(true);
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
    
    /**
     * Breadth first step button pressed.
     * Calls the RouteFinder.bestRouteStep() method to find out what happens next
     */
    public void BreadthFirstStepButtonPressed() {
        //System.out.println("Breadth First Step Button ");
        if(rf != null) {
            Maze maze = rf.getMaze();
            int BlockHeight = 50;
            int BlockWidth = 50;
            String[] mazeStr = maze.toString().split("\n");
            try {
                //finished
                if(rf != null && !rf.bestRouteStep()) {
                    List<Tile> route = rf.getTraversedTiles();
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
                rf.setAlgorithm(false);
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

    /**
     * Load route MenuItem clicked.
     * Displays a FileChooser to the user that allows them to select and load a serialised object file
     */
    public void LoadRouteMIClicked() {
        //FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("SER file (*.ser)", "*.ser");
        FileChooser fileChooser = new FileChooser();
        //fileChooser.getExtensionFilters().add(ext);
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Open Maze Route");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            //LoadMap(file.getAbsolutePath());
            try {
                RouteFinder newRF = RouteFinder.load(file.getAbsolutePath());
                boolean algorithm = newRF.getAlgorithm();
                // the scene needs to be changed
                // the user is either on the breadth first scene right now and they have loaded a depth first or vice versa
                if(algorithm) {
                    // need the depth first scene
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Scene Change Required!");
                    alert.setContentText("You have chosen to load a route that was initialised using the Depth First Search. You are being redirected to that page now.");
                    alert.showAndWait();

                    depthFirstClicked();
                } else {
                    // need the breadth first scene
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Scene Change Required!");
                    alert.setContentText("You have chosen to load a route that was initialised using the Breadth First Search. You are being redirected to that page now.");
                    alert.showAndWait();

                    breadthFirstClicked();
                }
                rf = newRF;
                renderMaze();
            } catch(Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(ex.getClass().getSimpleName() + " occured!");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * Save route MI clicked.
     * Saves the current state of the RouteFinder object rf as a java serialised object with the filename specified by the user
     * The filepath is also chosen by the user.
     */
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
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Operation Successful");
                    alert.setHeaderText("File successfully saved!");
                    alert.setContentText("The current state of the route solver has been successfully saved.");

                    alert.showAndWait();
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

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        // this is where the program starts, it essentially calls the start method
        Application.launch(args);
    }

}