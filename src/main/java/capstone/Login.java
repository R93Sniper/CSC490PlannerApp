package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Login Class
 * 
 * Driver Class object for login screen
 * @author Wahab Quazi
 *         -----------
 */
public class Login {
    
    @FXML
    private TextField loginUsername;
    @FXML
    private TextField loginPassword;
    
    
    /**
     * Validate Login Function
     * 
     * Called when login button is pressed.
     * Checks the Username and Password against the database
     * 
     * @return  True if both values are correct (INCLUSIVE AND)
     *          False if either value is incorrect
     */
    @FXML
    private boolean validateLogin(){
    //Do we maybe want to switch this to return an int?
    //0 for valid, 1 for wrong username, 2 for wrong password? - Wahab
        
    return false;
    }
    
    @FXML
    private void forgotPassword(){
        //App.setRoot("forgotPW");
    }
    
    
    @FXML
    private void switchToCreateAccount() throws IOException {
        App.setRoot("createAccount");
    }
    
    
    @FXML
    private void onLoginPressed() throws IOException {
        //validate Login first...
        UserProfileDataConnector instance = UserProfileDataConnector.getInstance();
        System.out.println("userName: "+loginUsername.getText());
        //boolean validLogin = false;
        if(instance.userFound(loginUsername.getText())){
            UserProfileModel theModel = UserProfileModel.getInstance();
            theModel.setUserName(loginUsername.getText());
            App.setRoot("userProfile");
        }else{
            loginUsername.setText("INVALID USER");    
        }
        
        
    }
    
    
    
}
