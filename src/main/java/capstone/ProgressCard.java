/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
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
    
    
    @FXML
    public void goBack() throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    public void saveCard() throws IOException {
        //TODO: Make this save the given contents to DB
    }

    @FXML
    public void goToMeasure() throws IOException {
        //App.setRoot("MeasureCard");
    }

    @FXML
    public void goToIntake() throws IOException {
        App.setRoot("IntakeCard");
    }

    @FXML
    public void goToExercise() throws IOException {
        //App.setRoot("ExerciseCard");
    }
}
