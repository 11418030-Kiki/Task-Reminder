import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.layout.*;
import java.util.Date;
import javafx.geometry.Insets;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.application.*;
import javafx.geometry.Pos;
/**
 * A small pop up window that prompts the user a task
 * Parameters for the task is Task name, description and its date.
 * 
 * @author Elvin Torres
 * @version 06/09/17
 */
public class AddPopUp
{
    //static instance variables
    private static Calendar c;
    private static boolean userPickedDate;
    private static LocalDate ld;
    /**
     * Prompts the user for the task information using a
     * small window display
     */
    public static Task prompt(){
        //resets the boolean from last instance
        userPickedDate = false;
        ld = null;
        c = Calendar.getInstance();
        //Classes and local variables
        Stage window = new Stage();
        //prevents user events in other windows/stages
        window.initModality(Modality.APPLICATION_MODAL);
        //sets the size of the window/stage
        window.setWidth(250);
        window.setHeight(255);
        window.setResizable(false);
        //sets the title of the window
        window.setTitle("Add Task");
        //----------Layout---------
        BorderPane border = new BorderPane();
        FlowPane header = new FlowPane(10, 10);
        FlowPane footer = new FlowPane(10, 10);
        //----------Components-----
        Label lb1 = new Label("Task Name");
        TextField txtField = new TextField();
        TextArea txtArea = new TextArea();

        //more components
        Label lb2 = new Label("Date");
        DatePicker datePicker = new DatePicker();
        Button okB = new Button("Ok");

        //sets the prompt text and other settings
        txtArea.setPromptText("Description...");
        txtArea.setWrapText(true);
        border.setMargin(txtArea, new Insets(10, 5, 10, 5));
        border.setMargin(header, new Insets(3, 5, 0, 5));
        border.setMargin(footer, new Insets(0, 5, 0, 5));
        footer.setAlignment(Pos.CENTER);
        header.setAlignment(Pos.CENTER);
        border.setStyle("-fx-background-color: #252839;");
        lb1.setStyle("-fx-text-fill: white;");
        lb2.setStyle("-fx-text-fill: white;");
        okB.setStyle("-fx-background-color: #b5b5b7;");
        okB.setStyle("-fx-border-color: #b5b5b7;");
        datePicker.setPrefWidth(150);
        //action listeners
        okB.setOnAction(e -> {
                window.close();
            });
        //DATE PICKER ACTION LISTENER
        datePicker.setOnAction(e -> {
                ld = datePicker.getValue();
                c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
                userPickedDate = true;
            });
            
        window.setOnCloseRequest(e -> {
            window.close();
            userPickedDate = false; //prevents from using the first condition in the return statement
        });
        //----adding components on layout--------
        header.getChildren().addAll(lb1, txtField);
        footer.getChildren().addAll(lb2, datePicker, okB);
        border.setTop(header);
        border.setCenter(txtArea);
        border.setBottom(footer);
        //creates the scene of the wind0ow
        Scene scene = new Scene(border);
        //Sets the scene of the stage
        window.setScene(scene);
        //waits for the user to handle the window
        window.showAndWait();   //this temporarily other events from other windows/stage
        
        //-----------------------RETURN STATEMENTS-----------------------------------------\\
        try{
            if(userPickedDate){
                Task t = new Task(txtField.getText(), txtArea.getText(), c.getTime());
                return t;
            }
            else{
                return null;
            }
        }catch(Exception e){
            AlertBox.display("Message","Incorrect Input.");
            return null;
        }
    }
}