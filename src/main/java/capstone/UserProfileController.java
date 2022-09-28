/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class UserProfileController {

    UserProfileDataConnector userDB = null;

    @FXML
    private Label labelPassword;
    @FXML
    private Label labelUserName;
    @FXML
    private TextField textFullName;

    public UserProfileController() {
    }

    @FXML
    public void initialize() {
        System.out.println("initial UserProfile here");
      
        userDB = UserProfileDataConnector.getInstance();
        
        loadProfile();
    }

    @FXML
    private void loadProfile() {
        //returns resultset matching the given username
        ResultSet result = userDB.getResult("kyz34");

        int id;
        String tempUserName = "";
        String tempPW = "";
        String tempFullName = "";
        try {
            while (result.next()) {
                id = result.getInt("ID");
                tempUserName = result.getString("UserName");
                tempPW = result.getString("Password");
                tempFullName = result.getString("FullName");

                //System.out.printf("%d %s \n", id, name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelUserName.setText(tempUserName);
        labelPassword.setText(tempPW);
        textFullName.setText(tempFullName);

    }

}
