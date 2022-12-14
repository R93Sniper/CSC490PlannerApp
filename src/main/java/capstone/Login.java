package capstone;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import static java.sql.JDBCType.NULL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Login Class
 * 
 * Driver Class object for login screen
 * @author Wahab Quazi
 *         Nick Andrle
 */
public class Login {
    
    dataConnector instance = dataConnector.getInstance();
    
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
        
    //TODO: GET THIS WORKING WITH DATABASE
    return false;
    }
    
    @FXML
    private void forgotPassword() throws IOException{
      
        if(instance.existingUser(loginUsername.getText())){
            UserProfileModel theModel = UserProfileModel.getInstance();   
            theModel.setUserName(loginUsername.getText());
            App.setRoot("ChangePassword");
        }else{
            loginUsername.setText("enter valid username first");
        }
        
    }
    
    
    @FXML
    private void switchToCreateAccount() throws IOException {
        App.setRoot("CreateAccount");
    }
    
    
    @FXML
    private void onLoginPressed() throws IOException, NoSuchAlgorithmException {
        String str = instance.returnHashPassword(loginPassword.getText());
        if(instance.verifiedUserInstance(loginUsername.getText(),str)){
            UserProfileModel theModel = UserProfileModel.getInstance();
            theModel.setUserName(loginUsername.getText());
            App.setRoot("UserHome");
        }else{
            loginUsername.setText("Invalid Username or Password");
            loginPassword.setText("");
        }
        
        
    }
    
    
    
}
