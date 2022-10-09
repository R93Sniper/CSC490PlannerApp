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
    private TextField textHeight;
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

    private UserProfileModel instanceUser;
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
        ArrayList<String> bodyTypeList = new ArrayList<>(Arrays.asList("Endomorph", "Ectomorph", "Mesomorph"));
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
        //returns resultset matching the given username

        instanceUser = UserProfileModel.getInstance();
        result = userDB.getResult(instanceUser.getUserName(), "User_Profile");

        int id;
        String tempUserName = "";
        String tempPW = "";
        String tempFirstName = "";
        String tempLastName = "";
        String tempEmail = "";
        String tempBodyType = "";
        String tempGender = "";
        String tempHeight = "";
        String tempDOB = "";

        try {
            while (result.next()) {
                //id = result.getInt("ID");
                tempUserName = result.getString("User_Name");
                tempPW = result.getString("User_Password");
                tempFirstName = result.getString("First_Name");
                tempLastName = result.getString("Last_Name");
                tempEmail = result.getString("Email");
                tempBodyType = result.getString("Body_Type");
                tempGender = result.getString("Gender");
                tempHeight = result.getString("Height");
                tempDOB = result.getString("Date_Of_Birth");

                //System.out.printf("username= %s , h= %s \n", tempUserName, tempHeight);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

        instanceUser.setUserName(tempUserName);
        instanceUser.setGender(tempGender);
        instanceUser.setPassword(tempPW);
        instanceUser.setFirstName(tempFirstName);
        instanceUser.setLastName(tempLastName);
        instanceUser.setEmail(tempEmail);
        instanceUser.setHeight(tempHeight);
        instanceUser.setBodyType(tempBodyType);
        
        labelPassword.setText(tempPW);
        textFirstName.setText(tempFirstName);
        textLastName.setText(tempLastName);
        textEmail.setText(tempEmail);
        textHeight.setText(tempHeight);
        labelUserName.setText(tempUserName);
        labelBodyType.setText(tempBodyType);
        labelGender.setText(tempGender);
        labelBirthDate.setText(tempDOB);
        
        if(tempDOB != null)
        {
        //LocalDate theDate = LocalDate.parse(tempDOB, DateTimeFormatter.ofPattern("MM-DD-YYYY"));
        //LocalDate.pa
        //instanceUser.setBirthDate(theDate);
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

        if (textHeight.getText() != null && !textHeight.getText().equals(instanceUser.getHeight())) {
            userDB.updateColumn(tableName, usr, textHeight.getText(), DBCol.Height.toString());
            System.out.println("height updated in the DB");
            instanceUser.setHeight(textHeight.getText());
        }

        if (genderSelected) {
            userDB.updateColumn(tableName, usr, instanceUser.getGender(), DBCol.Gender.toString());
            System.out.println("gender choice updated in the DB");
            genderSelected = false;
        }

        if (bodyTypeSelected) {
            userDB.updateColumn(tableName, usr, instanceUser.getBodyType(), DBCol.Body_Type.toString());
            System.out.println("gender choice updated in the DB");
            bodyTypeSelected = false;
        }

        //userDoB.getValue().
        //int day = birthDate.getValue().getDayOfMonth();
        //int month = birthDate.getValue().getMonthValue();
        //int year = birthDate.getValue().getYear();
        // System.out.println("day= "+day +" , month= "+month +" , year= "+year);
        //LocalDate date = LocalDate.now();
        LocalDate date = birthDate.getValue();
        //LocalDate date = LocalDate.parse(d.toString(), DateTimeFormatter.ofPattern("MM-DD-YYYY"));
       // LocalDate c = LocalDate.parse(date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        if (date != null) {
            labelBirthDate.setText(date.toString());
            if (!dataConnector.getInstance().updateUserBirthDate(date, labelUserName.getText())) {
                System.out.println("failed to add birthDate to DB");
            }
        }

        //userDoB.getValue().
        //System.out.println();
        // usrBirthDate.getV
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
