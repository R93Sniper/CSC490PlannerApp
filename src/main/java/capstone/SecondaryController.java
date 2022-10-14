/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author ramoy
 *         Wahab Quazi
 */
public class SecondaryController {
    @FXML
    private void goBack() throws IOException {
        App.setRoot("Landing");  
    }
    @FXML
    private void switchToCalorie() throws IOException {
        App.setRoot("calorieCalc");  
    }
    @FXML
    private void switchToWater() throws IOException {
        App.setRoot("waterCalc");  
    }
}
