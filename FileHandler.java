import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
/**
 * Class that handles all the file operations such as loading and saving files.
 * A JavaFX must be running.
 * 
 * @author Elvin Torres
 * @version 1.5
 */
public class FileHandler
{
    /**
     * Loads the selected file using the Javafx file chooser class and returns the file
     * 
     * @param ownerWindow Owner of this file chooser window
     * @return Selected file
     */
    public static File loadFile(Window ownerWindow){
        //filechooser object
        FileChooser fileC = new FileChooser();
        //file chooser settings
        fileC.setTitle("Load File...");
        fileC.setInitialDirectory(new File("Saved Files\\"));
        fileC.getExtensionFilters().addAll(new ExtensionFilter("Binary Files", "*.bin"));
        //calls the show dialog method to prompt the user to select a file
        File selectedFile = fileC.showOpenDialog(ownerWindow);
        return selectedFile;
    }

    /**
     * Writes the user's data in binary and stores them into a NEW file
     * @param ownerWindow owner of this file chooser window.
     * @param objectIn object to be written in binary
     */
    public static File saveAsFile(Window ownerWindow, Object objectIn){
        //filechooser object
        FileChooser fileC = new FileChooser();
        //file chooser settings
        fileC.setTitle("Save File...");
        fileC.setInitialDirectory(new File("Saved Files\\"));
        fileC.getExtensionFilters().addAll(new ExtensionFilter("Binary Files", "*.bin"));
        try{
            //uses the save dialog box to store the file
            File file = fileC.showSaveDialog(ownerWindow);
            //uses the file streamers to write the file in binary
            FileOutputStream filewriter = new FileOutputStream(file);
            ObjectOutputStream writer = new ObjectOutputStream(filewriter);
            //writes the object in binary
            writer.writeObject(objectIn);
            //closes the streamers
            filewriter.close();
            writer.close();
            //returns the user's file
            return file;
        }catch(Exception e){
            //System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Overwrites an existing file and writes the passed object in binary. 
     * @param objectIn object to write in file
     * @param existingFile user's existing file
     */
    public static void saveFile(Object objectIn, File existingFile){
        try{
            FileOutputStream filewriter = new FileOutputStream(existingFile);
            ObjectOutputStream writer = new ObjectOutputStream(filewriter);
            //writes the object in binary
            writer.writeObject(objectIn);
            //closes the streamers
            filewriter.close();
            writer.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}