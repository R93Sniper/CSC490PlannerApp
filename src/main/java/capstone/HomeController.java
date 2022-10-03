/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void onLogoutPressed() throws IOException {
        App.setRoot("login");
    }
    @FXML
    private void onViewProfile() throws IOException {
        App.setRoot("userProfile");
    }
    @FXML
    private void onCalcWater() throws IOException {
        App.setRoot("secondary");
    }
    
    
}
