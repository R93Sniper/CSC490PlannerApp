/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 *
 * @author Wahab Quazi Jesus Alvarado Nick
 */
public class ProgressCard {

    @FXML
    private TextField currentWeight;
    @FXML
    private RadioButton rb_pounds;
    @FXML
    private RadioButton rb_kilo;
    @FXML
    private Text currentDate;
    @FXML
    private Label labelCalorieTarget;

    /*
    We need to set the currentDate on initialize of this controller
    I forgot what the method name was.
    Be sure that we grab the current system date
     */
    private DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDateTime now = LocalDateTime.now();
    private ProgressCardConnector pcTable = new ProgressCardConnector();
    private UserProfileModel usr = UserProfileModel.getInstance();
    @FXML
    private Button measureMe;
    @FXML
    private ToggleGroup lbsOrKg;
    @FXML
    private ToggleGroup todayOrNot;
    @FXML
    private DatePicker datePick;
    @FXML
    private RadioButton todaysDate;
    @FXML
    private RadioButton PreviousDate;
    @FXML
    private Text dateText;
    
    private boolean prevSelected = false;
    
    private String prevDate = "";

    /**
     * intialize method, calls upon opening this controller sets the date to the
     * current system date
     */
    public void initialize() {
        
        if(!prevSelected){
            currentDate.setText(dt.format(now));
        } else{
            currentDate.setText(prevDate);
        }
        loadWeight();
        //loadCalorieTarget();
    }

    /**
     * method to validate data collected from the user
     */
    public boolean validateEntry() {
        
        if (currentWeight.getText().equals("")) {//if the person enters nothing
            makeAlert("Error, you did not enter a weight");
            return false;
        } else {
            if (rb_pounds.isSelected()) {//if pounds is selected then validate data in pounds

                if (Double.parseDouble(currentWeight.getText()) <= 0) {//if the person enters a negative weight or 0
                    makeAlert("Error, you entered a negative weight");
                    return false;
                } else if (Double.parseDouble(currentWeight.getText()) > 1000) {//if the person enters over 1000 pounds
                    makeAlert("Error, you enteted a weight over 1000lbs");
                    return false;
                }
                return true;
            } else if (rb_kilo.isSelected()) {//if KG is selected then validate the data in kilograms

                if (Double.parseDouble(currentWeight.getText()) <= 0) {//if the person enters a negative weight or 0
                    makeAlert("Error, you entered a negative weight");
                    return false;
                } else if (Double.parseDouble(currentWeight.getText()) > 453) {//if the person enters over 1000 pounds (in kg)
                    makeAlert("Error, you enteted a weight over 453 kgs");
                    return false;
                }
                return true;
            } else {//unknwon/reserved for future use
                makeAlert("Error, not sure how you got here but good job");
            }
        }

        return false;
    }

    /**
     * method to make an alert for entering invalid data
     *
     * @param alertText
     */
    public void makeAlert(String alertText) {

        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(alertText);
        a.showAndWait();

    }

    @FXML
    public void goBack() throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    public void saveCard() throws IOException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //if valid entry then add row to Progress Table;
        if (validateEntry()) {          
            //check to see if progressID already exists for user 
            int id = usr.getProgressCardId();
            if (id == 0 || id == -1) {
                id = pcTable.getProgressID(usr.getUserName(), df.format(now));
                System.out.println("pId=" + id + "  ,date=" + df.format(now));
            }
            //if id returned is -1, then it does not exist yet and need to add new row in table
            if (id == -1 || id == 0) {
                String intakeID = String.valueOf(usr.getDailyIntakeId());
                String exID = String.valueOf(usr.getDailyExerciseId());
                pcTable.userProgressCard(usr.getUserName(), currentDate.getText(), currentWeight.getText(), intakeID, exID, "0", "0");
                id = pcTable.getLastRow("Progress_Cards");
                makeAlert("Succefuly created a new Progress Card"
                        + "\nAnd Saved it to the Database!");
                usr.setProgressCardId(id);
            } else {// row exists in table, so just need to update fields
                pcTable.updateWeight(id, currentWeight.getText());
                pcTable.updateDailyIntakeID(id, String.valueOf(usr.getDailyIntakeId()));
                pcTable.updateDailyExerciseID(id, String.valueOf(usr.getDailyExerciseId()));
                makeAlert("Successfuly Updated Today's Progress Card");
            }

        }

    }

    public void loadWeight() {     
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        int id = pcTable.getProgressID(usr.getUserName(), df.format(now));
        if (id != -1) {
            currentWeight.setText(String.valueOf(pcTable.getWeight(id)));
        }
    }

    @FXML
    public void goToMeasure() throws IOException {
        //makeAlert("Error, feature not yet implemented");
        App.setRoot("MeasurementsCard");
    }

    @FXML
    public void goToIntake() throws IOException {
        App.setRoot("dailyIntake");
    }

    @FXML
    public void goToExercise() throws IOException {
        // makeAlert("Error feature not yet implemented");
        App.setRoot("ExerciseCard");
    }

    @FXML
    public void goToStrength() throws IOException {
        App.setRoot("StrengthCard");
    }

    @FXML
    private void showTodaysDate(ActionEvent event) {

        datePick.setOpacity(0);
        prevSelected = false;
    }

    @FXML
    private void ShowDatePicker(ActionEvent event) {

        datePick.setOpacity(1);
        prevSelected = true;

    }

    /**
     * method to check the date selected for validity, if all is good then set the date text to the current date otherwise 
     * do nothing
     * @param event 
     */
    @FXML
    private void checkAndDoDate(ActionEvent event) {

        String d = datePick.getValue().toString();
        
        prevDate = d;

        String d2 = dt.format(now);

        if (d.equals(d2)) {

            makeAlert("Error, you selected todays date, please select a previous date");

        } else if (d.compareTo(d2) > 0) {

            makeAlert("Error, unless you can time travel you cannot enter data about a date that has not happened yet");

        } else {

            currentDate.setText(d);

        }

    }
    
    @FXML
    public void loadCalorieTarget(){
    if(pcTable.getLastWeight(usr.getUserName()).equals("")){return;}
    getCalorieTarget();
    }
    
    private void getCalorieTarget() {
     
        String gender = usr.getGender();
        if(gender==null || gender.equals("")){
             makeAlert("Please update Gender in Profile to display Calorie Target");
            return;
        }
        String height = usr.getHeightInCM();
        if(height.equals("")){
            makeAlert("Please update Height in Profile to display Calorie Target");
            return;
        }
        //conver height to cm
        int age = usr.getAge();   //could be -1, if not found in profile
        if(age ==-1){
            makeAlert("Please update Date of Birth in Profile to display Calorie Target");
            return;
        }
        
        String a = String.valueOf(age);
        String weight = pcTable.getLastWeight(usr.getUserName());
        if(weight.equals("")){
            makeAlert("Please update Progress Card with Weight to display Calorie Target");
            return;
        }
        
        double kgW = ((Double.valueOf(weight))/2.2) ;
        double result = 0;
        if(gender.equals("Male")){ 
        result = (10* kgW) + (6.25*Double.valueOf(height)) - (5* Double.valueOf(a)) + 5;
        }
        if(gender.equals("Female")){ 
        result = (10* kgW) + (6.25*Double.valueOf(height)) - (5* Double.valueOf(a)) -161;
        }
        
        result = Math.round(result) + 300;
        labelCalorieTarget.setText("Daily Calorie Target = "+result);
        
    }
    
}
