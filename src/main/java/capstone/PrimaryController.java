package capstone;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button primaryButton;
    @FXML
    private Button switchToLogfin;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private void switchToProfile() throws IOException {
        App.setRoot("userProfile");
    }
    
    @FXML
    private void doSomeAction() throws IOException {
       // App.setRoot("userProfile");
    }
    
}
