package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Progress Card - Measurements Card
 * Controller Class for MeasurementsCard
 *
 * @author Wahab Quazi
 */
public class MeasurementsCard {

    //Member Variables
    DailyMeasurementsConnector measConnect = new DailyMeasurementsConnector();
    
    //FXML Variables
    @FXML
    private TextField text_Neck;
    @FXML
    private TextField text_Arms;
    @FXML
    private TextField text_Waist;
    @FXML
    private TextField text_Hips;
    @FXML
    private TextField text_Leg;
    @FXML
    private Text statusText;

    private UserProfileModel user = UserProfileModel.getInstance();
    
    
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
            
            Integer.parseInt(text_Neck.getText());
            Integer.parseInt(text_Arms.getText());
            Integer.parseInt(text_Waist.getText());
            Integer.parseInt(text_Hips.getText());
            Integer.parseInt(text_Leg.getText());
            //TODO: Change so not all values are required?
            //Would require connector to account for that
            
            //Shove the information into the strength card
            measConnect.dailyMeasurements(text_Neck.getText(), text_Arms.getText(), 
                    text_Waist.getText(), text_Hips.getText(), text_Leg.getText());
            //update the measurements id in the Progress Card
            int pId = user.getProgressCardId();
            if(pId!= 0){ 
            ProgressCardConnector pc = new ProgressCardConnector();
            pc.updateDailyMeasurementsID(pId, String.valueOf(pc.getLastID("Daily_Measurements_Cards")) );
            }
            
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
        text_Neck.setText("");
        text_Arms.setText("");
        text_Waist.setText("");
        text_Hips.setText("");
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
