/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 *
 * @author Omar Muy Wahab Quazi
 */
public class SecondaryController {

    @FXML
    private void goBack() throws IOException {
        UserProfileModel usr = UserProfileModel.getInstance();
        if (!usr.getUserName().equals("")) {
            App.setRoot("UserHome");
        } else {
            App.setRoot("Landing");
        }
    }

    @FXML
    private void switchToCalorie() throws IOException {
        App.setRoot("calorieCalc");
    }

    @FXML
    private void switchToWater() throws IOException {
        App.setRoot("waterCalc");
    }

    @FXML
    private void switchToBMI() throws IOException {
        App.setRoot("bmiCalc");
    }

    @FXML
    private void switchToBodyFat() throws IOException {
        App.setRoot("bodyfatCalc");
    }
}
