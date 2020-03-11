import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import java.net.URL;
import javafx.scene.*;

public class MazeApplication extends Application
{
    // We keep track of the count, and label displaying the count:
    private int count = 0;
    private Label myLabel = new Label("0");

    @Override
    public void start(Stage stage) throws Exception
    {

        Parent root = FXMLLoader.load(getClass().getResource("BasicApplication_css.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("BasicApplication.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void buttonClick(ActionEvent event)
    {
        // Counts number of button clicks and shows the result on a label
        count = count + 1;
        myLabel.setText(Integer.toString(count));
    }
}
