/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class CreateAccountController {


    dataConnector userDB = null;
    @FXML
    private TextField textUserName;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textPassword2;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        userDB = dataConnector.getInstance();

        // TODO
    }

    @FXML
    public void createAccount() throws IOException, NoSuchAlgorithmException {
        //First need to check if userName already Exists in DB
        //if new userName then add to DB 

        boolean passwordMatch = textPassword.getText().equals(textPassword2.getText());

        if (!userDB.existingUser(textUserName.getText())) 
        {
            if (passwordMatch) {
                //userDB.newUserSignup(textUserName.getText(), textPassword.getText());
                UserProfileModel.getInstance().setUserName(textUserName.getText());
                UserProfileModel.getInstance().setPassword(textPassword.getText());
                dataConnector.getInstance().newUserSignup(textUserName.getText(), textPassword.getText());
                App.setRoot("securityQuestionSelection");
            } else {
                textPassword.setText("Passwords Do Not Match");
                textPassword2.setText("");
            }
        } else {
            textUserName.setText("User Name Already Taken");

        }
    }
    
    //private String boolean isValidPassword()

}
