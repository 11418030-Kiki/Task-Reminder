import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.application.*;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class TaskDisplay{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="fp1"
    private FlowPane fp1; // Value injected by FXMLLoader

    @FXML // fx:id="dueDate"
    private Label dueDate; // Value injected by FXMLLoader

    @FXML // fx:id="fp2"
    private FlowPane fp2; // Value injected by FXMLLoader

    @FXML // fx:id="description"
    private Label description; // Value injected by FXMLLoader

    @FXML // fx:id="ok"
    private Button ok; // Value injected by FXMLLoader

    @FXML // fx:id="vb"
    private VBox vb; // Value injected by FXMLLoader

    @FXML // fx:id="title"
    private Label title; // Value injected by FXMLLoader

    @FXML // fx:id="bp"
    private BorderPane bp; // Value injected by FXMLLoader
    
    //instance variables
    String name, desc, date;
    /**
     * Constructor #1
     * @param task Task to display.
     */
    public TaskDisplay(Task task){
        //retrieves the task details using the
        name = task.getName();
        desc = task.getDescription();
        date = DateFormat.getDateInstance().format(task.getDate());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        assert fp1 != null : "fx:id=\"fp1\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        assert dueDate != null : "fx:id=\"dueDate\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        assert fp2 != null : "fx:id=\"fp2\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        assert vb != null : "fx:id=\"vb\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        assert bp != null : "fx:id=\"bp\" was not injected: check your FXML file 'taskDisplay.fxml'.";
        //sets the components' values using the argument from the constructor
        title.setText(name);
        description.setText(desc);
        dueDate.setText(dueDate.getText() + " " + date);
        //sets the action handlers
        ok.setOnAction(e -> this.closeWindow());
    }

    /**
     * Displays the window 
     */
    public void show(){
        try{
            //load the FXML file.
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskDisplay.fxml"));
            //sets the controller for the fxml file
            fxmlLoader.setController(this);
            //loads the parent/root
            Parent parent = fxmlLoader.load();
            //Build the scene graph using the parent
            Scene scene = new Scene(parent); 
            //display our window using the scene graph
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Task");
            stage.setResizable(false);
            //prevents user events in other windows/stages
            stage.initModality(Modality.APPLICATION_MODAL);
            //waits for the user to handle the window
            stage.showAndWait();   //this temporarily prevent other events from other windows/stage    
        }catch(Exception e){
            AlertBox.display("Error",e.getMessage());
        }
    }

    /**
     * Closes the current window/stage
     */
    public void closeWindow(){
        Stage stage = (Stage)bp.getScene().getWindow();
        stage.close();
    }
}