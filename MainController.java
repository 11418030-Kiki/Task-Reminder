import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.*;
import javafx.scene.effect.BlendMode;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.util.Date;
import javafx.scene.paint.Color;
/**
 * This class is the controller class for the Task Remainder application.
 * 
 * All the events that take place in the stage(window) of the Task Reminder 
 * app will be handled by this class.
 * 
 * @author Elvin Torres 
 * @version 07/16
 */
public class MainController
{
    /*  
     *              INJECTED COMPONENTS
     * These components were created in JavaFx Scene Builder
     */
    @FXML
    private Button add;

    @FXML
    private ColorPicker tabColor;

    @FXML
    private Label monthEvents;

    @FXML
    private Button edit;

    @FXML
    private AnchorPane ap;

    @FXML
    private VBox list;

    @FXML
    private Button remove;
    //not injected
    private Stage window;
    //FIELDS AND CLASSES
    private Bin taskBin;
    private File userFile;
    private boolean removeModeOn;
    private boolean editModeOn;
    private ArrayList<TaskTab> tabArray;
    private Color currentTabColor;
    /**
     * Initalizes all the injected @FXML components and assigns their corresponding action
     * listeners via JavaFX Scene Builder.
     */
    public void initialize(){
        //intializes task bin. This will start the user with an empty bin.
        taskBin = new Bin();
        userFile = null;
        removeModeOn = false;
        editModeOn = false;
        tabArray = new ArrayList<>();
        currentTabColor = Color.rgb(242, 182, 50);
        edit.setOnAction(e ->{
                editTask();
            });
    }

    //--------------------------- MAIN METHODS FOR THE USER --------------------------------
    /**
     * Small pop up window will appear and prompt the user for the task information.
     * After the user sets the fields of the task, the addTask method from the Bin
     * class will be called to add the task in the arraylist.
     */
    public void addTask(){
        //toggles other modes to off just in case
        if(removeModeOn)
            toggleRemoveMode();
        else if(editModeOn)
            toggleEditMode();
        //-----------------------------------------------------------------------
        //use the instance method, promptTask to prompt the user for the task
        try{
            taskBin.addTask(promptTask());
            //update the bin
            updateBin();
        }catch(NullPointerException e){
            //do nothing
            //AlertBox.display("ERROR", "NULL");
        }
    }

    /**
     * Prompts the user for the task information
     * @return Task
     */
    private Task promptTask(){
        return AddPopUp.prompt();
    }

    public void removeTask(){
        //Toggles other modes just in case
        if(editModeOn)
            toggleEditMode();
        //------------------------------------------------
        toggleRemoveMode();
        //SWITCHES ACTION HANDLERS
        if(removeModeOn){
            //sets the action handlers for the task tabs to remove mode
            removeMode();
        }
        else{
            //resets the action handler back to display mode 
            displayMode();
        }
    }

    public void editTask(){
        if(removeModeOn)
            toggleRemoveMode();
        //then toggle edit mode to indicate it is on
        toggleEditMode();
        //-----------------------------------
        //SWITCHES ACTION HANDLERS
        if(editModeOn) 
            editMode();
        else
            displayMode();
    }

    private void toggleRemoveMode(){
        //toggles the removeModeOn boolean
        this.removeModeOn = !this.removeModeOn;
        if(removeModeOn)
        //changes the blend mode of the remove button indicate the remove mode is on
            remove.setBlendMode(BlendMode.SOFT_LIGHT);
        else
        //sets the blend mode back to normal
            remove.setBlendMode(BlendMode.SRC_OVER);
    }

    public void toggleEditMode(){
        //toggles the editModeOn boolean
        this.editModeOn = !this.editModeOn;
        if(editModeOn)
        //changes the blend mode of the remove button indicate the remove mode is on
            edit.setBlendMode(BlendMode.SOFT_LIGHT);
        else
        //sets the blend mode back to normal
            edit.setBlendMode(BlendMode.SRC_OVER);
    }

    //------------------------------ FILE FUNCTIONS------------------------------------------
    /**
     * Loads user's saved file
     */
    public void load(){
        //creates a temporary bin to store the loaded class
        File loadedFile = FileHandler.loadFile(ap.getScene().getWindow());     //filehandler will display the dialog window to load a file
        Bin temp = null;   //temporary Bin
        if(loadedFile != null){
            // create File Streamers
            FileInputStream filereader = null;
            ObjectInputStream reader = null;
            try{
                //read files with FileInputStream class
                filereader = new FileInputStream(loadedFile);
                reader = new ObjectInputStream(filereader);     //feed the filereader to the objectInputStream
                //if the file exist then read the object and cast it into the Bin class
                temp = (Bin)reader.readObject();
            }catch(FileNotFoundException e){
                AlertBox.display("Message","File not found.");
            }
            catch(ClassNotFoundException e){
                System.out.println(e.getMessage());
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
            finally{
                try{
                    //close readers
                    filereader.close();
                    reader.close();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            if(temp != null){
                //relieves the memory held by the taskBin object and reinitalize with the temp object
                taskBin = null;
                taskBin = temp;
                //sets the loadedFile file as the user's current file
                userFile = loadedFile;
                //erase temp and loaded file from memory
                temp = null;
                loadedFile = null;
                //update the bin
                updateBin();
            }
            else{
                AlertBox.display("Message", "Error loading file");
            }
        }
        else{
            //do nothing
            //System.out.println("null");
        }
        //Garbage Collection
        System.gc();
    }

    /**
     * Saves user's data
     */
    public void save(){
        //if the user has not created a file yet then save as...
        if(userFile == null){
            userFile = FileHandler.saveAsFile(ap.getScene().getWindow(), taskBin);
        }
        else{   //else save to the current file
            FileHandler.saveFile(taskBin, userFile);
        }
    }

    /**
     * Save as file
     */
    public void saveAs(){
        userFile = FileHandler.saveAsFile(ap.getScene().getWindow(), taskBin);
    }

    /**
     * Creates a new instance for the user
     */
    public void newFile(){
        userFile = null;
        clearList();
    }

    ///////////////////////////--------Action handlers--------------------------------------------------

    /**
     * Sets the action handler to display mode for the tabs
     */
    private void displayMode(){
        for(int i=0;i<tabArray.size(); i++){
            tabArray.get(i).setOnAction((ActionEvent e) -> {
                    //retrieves the index of the event in the task tab list
                    TaskTab temp = (TaskTab)e.getSource();
                    //creates the Task display and displays the task.
                    TaskDisplay td =  new TaskDisplay(taskBin.getTaskAt(tabArray.indexOf(temp)));
                    td.show();

                });
            //Garbage collection
            System.gc();
        }
    }

    /**
     * Sets the Action Handler to remove mode for the tab
     */
    public void removeMode(){
        for(int i=0;i<tabArray.size(); i++){
            tabArray.get(i).setOnAction((ActionEvent e) -> {
                    //Retrieves the source of the event and cast it to a TaskTab object
                    TaskTab temp = (TaskTab)e.getSource();
                    //removes the specified task using temp object index
                    this.taskBin.removeAt(tabArray.indexOf(temp));
                    tabArray.remove(tabArray.indexOf(temp));
                    //update
                    this.list.getChildren().remove(temp);
                    //GARBAGE COLLECTION
                    temp = null;
                    System.gc();
                });
        }
    }

    /**
     * This mode can edit existing task on the task list
     */
    public void editMode(){
        for(int i=0;i<tabArray.size(); i++){
            tabArray.get(i).setOnAction((ActionEvent e) -> {
                    try{
                        //Retrieves the source of the event and cast it to a TaskTab object
                        TaskTab temp = (TaskTab)e.getSource();
                        //creates a new task editor
                        TaskEditor ed = new TaskEditor();
                        //retrieves the task from the task bin and edits the that specific task
                        Task editedTask = ed.editTask(taskBin.getTaskAt(tabArray.indexOf(temp)));
                        if(editedTask != null){
                            //calls the Bin's set method to replace the task
                            taskBin.set(tabArray.indexOf(temp), editedTask);
                        }
                        //GARBAGE COLLECTION
                        temp = null;
                        editedTask = null;
                        ed = null;
                        System.gc();
                        //updates the bin
                        updateBin();
                    }catch(Exception e1){
                        System.out.println(e1.getMessage());
                    }
                });
        }
    }

    //--------------------------------- MISC. & METHODS FOR THE APPLICATION ONLY---------------------------------
    /**
     * Exits the application
     * Only the exit option in the menu uses this method
     */
    public void exit(){
        System.exit(0);
    }

    /**
     * Updates the VBOX list relatived to the taskBin and
     * creates Task Tabs(buttons).
     */
    private void updateBin(){
        //First, check if the taskBin is Empty
        if(!taskBin.isEmpty()){
            //Second, sort the bin by date
            taskBin.sortBin();
            //clear the content of the task bin without clearing out the taskBin
            list.getChildren().clear();
            //clears out tabArray arrayList to prevent accumulation
            tabArray.clear();
            //replace the content of the list and tab array with the new sorted bin
            for(int i = 0; i<taskBin.size(); i++){
                //ADDS ALL THE TASK PARALLEL TO THE TASKBIN
                tabArray.add(new TaskTab(taskBin.getTaskNameAt(i), currentTabColor));
                //adds the nodes to the VBOX to display them
                list.getChildren().add(tabArray.get(i));
                //SETS THE CORRESPONDING MODE FOR THE TASK LIST
                if(editModeOn)
                    editMode();
                else if(removeModeOn)
                    removeMode();
                else
                    displayMode();
            }
            //GARBAGE COLLECTION
            System.gc();
        }
        else{
            //do nothing
        }
    }

    private Stage getCurrentStage(){
        return (Stage)ap.getScene().getWindow();
    }

    /**
     * Clears the VBOX's components and taskBin.
     */
    public void clearList(){
        list.getChildren().clear();
        taskBin.clearBin();
        tabArray.clear();
        System.gc();
    }

    /**
     * Changes the color of the tabs when the user
     * picks a color in the edit menu
     */
    public void changeTabColor(){
        currentTabColor = tabColor.getValue();
        updateBin();
    }
}