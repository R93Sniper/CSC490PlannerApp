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
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textPhoneNumber;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textGender;
    @FXML
    private TextField textHeight;

    public UserProfileController() {
    }

    @FXML
    public void initialize() {
        System.out.println("initial UserProfile here");
      
        userDB = UserProfileDataConnector.getInstance();
        
        loadProfile();
    }
    
    //loadProfile for userName in the model
    @FXML
    private void loadProfile() {
        //returns resultset matching the given username
        UserProfileModel instance = UserProfileModel.getInstance();
        ResultSet result = userDB.getResult(instance.getUserName());

        int id;
        String tempUserName = "";
        String tempPW = "";
        String tempFullName = "";
        String tempEmail = "";
        String temPhoneNum= "";
        String tempAddress = "";
        String tempGender = "";
        String tempHeight= "";
      
        try {
            while (result.next()) {
                id = result.getInt("ID");
                tempUserName = result.getString("UserName");
                tempPW = result.getString("Password");
                tempFullName = result.getString("FullName");
                tempEmail = result.getString("Email");
                temPhoneNum= result.getString("PhoneNumber");
                tempAddress = result.getString("Address");
                tempGender = result.getString("Gender");
                tempHeight= result.getString("Height");

                //System.out.printf("%d %s \n", id, name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelUserName.setText(tempUserName);
        labelPassword.setText(tempPW);
        textFullName.setText(tempFullName);
        textEmail.setText(tempEmail);
        textPhoneNumber.setText(temPhoneNum);
        textAddress.setText(tempAddress);
        textGender.setText(tempGender);
        textHeight.setText(tempHeight);


    }

}
