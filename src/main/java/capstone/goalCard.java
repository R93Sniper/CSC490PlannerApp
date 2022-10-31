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
 * @author Omar Muy
 */
public class goalCard {
    
    @FXML
    private void goBack() throws IOException{
        App.setRoot("UserHome");
        
    }
}
