import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.lang.StringBuilder;
/**
 * This class represents a task bin,
 * Task will be dropped and sorted from upcoming.
 * This class and its fields must be saved.
 * 
 * @author Elvin Torres 
 * @version 06/20/17
 */
public class Bin implements Serializable
{
    //CLASS & FIELDS
    private static final long serialVersionUID = 1L;
    private ArrayList<Task>taskBin;

    /**
     * Default contructor
     */
    public Bin(){
        taskBin = new ArrayList<>();
    }

    /**
     * Sorts the bin from the soonest using the bubble sort.
     */
    public void sortBin(){
        boolean hadSwaps = true;
        if(taskBin.size() > 1){
            while(hadSwaps) {
                hadSwaps = false; //Assume no swaps
                for(int i = 0; i < taskBin.size()-1; i++) { 
                    //compareTo method returns -1 if the date compareTo it is before, returns 0 if they are the same, and 1 if its after
                    if(taskBin.get(i).getDate().compareTo(taskBin.get(i+1).getDate()) > 0) { 
                        //Swap the values
                        Task tempTask = taskBin.get(i);
                        taskBin.set(i, taskBin.get(i+1));
                        taskBin.set((i+1), tempTask);
                        hadSwaps = true;
                    }
                }
            }
        }
    }

    /**
     * Returns true if the Bin is empty, otherwise it returns false
     * @return Boolean
     */
    public Boolean isEmpty(){
        if(taskBin.size() == 0){
            return true;
        }
        return false;
    }

    /**
     * Returns the task name
     * @return taskName
     */
    public String getTaskNameAt(int index){
        return taskBin.get(index).getName();
    }

    /**
     * Returns the size of the bin
     * @return size
     */
    public int size(){
        return taskBin.size();
    }

    public void addTask(Task taskIn){
        //adds the task to the taskBin arraylist
        if(taskIn != null){
            taskBin.add(taskIn);
        }
    }

    /**
     * Clears the whole bin
     */
    public void clearBin(){
        taskBin.clear();
    }

    /**
     * Accepts a saved Bin and loads it up. 
     * If the Bin is already accoupied, the Bin will 
     * be cleared and overwritten.
     */
    public void loadBin(ArrayList<Task>loadedBin){
        //clears the current task bin
        this.taskBin.clear();
        //sets the current task bin to null
        this.taskBin = null;
        //reintializes the current task bin with the arguement
        this.taskBin = loadedBin;
    }

    /**
     * Removes task at specified index
     */
    public void removeAt(int index){
        taskBin.remove(index);
    }

    /**
     * @Override
     */
    public String toString(){
        if(!isEmpty()){
            StringBuilder str = new StringBuilder("");
            for(int i = 0; i<taskBin.size(); i++){
                str.append(taskBin.get(i).toString());
                str.append("\n");
            }
            return str.toString();
        }
        else{
            return "Empty";
        }
    }
    
    public Task getTaskAt(int i){
        return taskBin.get(i);
    }
    
    public String getTaskToString(int index){
        return taskBin.get(index).toString();
    }
    
    /**
     * Replaces a task with another one. This method is similar 
     * to the ArrayList's method set()
     * 
     * @param task Task replacement
     * @param index Index to replacement
     */
    public void set(int index, Task task){
        taskBin.set(index,task);
    }
}