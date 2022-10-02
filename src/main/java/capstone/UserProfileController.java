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

    dataConnector userDB = null;

    @FXML
    private Label labelPassword;
    @FXML
    private Label labelUserName;
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textLastName;
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

    private UserProfileModel instanceUser;
    private ResultSet result;
    
    public UserProfileController() {
    }

    @FXML
    public void initialize() {
        System.out.println("initial UserProfile here");
        userDB = dataConnector.getInstance();
        loadProfile();
    }
    
    //loadProfile for userName in the model
    @FXML
    private void loadProfile() {
        //returns resultset matching the given username
        instanceUser = UserProfileModel.getInstance();
        result = userDB.getResult(instanceUser.getUserName(), "User_Profile");

        int id;
        String tempUserName = "";
        String tempPW = "";
        String tempFirstName = "";
        String tempLastName = "";
        String tempEmail = "";
        //String tempPhoneNum= "";
        //String tempAddress = "";
        String tempGender = "";
        String tempHeight= "";
        
        try {
            while (result.next()) {
                //id = result.getInt("ID");
                tempUserName = result.getString("User_Name");
                tempPW = result.getString("User_Password");
                tempFirstName = result.getString("First_Name");
                tempLastName = result.getString("Last_Name");
                tempEmail = result.getString("Email");
                //tempPhoneNum= result.getString("PhoneNumber");
                //tempAddress = result.getString("Address");
                tempGender = result.getString("Gender");
                tempHeight= result.getString("Height");

                System.out.printf("username= %s , h= %s \n", tempUserName, tempHeight);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        instanceUser.setUserName(tempUserName);
        instanceUser.setGender(tempGender);
        instanceUser.setPassword(tempPW);
        instanceUser.setFirstName(tempFirstName);
        instanceUser.setLastName(tempLastName);
        //instanceUser .setAddress(tempAddress);
        instanceUser.setEmail(tempEmail);
        //instanceUser .setPhoneNum(tempPhoneNum);
        instanceUser.setHeight(tempHeight);
        
        
        labelPassword.setText(tempPW);
        textFirstName.setText(tempFirstName);
        textLastName.setText(tempLastName);
        textEmail.setText(tempEmail);
       // textPhoneNumber.setText(tempPhoneNum);
       // textAddress.setText(tempAddress);
        textHeight.setText(tempHeight);
        labelUserName.setText(tempUserName);
        textGender.setText(tempGender);
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void saveBtnPressed(){
        String tableName = "User_Profile";
        String usr = instanceUser.getUserName();
        
        if(textEmail.getText() != null &&!textEmail.getText().equals(instanceUser.getEmail()) ){    
            userDB.updateColumn(tableName, usr, textEmail.getText(), DBColumn.EMAIL);
            System.out.println("email updated in the DB");
            instanceUser.setEmail(textEmail.getText());
        }
        if(textFirstName.getText() != null && !textFirstName.getText().equals(instanceUser.getFirstName()) ){           
            userDB.updateColumn(tableName,usr, textFirstName.getText(), DBColumn.FIRSTNAME);
            System.out.println("FirstName updated in the DB");
            instanceUser.setFullName(textFirstName.getText());
        }  
         if(textGender.getText()!= null && !textGender.getText().equals(instanceUser.getGender()) ){      
            userDB.updateColumn(tableName,usr, textGender.getText(), DBColumn.GENDER);
            System.out.println("gender updated in the DB");
            instanceUser.setGender(textGender.getText());
         }
                  
         if(textLastName.getText()!= null && !textLastName.getText().equals(instanceUser.getLastName()) ){      
            userDB.updateColumn(tableName,usr, textLastName.getText(), DBColumn.LASTNAME);
            System.out.println("last name updated in the DB");
            instanceUser.setLastName(textLastName.getText());
         }
         
         if(textHeight.getText()!= null && !textHeight.getText().equals(instanceUser.getHeight()) ){      
            userDB.updateColumn(tableName,usr , textHeight.getText(), DBColumn.HEIGHT);
            System.out.println("height updated in the DB");
            instanceUser.setHeight(textHeight.getText());
           
         }

        
    }
    
}
