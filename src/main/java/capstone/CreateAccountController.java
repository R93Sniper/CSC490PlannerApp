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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class CreateAccountController {

    UserProfileDataConnector userDB = null;

    @FXML
    private TextField textUserName;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textFullName;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        userDB = UserProfileDataConnector.getInstance();
        // TODO
    }

    @FXML
    public void createAccount() throws IOException {
        //First need to check if userName already Exists in DB
        //if new userName then add to DB 
        //switch to loginScreen

        System.out.println("vars: " + textUserName.getText() + " : "
                + " : " + textPassword.getText()
                + " : " + textFullName.getText());

        if (!userDB.userFound(textUserName.getText())) {
            userDB.insertAccountToDB(textUserName.getText(), textPassword.getText(), textFullName.getText());
            App.setRoot("login");
        } else {
            textUserName.setText("User Name Already Taken");
            textPassword.setText("");
            
        }

    }

}
