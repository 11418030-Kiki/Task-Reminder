import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
//import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
/**
 * This JavaFX application is a task reminder,
 * user can create a task with its name, description and due date.
 * Once the user has created the task, the list will sort the task
 * by its due date.
 * @author Elvin Torres
 * @version 1.0
 */
public class TaskReminderApp extends Application{
    public void start(Stage window) throws IOException {
        //load the FXML file.
        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        //Build the scene graph
        Scene scene = new Scene(parent); 
        //sets the look and feel of the UI
        setUserAgentStylesheet(this.STYLESHEET_CASPIAN);
        //display our window using the scene graph
        window.setScene(scene);
        window.setTitle("Task Reminder");
        window.setResizable(false);
        //On Close Operation
        window.setOnCloseRequest(e -> {
                int option = AlertBox.prompt("Message","Are you sure you want to exit?");
                if(option == AlertBox.YES_OPTION)
                    System.exit(0);
                else
                    e.consume();
            });
        window.show();
    }

    public static void main(String[] arg){
        //launch the application
        launch(arg);
    }
}
