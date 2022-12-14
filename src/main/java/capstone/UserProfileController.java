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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    public void initialize() {
        userDB = dataConnector.getInstance();
        loadProfile();
        loadChoiceBox(); 
  
    }
     
    private void loadChoiceBox(){
        ArrayList<String> genderList = new ArrayList<>(Arrays.asList("Male", "Female"));
        ArrayList<String> bodyTypeList = new ArrayList<>(Arrays.asList("Ectomorph","Mesomorph", "Endomorph"));
     //   ArrayList<String> medicalList = new ArrayList<>(Arrays.asList("Diabetes", "Asthma", "Low Blood Pressure", "ACL tear"));

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
        
        /*
        
        choiceBoxMedical.setItems(FXCollections.observableArrayList(medicalList));
        choiceBoxMedical.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        System.out.println("medical selected is = " + medicalList.get(new_value.intValue()));
                        String m = medicalList.get(new_value.intValue());
                        //instanceUser
                        labelMedical.setText(m);
                        //genderSelected = true;
                    }
                });

                */
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
        if(h != null && !h.equals(""))
        {
        if(h.contains("-")){
        String[] height =  h.split("-");
        String h1 = height[0].length()>0? height[0]: "0" ;
        String h2 = height[1].length()>0? height[1]: "0" ;
        textFeet.setText(h1);
        textInches.setText(h2);
        }
        }

    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    private void saveBtnPressed() {
        String tableName = "User_Profile";
        String usr = instanceUser.getUserName();
        String msg = "";

        if (textEmail.getText() != null && !textEmail.getText().equals(instanceUser.getEmail())) {
            userDB.updateColumn(tableName, usr, textEmail.getText(), DB_Col.Email.toString());
            System.out.println("email updated in the DB");
            msg += "\n email updated to Profile";
            instanceUser.setEmail(textEmail.getText());
        }
        if (textFirstName.getText() != null && !textFirstName.getText().equals(instanceUser.getFirstName())) {
            userDB.updateColumn(tableName, usr, textFirstName.getText(), DB_Col.First_Name.toString());
            System.out.println("FirstName updated in the DB");
            msg += "\n First Name updated to Profile";
            instanceUser.setFullName(textFirstName.getText());
        }

        if (textLastName.getText() != null && !textLastName.getText().equals(instanceUser.getLastName())) {
            userDB.updateColumn(tableName, usr, textLastName.getText(), DB_Col.Last_Name.toString());
            System.out.println("last name updated in the DB");
            msg += "\n Last Name updated to Profile";
            instanceUser.setLastName(textLastName.getText());
        }

        if (!textFeet.getText().equals("") && !textInches.getText().equals("")) {
            String h = textFeet.getText() +"-" + textInches.getText();
            if(!h.equals(instanceUser.getHeight())){
             userDB.updateColumn(tableName, usr, h, DB_Col.Height.toString());
            //System.out.println("height updated in the DB");
            msg += "\n height updated to Profile";
            instanceUser.setHeight(h);
            }
           
        }

        if (genderSelected) {
            userDB.updateColumn(tableName, usr, labelGender.getText(), DB_Col.Gender.toString());
            System.out.println("gender choice updated in the DB");
            msg += "\n Gender updated to Profile";
            genderSelected = false;
            instanceUser.setGender(labelGender.getText());
        }

        if (bodyTypeSelected) {
            userDB.updateColumn(tableName, usr, instanceUser.getBodyType(), DB_Col.Body_Type.toString());
            msg += "\n Body Type updated to Profile";
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
               msg += "BirthDate update in Profile";
            }
        }
        
        makeAlert(msg);

    }

    @FXML
    private void onUpdateSecQuestions() throws IOException {
        App.setRoot("SecurityQuestions");
    }

    @FXML
    private void changePassword() throws IOException {
        App.setRoot("ChangePassword");
    }

    @FXML
    private void OpenMedConditions(ActionEvent event) throws IOException {
        App.setRoot("MedicalConditions");
    }

        public void makeAlert(String alertText) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("User Profile");
        a.setHeaderText(alertText);
        a.show();
    }
    
}
