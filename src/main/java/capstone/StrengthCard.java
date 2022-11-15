package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Progress Card - Strength Card
 * Controller Class for StrengthCard
 *
 * @author Wahab Quazi
 */
public class StrengthCard {

    //Member Variables
    DailyStrengthCardsConnector strConnect = new DailyStrengthCardsConnector();
    
    //FXML Variables
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

    
    
    
    /**
     * Initialize Function
     *
     * When the card is loaded, empty the information in the status text
     */
    @FXML
    public void initialize() {
        statusText.setText("");
    }

    /**
     * Save Card Function
     *
     * Attempt to save the information presented in the card
     */
    @FXML
    public void saveCard() {
        try {
            //We Parse the text values as ints to ensure they're valid.
            //As a knock-on effect, this checks for null/empty values
            
            Integer.parseInt(text_Bench.getText());
            Integer.parseInt(text_Shoulder.getText());
            Integer.parseInt(text_Deadlift.getText());
            Integer.parseInt(text_Squat.getText());
            Integer.parseInt(text_Leg.getText());
            //TODO: Change so not all values are required?
            //Would require connector to account for that
            
            //Shove the information into the strength card
            strConnect.dailyStrengthCards(text_Bench.getText(),text_Deadlift.getText(), 
                    text_Squat.getText(), text_Leg.getText(), text_Shoulder.getText());
            statusText.setText("Save Successful");
        } catch (NumberFormatException exception) {
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

    /**
     * Go Back Function
     *
     * Returns you to progress card when the back button is pressed
     *
     * @throws IOException
     */
    @FXML
    public void goBack() throws IOException {
        App.setRoot("progresscard");
    }

}
