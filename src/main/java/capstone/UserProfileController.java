/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class UserProfileController {

    dataConnector userDB = dataConnector.getInstance();
    UserProfileModel instanceUser = UserProfileModel.getInstance();
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
    private TextField textFeet;
    @FXML
    private TextField textInches;
    
    @FXML
    private DatePicker birthDate;
    @FXML
    private ChoiceBox<String> choiceBoxGender;

    @FXML
    private ChoiceBox<String> choiceBoxMedical;
    @FXML
    private ChoiceBox<String> choiceBoxBodyType;
    @FXML
    private Label labelGender;
    @FXML
    private Label labelBodyType;
    @FXML
    private Label labelMedical;
    @FXML
    private Label labelBirthDate;

    private ResultSet result;
    private boolean genderSelected = false;
    private boolean bodyTypeSelected = false;

    public UserProfileController() {
    }

    @FXML
    public void initialize() {
        userDB = dataConnector.getInstance();
        loadProfile();
        loadChoiceBox(); 
  
    }
     
    @FXML
    private void loadChoiceBox(){
        ArrayList<String> genderList = new ArrayList<>(Arrays.asList("Male", "Female"));
        ArrayList<String> bodyTypeList = new ArrayList<>(Arrays.asList("Ectomorph","Mesomorph", "Endomorph"));
        ArrayList<String> medicalList = new ArrayList<>(Arrays.asList("Diabetes", "Asthma", "Low Blood Pressure", "ACL tear"));

        choiceBoxGender.setItems(FXCollections.observableArrayList(genderList));
        choiceBoxGender.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        System.out.println("gender selected is = " + genderList.get(new_value.intValue()));
                        String gender = genderList.get(new_value.intValue());
                        instanceUser.setGender(gender);
                        labelGender.setText(gender);
                        genderSelected = true;
                    }
                });

        choiceBoxBodyType.setItems(FXCollections.observableArrayList(bodyTypeList));
        choiceBoxBodyType.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {

                        String bt = bodyTypeList.get(new_value.intValue());
                        instanceUser.setBodyType(bt);
                        labelBodyType.setText(bt);
                        bodyTypeSelected = true;
                    }
                });

    }

    //loadProfile for userName in the model
    @FXML
    private void loadProfile() {
 
        labelPassword.setText(instanceUser.getPassword());
        textFirstName.setText(instanceUser.getFirstName());
        textLastName.setText(instanceUser.getLastName());
        textEmail.setText(instanceUser.getEmail());
        labelUserName.setText(instanceUser.getUserName());
        labelBodyType.setText(instanceUser.getBodyType());
        labelGender.setText(instanceUser.getGender());
        labelBirthDate.setText(instanceUser.getBirthDate());
        
        String h = instanceUser.getHeight();
        if(h != null)
        {
        String[] height =  h.split("-");
        textFeet.setText(height[0]);
        textInches.setText(height[1]);
        }

    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void saveBtnPressed() {
        String tableName = "User_Profile";
        String usr = instanceUser.getUserName();

        if (textEmail.getText() != null && !textEmail.getText().equals(instanceUser.getEmail())) {
            userDB.updateColumn(tableName, usr, textEmail.getText(), DBCol.Email.toString());
            System.out.println("email updated in the DB");
            instanceUser.setEmail(textEmail.getText());
        }
        if (textFirstName.getText() != null && !textFirstName.getText().equals(instanceUser.getFirstName())) {
            userDB.updateColumn(tableName, usr, textFirstName.getText(), DBCol.First_Name.toString());
            System.out.println("FirstName updated in the DB");
            instanceUser.setFullName(textFirstName.getText());
        }

        if (textLastName.getText() != null && !textLastName.getText().equals(instanceUser.getLastName())) {
            userDB.updateColumn(tableName, usr, textLastName.getText(), DBCol.Last_Name.toString());
            System.out.println("last name updated in the DB");
            instanceUser.setLastName(textLastName.getText());
        }

        if (textFeet.getText() != null && textInches.getText() != null) {
            String h = textFeet.getText() +"-" + textInches.getText();
            userDB.updateColumn(tableName, usr, h, DBCol.Height.toString());
            System.out.println("height updated in the DB");
            instanceUser.setHeight(h);
        }

        if (genderSelected) {
            userDB.updateColumn(tableName, usr, labelGender.getText(), DBCol.Gender.toString());
            System.out.println("gender choice updated in the DB");
            genderSelected = false;
            instanceUser.setGender(labelGender.getText());
        }

        if (bodyTypeSelected) {
            userDB.updateColumn(tableName, usr, instanceUser.getBodyType(), DBCol.Body_Type.toString());
            System.out.println("gender choice updated in the DB");
            bodyTypeSelected = false;
            instanceUser.setBodyType(labelBodyType.getText());
        }
        
        LocalDate date = birthDate.getValue();
        if (date != null) {
            String month = String.valueOf(date.getMonthValue());
            String day = String.valueOf(date.getDayOfMonth());
            String year = String.valueOf(date.getYear());
            String bDate = month+"-"+day+"-"+year;
            labelBirthDate.setText(bDate);
            if (dataConnector.getInstance().updateColumn(tableName, usr, bDate, "Date_Of_Birth")) {
               instanceUser.setBirthDate(bDate);
            }
        }

    }

    @FXML
    private void onUpdateSecQuestions() throws IOException {
        App.setRoot("securityQuestionSelection");
    }

    @FXML
    private void changePassword() throws IOException {
        App.setRoot("forgotPW");
    }

}
