import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MazeApplication1 extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        primaryStage.setTitle("Maze Application");
        
        primaryStage.setScene(getHomeScene());
        primaryStage.show();
        
    }
    
    public MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        Menu actionsMenu = new Menu("Maze Solvers");
        
        MenuItem breadth = new MenuItem("Breadth First Algorithm");
        breadth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                breadthFirstClicked();
            }
        });
        MenuItem depth = new MenuItem("Depth First Algorithm");
        depth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                depthFirstClicked();
            }
        });
        actionsMenu.getItems().add(breadth);
        actionsMenu.getItems().add(depth);
        
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
        
        MenuBar menuBar = getMenuBar();
        
        VBox vbox = new VBox(startInstructionLabel, breadthFirstBtn, depthFirstBtn);
        vbox.setStyle("-fx-background-color: rgb(42, 55, 63)");
        vbox.setAlignment(Pos.CENTER);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox);
        
        Scene scene = new Scene(borderPane, 600, 500, Color.BLACK);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());
        
        return scene;
    }
    
    public void depthFirstClicked() {
        System.out.println("Depth First Clicked!");
    }
    
    public void breadthFirstClicked() {
        System.out.println("Breadth First Clicked!");
        
    }
    
    public void legendClicked() {
        System.out.println("Legend Clicked!");
    }
    
    public void instructionsClicked() {
        System.out.println("Instructions Clicked!");
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}