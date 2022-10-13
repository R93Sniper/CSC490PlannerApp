package capstone;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button primaryButton;
    @FXML
    private Button switchToLogfin;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("Calculators");
        
    }
    
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("LoginScreen");
    }
    
    @FXML
    private void switchToProfile() throws IOException {
        App.setRoot("Profile");
    }
    
    @FXML
    private void doSomeAction() throws IOException {
       // App.setRoot("userProfile");
    }
    
}

