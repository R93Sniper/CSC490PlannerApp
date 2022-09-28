/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
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

    private UserProfileModel instance;
    private ResultSet result;
    
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
        instance = UserProfileModel.getInstance();
        result = userDB.getResult(instance.getUserName());

        int id;
        String tempUserName = "";
        String tempPW = "";
        String tempFullName = "";
        String tempEmail = "";
        String tempPhoneNum= "";
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
                tempPhoneNum= result.getString("PhoneNumber");
                tempAddress = result.getString("Address");
                tempGender = result.getString("Gender");
                tempHeight= result.getString("Height");

                //System.out.printf("%d %s \n", id, name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        instance.setUserName(tempUserName);
        instance.setPassword(tempPW);
        instance.setFullName(tempFullName);
        instance.setAddress(tempAddress);
        instance.setEmail(tempEmail);
        instance.setPhoneNum(tempPhoneNum);
        instance.setGender(tempGender);
        instance.setHeight(tempHeight);
        
        labelUserName.setText(tempUserName);
        labelPassword.setText(tempPW);
        textFullName.setText(tempFullName);
        textEmail.setText(tempEmail);
        textPhoneNumber.setText(tempPhoneNum);
        textAddress.setText(tempAddress);
        textGender.setText(tempGender);
        textHeight.setText(tempHeight);

    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private void saveBtnPressed(){
        
        if(textEmail.getText() != null &&!textEmail.getText().equals(instance.getEmail()) ){    
            userDB.updateColumn(instance.getUserName(), textEmail.getText(), DBColumn.EMAIL);
            System.out.println("email updated in the DB");
            instance.setEmail(textEmail.getText());
        }
        if(textFullName.getText() != null && !textFullName.getText().equals(instance.getFullName()) ){           
            userDB.updateColumn(instance.getUserName(), textFullName.getText(), DBColumn.FULLNAME);
            System.out.println("FullName updated in the DB");
            instance.setFullName(textFullName.getText());
        }
        if(textPhoneNumber.getText()!= null && !textPhoneNumber.getText().equals(instance.getPhoneNum()) ){
            userDB.updateColumn(instance.getUserName(), textPhoneNumber.getText(), DBColumn.PHONENUMBER);
            System.out.println("phone number updated in the DB");
            instance.setPhoneNum(textPhoneNumber.getText());
        }
        if(textAddress.getText()!= null && !textAddress.getText().equals(instance.getAddress()) ){      
            userDB.updateColumn(instance.getUserName(), textAddress.getText(), DBColumn.ADDRESS);
            System.out.println("address updated in the DB");
            instance.setAddress(textAddress.getText());
            
        }
        
    }
    
}
