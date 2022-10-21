/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author Wahab Quazi
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

    /*
    We need to set the currentDate on initialize of this controller
    I forgot what the method name was.
    Be sure that we grab the current system date
     */
    private DateTimeFormatter dt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private LocalDateTime now = LocalDateTime.now();
    private ProgressCardConnector pcTable = new ProgressCardConnector();
    private UserProfileModel usr = UserProfileModel.getInstance();
    @FXML
    private Button measureMe;

    /**
     * intialize method, calls upon opening this controller sets the date to the
     * current system date
     */
    public void initialize() {

        currentDate.setText(dt.format(now));

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

        //if valid entry then add row to Progress Table;
        if (validateEntry() && usr.getProgressCardId() == 0) {
            //check to see if progressID already exists for user 
            int id = pcTable.getProgressID(usr.getUserName(), now.toString());
            //if id returned is -1, then it does not exist yet and need to add new row in table
            if (id == -1) {
                int intakeID = usr.getDailyIntakeId();
                if (intakeID == 0) {
                    pcTable.userProgressCard(usr.getUserName(), now.toString(), currentWeight.getText(), 0, 0, "0", "0");
                } else {
                    pcTable.userProgressCard(usr.getUserName(), now.toString(), currentWeight.getText(), intakeID, 0, "0", "0");
                }
                id = pcTable.getLastRow("Progress_Cards");
            }
            usr.setProgressCardId(id);
        }

    }

    @FXML
    public void goToMeasure() throws IOException {
        makeAlert("Error, feature not yet implemented");
        //App.setRoot("MeasureCard");
    }

    @FXML
    public void goToIntake() throws IOException {
        App.setRoot("dailyIntake");
    }

    @FXML
    public void goToExercise() throws IOException {
        makeAlert("Error feature not yet implemented");
        //App.setRoot("ExerciseCard");
    }
}
