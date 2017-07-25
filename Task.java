import java.util.Date;
import java.io.Serializable;
/**
 * An object oriented class of a task. This class is a representation of a 
 * real world task. Fields include task name, description and the due date of
 * this task.
 * 
 * @author Elvin Torres
 * @version 1.5
 */
public class Task implements Serializable
{
    //Ser number
    private static final long serialVersionUID = 2L;
    //Instance Fields and classes
    private String taskName;
    private String description;
    private Date date;
    /**
     * Default contructor
     */
    public Task(){
        //empty task
        taskName = "";
        description = "";
        date = null;
    }

    /**
     * Overloaded Constructor #1
     * @param nameIn Name of the task
     * @param desIn Description of the task
     * @param dateIn Due date of the task
     */
    public Task(String nameIn, String desIn, Date dateIn){
        taskName = nameIn;
        description = desIn;
        date = dateIn;
    }

    /**
     * Accessor method for the taskName field
     */
    public String getName(){
        return taskName;
    }

    /**
     * Accessor method for the description field
     */
    public String getDescription(){
        return description;
    }

    /**
     * Accessor for the date field
     */
    public Date getDate(){
        return date;
    }

    //------------------Mutator methods--------------------
    public void setName(String nameIn){
        taskName = null;
        taskName = nameIn;
    }

    public void setDesc(String descIn){
        description = null;
        description = descIn;
    }

    public void setDate(Date dateIn){
        date = null;        //garbage
        date = dateIn;
    }
    //------------------------------------------------------

    /**
     * Overrided toString method
     * @return name, description and date of the task
     */
    public String toString(){
        return "Task Name: " + taskName + "\nDescription: " + description + "\nDate: " + date.toString();
    }
}