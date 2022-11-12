package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author Wahab Quazi
 */
public class StrengthCard {

    @FXML
    private TextField text_Bench;
    @FXML
    private TextField text_Shoulder;
    @FXML
    private TextField text_Deadlift;
    @FXML
    private TextField text_Squat;
    @FXML
    private TextField text_Leg;
    @FXML
    private Text statusText;

    @FXML
    public void initialize() {
        statusText.setText("");
    }

    @FXML
    public void saveCard() {
        try {
        Integer.parseInt(text_Bench.getText());
        Integer.parseInt(text_Shoulder.getText());
        Integer.parseInt(text_Deadlift.getText());
        Integer.parseInt(text_Squat.getText());
        Integer.parseInt(text_Leg.getText());
        
        /*
        Get Connector
        Dump info into connector
        */
        
        //statusText.setText("Save Successful");
        }
        catch(NumberFormatException exception){
            statusText.setText("Your values are invalid!");     
        }
    }

    /**
     * Clear Card Function
     * 
     * Empties everything in the text fields
     * If the status text was set, this will clear it as well
     */
    @FXML
    public void clearCard() {
        text_Bench.setText("");
        text_Shoulder.setText("");
        text_Deadlift.setText("");
        text_Squat.setText("");
        text_Leg.setText("");
        statusText.setText("");
    }

    @FXML
    public void goBack() throws IOException {
        App.setRoot("progresscard");
    }

}
