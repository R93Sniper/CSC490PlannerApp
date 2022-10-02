package capstone;

import java.io.IOException;
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
            App.setRoot("forgotPW");
        }else{
            loginUsername.setText("enter valid username first");
        }
        
    }
    
    
    @FXML
    private void switchToCreateAccount() throws IOException {
        App.setRoot("createAccount");
    }
    
    
    @FXML
    private void onLoginPressed() throws IOException {
        //validate Login first...
        
       // dataConnector instance = dataConnector.getInstance();
        System.out.println("userName: "+loginUsername.getText());
        //boolean validLogin = false;
        if(instance.verifiedUserInstance(loginUsername.getText(), loginPassword.getText())){
            UserProfileModel theModel = UserProfileModel.getInstance();
            theModel.setUserName(loginUsername.getText());
            App.setRoot("home");
        }else{
            loginUsername.setText("Invalid Username or Password");
            loginPassword.setText("");
        }
        
        
    }
    
    
    
}
