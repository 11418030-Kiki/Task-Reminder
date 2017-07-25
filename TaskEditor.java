
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Calendar;
/**
 * This class is responsible for editing tasks
 */
public class TaskEditor {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="date"
    private DatePicker date; // Value injected by FXMLLoader

    @FXML // fx:id="fp1"
    private FlowPane fp1; // Value injected by FXMLLoader

    @FXML // fx:id="dueDate"
    private Label dueDate; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="fp2"
    private FlowPane fp2; // Value injected by FXMLLoader

    @FXML // fx:id="description"
    private TextArea description; // Value injected by FXMLLoader

    @FXML // fx:id="ok"
    private Button ok; // Value injected by FXMLLoader

    @FXML // fx:id="vb"
    private VBox vb; // Value injected by FXMLLoader

    @FXML // fx:id="bp"
    private BorderPane bp; // Value injected by FXMLLoader

    //FIELDS AND CLASSES
    //static instance variables
    private Calendar c;
    private LocalDate ld;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert fp1 != null : "fx:id=\"fp1\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert dueDate != null : "fx:id=\"dueDate\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert fp2 != null : "fx:id=\"fp2\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert vb != null : "fx:id=\"vb\" was not injected: check your FXML file 'taskEdit.fxml'.";
        assert bp != null : "fx:id=\"bp\" was not injected: check your FXML file 'taskEdit.fxml'.";
        //ACTION HANDLERS
        date.setOnAction(e -> {
                ld = date.getValue();
                c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
            });

        ok.setOnAction(e ->{
            Stage stage = (Stage)bp.getScene().getWindow();
            stage.close();
        });
    }

    public Task editTask(Task task){
        try{
            //load the FXML file.
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskEdit.fxml"));
            //sets the controller for the fxml file
            fxmlLoader.setController(this);
            //loads the parent/root
            Parent parent = fxmlLoader.load();
            //Build the scene graph using the parent
            Scene scene = new Scene(parent); 
            //display our window using the scene node
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Task");
            stage.setResizable(false);
            //instantiate Instance variables
            ld = new java.sql.Date(task.getDate().getTime()).toLocalDate();
            c = Calendar.getInstance(); //intialize the calendar object
            c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());  //set the calendar to the localDate
            //sets the fields of the components
            name.setText(task.getName());
            description.setText(task.getDescription());
            date.setValue(ld);
            //prevents user events in other windows/stages
            stage.initModality(Modality.APPLICATION_MODAL);
            //waits for the user to handle the window
            stage.showAndWait();   //this temporarily prevent other events from other windows/stage
            //Return statement
            return new Task(name.getText(),description.getText(),c.getTime());
        }catch(Exception e){
            e.printStackTrace();
            //if any errors occur then return null
            return null;
        }
    }

    /**
     * Converts the string representation of a date into a local date.
     */
    private LocalDate getLocalDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}