import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Shape;
/**
 * This class is an extended class of the Button component
 * 
 * @author Elvin Torres 
 * @version 1.0
 */
public class TaskTab extends Button
{
    /**
     * Creates a pre-made customized button
     * @return Customized button
     */
    public TaskTab(String taskName, Color color){
        setText(taskName );
        //customizes the button
        setAlignment(Pos.CENTER_LEFT);
        setFont(Font.font("Arial",FontWeight.BOLD, 14));
        //sets the background color using the javafx library
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        //set the size of the component
        setPrefWidth(389);
        //CSS
        setStyle("-fx-text-fill: grey;");
        setStyle("-fx-border-style: solid;");
        setStyle("-fx-border-color: white;");
    }
}