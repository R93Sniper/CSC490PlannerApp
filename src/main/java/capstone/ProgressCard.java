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
 * @author Wahab Quazi
 */
public class ProgressCard {
    
    
     @FXML
     public void initialize() {
    }    
    
    
    @FXML
    private void onGoBack() throws IOException {  
        App.setRoot("home");
    }
    @FXML
    private void onDietIntake() throws IOException {  
        App.setRoot("dailyIntake");
    }
    
    @FXML
    private void onSave(){  
       
    }
    
}
