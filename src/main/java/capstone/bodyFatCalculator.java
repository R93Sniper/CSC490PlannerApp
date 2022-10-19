/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author Omar Muy
 */
public class bodyFatCalculator {
    
    @FXML
    private RadioButton rbMale, rbFemale;
    @FXML 
    private TextField ageTF, lbsTF, kgsTF, feetTF, inchesTF, centTF, inches2TF, cm2TF, inches3TF, cm3TF; 
    
    @FXML
    private void switchToCalc() throws IOException{
        App.setRoot("Calculators");
    }
}
