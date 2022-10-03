package capstone;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void switchToLogin() throws IOException {
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
